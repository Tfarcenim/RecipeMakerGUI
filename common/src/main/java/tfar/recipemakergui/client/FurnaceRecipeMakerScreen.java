package tfar.recipemakergui.client;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import tfar.recipemakergui.RecipeMakerGUI;
import tfar.recipemakergui.menu.CraftingRecipeMakerMenu;
import tfar.recipemakergui.menu.FurnaceRecipeMakerMenu;

public class FurnaceRecipeMakerScreen extends RecipeMakerScreen<FurnaceRecipeMakerMenu> {


    public FurnaceRecipeMakerScreen(FurnaceRecipeMakerMenu $$0, Inventory $$1, Component $$2) {
        super($$0, $$1, $$2);
    }

    private static final ResourceLocation CRAFTING_TABLE_LOCATION = RecipeMakerGUI.id("textures/gui/furnace.png");

    @Override
    protected void init() {
        super.init();
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pGuiGraphics);
        this.renderBg(pGuiGraphics, pPartialTick, pMouseX, pMouseY);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        int i = this.leftPos;
        int j = (this.height - this.imageHeight) / 2;
        pGuiGraphics.blit(CRAFTING_TABLE_LOCATION, i, j, 0, 0, this.imageWidth, this.imageHeight);
    }
}
