package tfar.recipemakergui.init;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import tfar.recipemakergui.menu.CraftingRecipeMakerMenu;

public class ModMenuTypes {

    public static final MenuType<CraftingRecipeMakerMenu> CRAFTING_RECIPE_MAKER = new MenuType<>((int $$0, Inventory $$1) -> new CraftingRecipeMakerMenu($$0, $$1), FeatureFlags.VANILLA_SET);

}
