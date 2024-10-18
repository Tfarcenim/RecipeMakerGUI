package tfar.recipemakergui.init;

import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import tfar.recipemakergui.menu.CookingRecipeMakerMenu;
import tfar.recipemakergui.menu.CraftingRecipeMakerMenu;
import tfar.recipemakergui.menu.StonecuttingRecipeMakerMenu;

public class ModMenuTypes {

    public static final MenuType<CraftingRecipeMakerMenu> CRAFTING_RECIPE_MAKER = new MenuType<>(CraftingRecipeMakerMenu::new, FeatureFlags.VANILLA_SET);
    public static final MenuType<CookingRecipeMakerMenu> COOKING_RECIPE_MAKER = new MenuType<>(CookingRecipeMakerMenu::client, FeatureFlags.VANILLA_SET);
    public static final MenuType<StonecuttingRecipeMakerMenu> STONECUTTER_RECIPE_MAKER = new MenuType<>(StonecuttingRecipeMakerMenu::client, FeatureFlags.VANILLA_SET);

}
