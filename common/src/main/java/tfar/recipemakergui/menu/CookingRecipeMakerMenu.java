package tfar.recipemakergui.menu;

import com.google.gson.JsonObject;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.Nullable;
import tfar.recipemakergui.init.ModMenuTypes;

public class CookingRecipeMakerMenu extends RecipeMakerMenu {

    public static final int DATA_COOKTIME = 0;
    public static final int DATA_SAVE_NBT = 1;

    protected double xp;

    public CookingRecipeMakerMenu(@Nullable MenuType<?> $$0, int $$1, Inventory inventory) {
        super($$0, $$1,inventory,new SimpleContainer(2),new SimpleContainerData(3));
    }

    @Override
    protected RecipeSerializer<?> getRecipeType() {
        return RecipeSerializer.SMELTING_RECIPE;
    }

    @Override
    protected void serializeRecipeData(JsonObject jsonobject) {
        ItemStack result = craftingInventory.getItem(0);
        jsonobject.add("ingredient", Ingredient.of(craftingInventory.getItem(1).getItem()).toJson());
        //jsonobject.addProperty("result", BuiltInRegistries.ITEM.getKey(this.result).toString());
        jsonobject.addProperty("experience", getExperience());
        int cookingTime = getCookingTime();
        jsonobject.addProperty("cookingtime", cookingTime > 0 ? cookingTime : 200);
        jsonobject.add("result",serializeItemStack(result,saveNBT()));
    }

    @Override
    public boolean clickMenuButton(Player player, int id) {
        if (id < 0) {
            int ordinal = -(id + 1);
            FurnaceMenuButton craftingMenuButton = FurnaceMenuButton.values()[ordinal];
            switch (craftingMenuButton) {
                case TOGGLE_NBT_SAVE -> data.set(DATA_SAVE_NBT,1-data.get(1));
            }
            return true;
        } else {
            return super.clickMenuButton(player, id);
        }
    }

    public boolean saveNBT() {
        return data.get(DATA_SAVE_NBT) > 0;
    }

    @Override
    public boolean hasValidInput() {
        return !craftingInventory.getItem(1).isEmpty();
    }

    public int getCookingTime() {
        return data.get(DATA_COOKTIME);
    }

    public double getExperience() {
        return xp;
    }

    @Override
    public void setServerSideDoubleValue(double value) {
        xp = value;
    }

    public CookingRecipeMakerMenu(int $$0, Inventory $$1) {
        this(ModMenuTypes.FURNACE_RECIPE_MAKER_MENU,$$0, $$1);
    }

    @Override
    protected void addCraftingInventory(SimpleContainer craftingInventory) {
        this.addSlot(new Slot(craftingInventory, 1, 56, 17));
       // this.addSlot(new FurnaceFuelSlot(this, pContainer, 1, 56, 53));
        this.addSlot(new Slot(craftingInventory, 0, 116, 35));
    }
}
