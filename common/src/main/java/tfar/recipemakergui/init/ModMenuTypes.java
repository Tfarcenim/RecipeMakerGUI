package tfar.recipemakergui.init;

import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import tfar.recipemakergui.menu.CraftingRecipeMakerMenu;
import tfar.recipemakergui.menu.CookingRecipeMakerMenu;

public class ModMenuTypes {

    public static final MenuType<CraftingRecipeMakerMenu> CRAFTING_RECIPE_MAKER = new MenuType<>(CraftingRecipeMakerMenu::new, FeatureFlags.VANILLA_SET);
    public static final MenuType<CookingRecipeMakerMenu> FURNACE_RECIPE_MAKER_MENU = new MenuType<>(CookingRecipeMakerMenu::new, FeatureFlags.VANILLA_SET);

}
