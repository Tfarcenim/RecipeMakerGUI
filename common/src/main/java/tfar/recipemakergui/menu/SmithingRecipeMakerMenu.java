package tfar.recipemakergui.menu;

import com.google.gson.JsonObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.Nullable;
import tfar.recipemakergui.init.ModMenuTypes;

public class SmithingRecipeMakerMenu extends RecipeMakerMenu {


    public SmithingRecipeMakerMenu(@Nullable MenuType<?> type, int id, Inventory inventory) {
        super(type, id,inventory,new SimpleContainer(4),new SimpleContainerData(1));
    }

    public static SmithingRecipeMakerMenu client(int id, Inventory inventory) {
        return new SmithingRecipeMakerMenu(ModMenuTypes.SMITHING_RECIPE_MAKER,id,inventory);
    }

    @Override
    protected RecipeSerializer<?> getRecipeType() {
        return RecipeSerializer.SMITHING_TRANSFORM;
    }

    @Override
    protected void serializeRecipeData(JsonObject jsonobject) {
        ItemStack result = craftingInventory.getItem(0);
        jsonobject.add("ingredient", Ingredient.of(craftingInventory.getItem(1).getItem()).toJson());
        //jsonobject.addProperty("result", BuiltInRegistries.ITEM.getKey(this.result).toString());

        jsonobject.add("template",Ingredient.of(craftingInventory.getItem(1).getItem()).toJson());
        jsonobject.add("base",Ingredient.of(craftingInventory.getItem(2).getItem()).toJson());
        jsonobject.add("addition", Ingredient.of(craftingInventory.getItem(3).getItem()).toJson());

        jsonobject.add("result", serializeItemStack(result,saveNBT()));
    }


    @Override
    public boolean hasValidInput() {
        return !craftingInventory.getItem(1).isEmpty();
    }

    @Override
    protected void addCraftingInventory(SimpleContainer craftingInventory) {
        this.addSlot(new Slot(craftingInventory, 1, 20, 35));
        this.addSlot(new Slot(craftingInventory, 2, 38, 35));
        this.addSlot(new Slot(craftingInventory, 3, 56, 35));
       // this.addSlot(new FurnaceFuelSlot(this, pContainer, 1, 56, 53));
        this.addSlot(new Slot(craftingInventory, 0, 116, 35));
    }
}
