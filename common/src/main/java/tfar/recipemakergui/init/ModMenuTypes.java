package tfar.recipemakergui.init;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import tfar.recipemakergui.menu.CraftingRecipeMakerMenu;
import tfar.recipemakergui.menu.FurnaceRecipeMakerMenu;

public class ModMenuTypes {

    public static final MenuType<CraftingRecipeMakerMenu> CRAFTING_RECIPE_MAKER = new MenuType<>(CraftingRecipeMakerMenu::new, FeatureFlags.VANILLA_SET);
    public static final MenuType<FurnaceRecipeMakerMenu> FURNACE_RECIPE_MAKER_MENU = new MenuType<>(FurnaceRecipeMakerMenu::new, FeatureFlags.VANILLA_SET);

}
