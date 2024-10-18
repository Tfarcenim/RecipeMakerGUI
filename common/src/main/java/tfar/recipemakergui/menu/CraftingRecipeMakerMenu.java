package tfar.recipemakergui.menu;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.Nullable;
import tfar.recipemakergui.init.ModMenuTypes;

import java.util.*;
import java.util.stream.IntStream;

public class CraftingRecipeMakerMenu extends RecipeMakerMenu {

    public CraftingRecipeMakerMenu(@Nullable MenuType<?> $$0, int $$1, Inventory inventory) {
        super($$0, $$1, inventory, new SimpleContainer(10),new SimpleContainerData(2));
    }


    public CraftingRecipeMakerMenu(int id, Inventory inventory) {
        this(ModMenuTypes.CRAFTING_RECIPE_MAKER, id, inventory);
    }

    final String pattern = "abcdefghi";

    @Override
    protected void serializeRecipeData(JsonObject jsonobject) {
        ItemStack result = craftingInventory.getItem(0);
        if (isShapeless()) {
            List<Ingredient> ingredients = new ArrayList<>();
            for (int i = 1; i < 10; i++) {
                ItemStack stack = craftingInventory.getItem(i);
                if (!stack.isEmpty()) {
                    ingredients.add(Ingredient.of(stack.getItem()));
                }
            }
            JsonArray jsonarray = new JsonArray();
            for(Ingredient ingredient : ingredients) {
                jsonarray.add(ingredient.toJson());
            }
            jsonobject.add("ingredients", jsonarray);
        } else {
            writeShaped(jsonobject);
           // jsonobject.addProperty("show_notification", this.showNotification);
        }

        JsonObject resultObj = serializeItemStack(result,saveNBT());
        jsonobject.add("result", resultObj);
    }

    protected void writeShaped(JsonObject jsonObject) {
        Map<Item,Character> unique = new HashMap<>();
        int j = 0;
        for (int i = 1; i < 10; i++) {
            ItemStack stack = craftingInventory.getItem(i);
            if (!stack.isEmpty()) {
                if (unique.containsKey(stack.getItem())) {

                } else {
                    unique.put(stack.getItem(),pattern.charAt(j));
                    j++;
                }

            }
        }

        List<String> rows = new ArrayList<>();

        for (int y = 0; y < 3; y++) {
            StringBuilder row = new StringBuilder();
            for (int x = 0; x < 3;x++) {
                ItemStack stack = craftingInventory.getItem(3 * y + x + 1);
                if (!stack.isEmpty()) {
                    row.append(unique.get(stack.getItem()));
                } else {
                    row.append(" ");
                }
            }
            rows.add(row.toString());
        }

        Map<Character,Ingredient> keys = new HashMap<>();

        for (Map.Entry<Item,Character> entry : unique.entrySet()) {
            keys.put(entry.getValue(),Ingredient.of(entry.getKey()));
        }

        JsonArray jsonarray = new JsonArray();

        for(String s : rows) {
            jsonarray.add(s);
        }

        jsonObject.add("pattern", jsonarray);
        JsonObject keyObject = new JsonObject();

        for(Map.Entry<Character, Ingredient> entry : keys.entrySet()) {
            keyObject.add(String.valueOf(entry.getKey()), entry.getValue().toJson());
        }

        jsonObject.add("key", keyObject);
    }

    @Override
    protected RecipeSerializer<?> getRecipeType() {
        return isShapeless() ? RecipeSerializer.SHAPELESS_RECIPE : RecipeSerializer.SHAPED_RECIPE;
    }


    @Override
    public boolean hasValidInput() {
        return !IntStream.range(1, 10).allMatch(i -> craftingInventory.getItem(i).isEmpty());
    }

    @Override
    public boolean clickMenuButton(Player player, int id) {
        if (id < 0) {
            int ordinal = -(id + 1);
            CraftingMenuButton craftingMenuButton = CraftingMenuButton.values()[ordinal];
            switch (craftingMenuButton) {
                case TOGGLE_SHAPELESS -> data.set(0, 1 - data.get(0));
                case TOGGLE_NBT_SAVE -> data.set(1,1-data.get(1));
            }
            return true;
        } else {
            return super.clickMenuButton(player, id);
        }
    }

    public boolean isShapeless() {
        return data.get(0) > 0;
    }

    public boolean saveNBT() {
        return data.get(1) > 0;
    }

    @Override
    protected void addCraftingInventory(SimpleContainer craftingInventory) {
        this.addSlot(new Slot(craftingInventory, 0, 124, 35));

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                this.addSlot(new Slot(craftingInventory, j + i * 3 + 1, 30 + j * 18, 17 + i * 18));
            }
        }
    }
}
