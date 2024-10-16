package tfar.recipemakergui.menu;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.Nullable;
import tfar.recipemakergui.RecipeMakerGUI;
import tfar.recipemakergui.init.ModMenuTypes;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class CraftingRecipeMakerMenu extends RecipeMakerMenu {

    private final ContainerData data;

    public CraftingRecipeMakerMenu(@Nullable MenuType<?> $$0, int $$1, Inventory inventory) {
        super($$0, $$1,inventory,new SimpleContainer(10));
        data = new SimpleContainerData(1);
        addDataSlots(data);
    }


    public CraftingRecipeMakerMenu(int id, Inventory inventory) {
        this(ModMenuTypes.CRAFTING_RECIPE_MAKER,id, inventory);
    }

    @Override
    protected void saveCurrentRecipe() {
        String name = getNextName();

            JsonObject jsonObject = serializeRecipe();
            write(jsonObject,name);
           // ShapelessRecipeBuilder.Result result = new ShapelessRecipeBuilder.Result(name,);
    }

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
        }
    }

    @Override
    protected RecipeSerializer<?> getRecipeType() {
        return isShapeless() ? RecipeSerializer.SHAPELESS_RECIPE:RecipeSerializer.SHAPED_RECIPE;
    }

    String getNextName() {
        ItemStack stack = craftingInventory.getItem(0);
        String defaultName =BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath();

        if (RecipeMakerGUI.doesNameExist(defaultName)) {

        }

        return defaultName;
    }

    @Override
    public boolean hasValidInput() {
        return !IntStream.range(1,10).allMatch(i -> craftingInventory.getItem(i).isEmpty());
    }

    @Override
    public boolean clickMenuButton(Player player, int id) {
        if (id < 0) {
            int ordinal = -(id + 1);
            CraftingMenuButton craftingMenuButton = CraftingMenuButton.values()[ordinal];
            switch (craftingMenuButton){
                case TOGGLE_SHAPELESS -> {
                    data.set(0,1 - data.get(0));
                }
            }
            return true;
        } else {
            return super.clickMenuButton(player, id);
        }
    }

    public boolean isShapeless() {
        return data.get(0) > 0;
    }

    @Override
    protected void addCraftingInventory(SimpleContainer craftingInventory) {
        this.addSlot(new Slot(craftingInventory, 0, 124, 35));

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 3; ++j) {
                this.addSlot(new Slot(craftingInventory, j + i * 3 +1, 30 + j * 18, 17 + i * 18));
            }
        }
    }
}
