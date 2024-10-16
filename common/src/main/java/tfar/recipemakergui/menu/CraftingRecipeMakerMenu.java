package tfar.recipemakergui.menu;

import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import org.jetbrains.annotations.Nullable;
import tfar.recipemakergui.init.ModMenuTypes;

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
