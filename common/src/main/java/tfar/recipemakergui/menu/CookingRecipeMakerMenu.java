package tfar.recipemakergui.menu;

import com.google.gson.JsonObject;
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

public class CookingRecipeMakerMenu extends RecipeMakerMenu {

    public static final int DATA_COOKTIME = 1;
    private final RecipeSerializer<?> serializer;

    protected double xp;

    public CookingRecipeMakerMenu(@Nullable MenuType<?> type, int id, Inventory inventory, RecipeSerializer<?> serializer) {
        super(type, id,inventory,new SimpleContainer(2),new SimpleContainerData(3));
        this.serializer = serializer;
    }

    public static CookingRecipeMakerMenu smelting(int id, Inventory inventory) {
        return new CookingRecipeMakerMenu(ModMenuTypes.COOKING_RECIPE_MAKER,id,inventory,RecipeSerializer.SMELTING_RECIPE);
    }

    public static CookingRecipeMakerMenu blasting(int id, Inventory inventory) {
        return new CookingRecipeMakerMenu(ModMenuTypes.COOKING_RECIPE_MAKER,id,inventory,RecipeSerializer.BLASTING_RECIPE);
    }

    public static CookingRecipeMakerMenu smoking(int id, Inventory inventory) {
        return new CookingRecipeMakerMenu(ModMenuTypes.COOKING_RECIPE_MAKER,id,inventory,RecipeSerializer.SMOKING_RECIPE);
    }

    public static CookingRecipeMakerMenu campfire(int id, Inventory inventory) {
        return new CookingRecipeMakerMenu(ModMenuTypes.COOKING_RECIPE_MAKER,id,inventory,RecipeSerializer.CAMPFIRE_COOKING_RECIPE);
    }

    public static CookingRecipeMakerMenu client(int id, Inventory inventory) {
        return new CookingRecipeMakerMenu(ModMenuTypes.COOKING_RECIPE_MAKER,id,inventory,null);
    }

    @Override
    protected RecipeSerializer<?> getRecipeType() {
        return serializer;
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

    @Override
    protected void addCraftingInventory(SimpleContainer craftingInventory) {
        this.addSlot(new Slot(craftingInventory, 1, 56, 35));
       // this.addSlot(new FurnaceFuelSlot(this, pContainer, 1, 56, 53));
        this.addSlot(new Slot(craftingInventory, 0, 116, 35));
    }
}
