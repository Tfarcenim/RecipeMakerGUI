package tfar.recipemakergui.menu;

import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import org.jetbrains.annotations.Nullable;
import tfar.recipemakergui.init.ModMenuTypes;

public class CraftingRecipeMakerMenu extends RecipeMakerMenu {

    public CraftingRecipeMakerMenu(@Nullable MenuType<?> $$0, int $$1, Inventory inventory) {
        super($$0, $$1,inventory,new SimpleContainer(10));
    }


    public CraftingRecipeMakerMenu(int id, Inventory inventory) {
        this(ModMenuTypes.CRAFTING_RECIPE_MAKER,id, inventory);
    }


    @Override
    protected void addCraftingInventory(SimpleContainer craftingInventory) {
        this.addSlot(new Slot(craftingInventory, 0, 124, 35));

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 3; ++j) {
                this.addSlot(new Slot(craftingInventory, j + i * 3, 30 + j * 18, 17 + i * 18));
            }
        }
    }
}
