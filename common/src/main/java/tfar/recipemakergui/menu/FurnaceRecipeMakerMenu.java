package tfar.recipemakergui.menu;

import com.google.gson.JsonObject;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.Nullable;
import tfar.recipemakergui.init.ModMenuTypes;

public class FurnaceRecipeMakerMenu extends RecipeMakerMenu {



    public FurnaceRecipeMakerMenu(@Nullable MenuType<?> $$0, int $$1, Inventory inventory) {
        super($$0, $$1,inventory,new SimpleContainer(2));
    }

    @Override
    protected void saveCurrentRecipe() {

    }

    @Override
    protected RecipeSerializer<?> getRecipeType() {
        return RecipeSerializer.SMELTING_RECIPE;
    }

    @Override
    protected void serializeRecipeData(JsonObject jsonobject) {

    }

    @Override
    public boolean hasValidInput() {
        return !craftingInventory.getItem(1).isEmpty();
    }

    public FurnaceRecipeMakerMenu(int $$0, Inventory $$1) {
        this(ModMenuTypes.FURNACE_RECIPE_MAKER_MENU,$$0, $$1);
    }

    @Override
    protected void addCraftingInventory(SimpleContainer craftingInventory) {

    }
}
