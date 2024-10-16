package tfar.recipemakergui.menu;

import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public abstract class RecipeMakerMenu extends AbstractContainerMenu {

    public final SimpleContainer craftingInventory;

    protected RecipeMakerMenu(@Nullable MenuType<?> type, int id, Inventory inventory,SimpleContainer craftingInventory) {
        super(type, id);
        this.craftingInventory = craftingInventory;
        addCraftingInventory(craftingInventory);
        addPlayerInventory(inventory,0);
    }

    protected abstract void addCraftingInventory(SimpleContainer craftingInventory);

    protected void addPlayerInventory(Inventory inventory,int yPos) {
        int i;
        for(i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18 + yPos));
            }
        }

        for(i = 0; i < 9; ++i) {
            this.addSlot(new Slot(inventory, i, 8 + i * 18, 142 + yPos));
        }
    }

    @Override
    public boolean clickMenuButton(Player player, int id) {
        if (id >= 0) {
            GlobalMenuButton globalMenuButton = GlobalMenuButton.values()[id];
            switch (globalMenuButton) {
                case FURNACE -> {
                    player.openMenu(new MenuProvider() {
                        @Override
                        public Component getDisplayName() {
                            return Component.literal("Furnace");
                        }

                        @Override
                        public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
                            return new FurnaceRecipeMakerMenu(i, inventory);
                        }
                    });
                }
                case CRAFTING -> {
                    player.openMenu(new MenuProvider() {
                        @Override
                        public Component getDisplayName() {
                            return Component.literal("Crafting Table");
                        }

                        @Override
                        public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
                            return new CraftingRecipeMakerMenu(i, inventory);
                        }
                    });
                }
                case SAVE -> {

                }
            }
            return true;
        }
        return false;
    }

    public abstract boolean hasValidInput();

    protected abstract void saveCurrentRecipe();

    @Override
    public ItemStack quickMoveStack(Player player, int slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}
