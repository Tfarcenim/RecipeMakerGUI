package tfar.recipemakergui.menu;

import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public enum GlobalMenuButton {
    CRAFTING(Items.CRAFTING_TABLE,CraftingRecipeMakerMenu::new),
    FURNACE(Items.FURNACE,CookingRecipeMakerMenu::smelting),
    BLASTING(Items.BLAST_FURNACE,CookingRecipeMakerMenu::blasting),
    SMOKING(Items.SMOKER,CookingRecipeMakerMenu::smoking),
    CAMPFIRE(Items.CAMPFIRE,CookingRecipeMakerMenu::campfire),

    SAVE;
    public final Item item;
    public final MenuType.MenuSupplier<?> supplier;

    GlobalMenuButton(Item item, MenuType.MenuSupplier<?> supplier) {
        this.item = item;
        this.supplier = supplier;
    }

    GlobalMenuButton() {
        this(null,(i, inventory) -> null);
    }

    public boolean isStation() {
        return item != null;
    }

}
