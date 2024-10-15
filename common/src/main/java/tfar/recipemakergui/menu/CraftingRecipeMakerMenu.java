package tfar.recipemakergui.menu;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.Nullable;
import tfar.recipemakergui.init.ModMenuTypes;

public class CraftingRecipeMakerMenu extends RecipeMakerMenu {



    public CraftingRecipeMakerMenu(@Nullable MenuType<?> $$0, int $$1,Inventory inventory) {
        super($$0, $$1,inventory);
        addPlayerInventory(inventory,0);
    }


    public CraftingRecipeMakerMenu(int $$0, Inventory $$1) {
        this(ModMenuTypes.CRAFTING_RECIPE_MAKER,$$0, $$1);
    }


}
