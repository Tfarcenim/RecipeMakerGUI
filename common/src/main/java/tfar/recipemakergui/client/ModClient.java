package tfar.recipemakergui.client;

import net.minecraft.client.gui.screens.MenuScreens;
import tfar.recipemakergui.init.ModMenuTypes;

public class ModClient {

    public static void screens() {
        MenuScreens.register(ModMenuTypes.CRAFTING_RECIPE_MAKER,CraftingRecipeMakerScreen::new);
        MenuScreens.register(ModMenuTypes.FURNACE_RECIPE_MAKER_MENU,FurnaceRecipeMakerScreen::new);
    }
}
