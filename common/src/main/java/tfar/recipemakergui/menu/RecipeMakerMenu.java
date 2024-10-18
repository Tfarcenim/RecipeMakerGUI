package tfar.recipemakergui.menu;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.Nullable;
import tfar.recipemakergui.RecipeMakerGUI;

import java.io.File;
import java.io.FileWriter;

public abstract class RecipeMakerMenu extends AbstractContainerMenu {

    public static final int DATA_SAVE_NBT = 0;

    public final SimpleContainer craftingInventory;
    protected final ContainerData data;

    protected RecipeMakerMenu(@Nullable MenuType<?> type, int id, Inventory inventory, SimpleContainer craftingInventory, ContainerData data) {
        super(type, id);
        this.craftingInventory = craftingInventory;
        addCraftingInventory(craftingInventory);
        addPlayerInventory(inventory, 0);
        this.data = data;
        addDataSlots(data);
    }

    protected abstract void addCraftingInventory(SimpleContainer craftingInventory);

    protected void addPlayerInventory(Inventory inventory, int yPos) {
        int i;
        for (i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18 + yPos));
            }
        }

        for (i = 0; i < 9; ++i) {
            this.addSlot(new Slot(inventory, i, 8 + i * 18, 142 + yPos));
        }
    }

    public boolean saveNBT() {
        return data.get(DATA_SAVE_NBT) > 0;
    }

    @Override
    public boolean clickMenuButton(Player player, int id) {
        if (id >= 0) {
            GlobalMenuButton globalMenuButton = GlobalMenuButton.values()[id];
            if (globalMenuButton.isStation()) {
                player.openMenu(new MakerProvider<>(globalMenuButton.item, (MenuType.MenuSupplier) globalMenuButton.supplier));
            } else {
                switch (globalMenuButton) {
                    case SAVE -> saveCurrentRecipe();
                    case TOGGLE_NBT_SAVE -> data.set(DATA_SAVE_NBT,1-data.get(DATA_SAVE_NBT));
                }
            }
            return true;
        }
        return false;
    }

    public abstract boolean hasValidInput();

    protected void saveCurrentRecipe() {
        String name = createName();
        JsonObject jsonObject = serializeRecipe();
        write(jsonObject, name);
    }

    protected String createName() {
        ItemStack stack = craftingInventory.getItem(0);
        String defaultName = BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath();

        if (RecipeMakerGUI.doesNameExist(defaultName)) {
            int i = 0;
            while (true) {
                i++;
                String dupName = defaultName + "_" + i;
                if (!RecipeMakerGUI.doesNameExist(dupName)) {
                    return dupName;
                }
            }
        }
        return defaultName;
    }


    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        int craftingSlots = getCraftingSlots();
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (pIndex < craftingSlots) {
                if (!this.moveItemStackTo(itemstack1, craftingSlots, craftingSlots + 36, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, craftingSlots, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(pPlayer, itemstack1);
        }

        return itemstack;
    }

    int getCraftingSlots() {
        return craftingInventory.getContainerSize();
    }

    /**
     * Called when the container is closed.
     */
    @Override
    public void removed(Player pPlayer) {
        super.removed(pPlayer);
        if (pPlayer instanceof ServerPlayer) {
            clearContainer(pPlayer,craftingInventory);
        }
    }


    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    protected abstract RecipeSerializer<?> getRecipeType();

    protected JsonObject serializeRecipe() {
        JsonObject jsonobject = new JsonObject();
        jsonobject.addProperty("type", BuiltInRegistries.RECIPE_SERIALIZER.getKey(this.getRecipeType()).toString());
        this.serializeRecipeData(jsonobject);
        return jsonobject;
    }

    public static JsonObject serializeItemStack(ItemStack stack, boolean saveNBT) {
        JsonObject resultObj = new JsonObject();
        resultObj.addProperty("item", BuiltInRegistries.ITEM.getKey(stack.getItem()).toString());
        if (stack.getCount() > 1) {
            resultObj.addProperty("count", stack.getCount());
        }
        if (stack.hasTag() && saveNBT) {
            resultObj.addProperty("nbt", stack.getTag().toString());
        }
        return resultObj;
    }

    public void setServerSideDataValue(int index, short value) {
        data.set(index, value);
    }

    public void setServerSideDoubleValue(double value) {

    }

    public static void write(JsonObject object, String fileName) {
        Gson gson = new Gson();
        JsonWriter writer = null;
        try {
            File file = new File("recipemakergui/data/recipemakergui/recipes/" + fileName + ".json");
            writer = gson.newJsonWriter(new FileWriter(file));
            writer.setIndent("    ");
            gson.toJson(object, writer);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(writer);
        }
    }

    protected abstract void serializeRecipeData(JsonObject jsonobject);


}
