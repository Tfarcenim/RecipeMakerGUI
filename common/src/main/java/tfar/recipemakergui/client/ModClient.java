package tfar.recipemakergui.client;

import net.minecraft.client.gui.screens.MenuScreens;
import tfar.recipemakergui.init.ModMenuTypes;

public class ModClient {

    public static void screens() {
        MenuScreens.register(ModMenuTypes.CRAFTING_RECIPE_MAKER,CraftingRecipeMakerScreen::new);
        MenuScreens.register(ModMenuTypes.COOKING_RECIPE_MAKER, AbstractCookingRecipeMakerScreen::new);
        MenuScreens.register(ModMenuTypes.STONECUTTER_RECIPE_MAKER,StonecuttingRecipeMakerScreen::new);
    }
}
