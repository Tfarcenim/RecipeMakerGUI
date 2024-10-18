package tfar.recipemakergui.client;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import tfar.recipemakergui.RecipeMakerGUI;
import tfar.recipemakergui.menu.GlobalMenuButton;
import tfar.recipemakergui.menu.SmithingRecipeMakerMenu;
import tfar.recipemakergui.menu.StonecuttingRecipeMakerMenu;

public class SmithingRecipeMakerScreen extends RecipeMakerScreen<SmithingRecipeMakerMenu> {

    public SmithingRecipeMakerScreen(SmithingRecipeMakerMenu $$0, Inventory $$1, Component $$2) {
        super($$0, $$1, $$2);
    }

    private static final ResourceLocation BACKGROUND = RecipeMakerGUI.id("textures/gui/smithing.png");


    @Override
    protected void init() {
        super.init();

        save = Button.builder(Component.literal("Save"), button -> trySave())
                .size(30,18)
                .pos(leftPos + 116,topPos + 62)
                .build();
        addRenderableWidget(save);

        SmallXButton outputNBT = new SmallXButton(leftPos+159,topPos+20,12,12,Component.literal("x"),button -> {
            sendGlobalButtonToServer(GlobalMenuButton.TOGGLE_NBT_SAVE);
        }, menu.saveNBT());
        outputNBT.setTooltip(Tooltip.create(Component.literal("Save Output NBT")));

        addRenderableWidget(outputNBT);
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        int i = this.leftPos;
        int j = (this.height - this.imageHeight) / 2;
        pGuiGraphics.blit(BACKGROUND, i, j, 0, 0, this.imageWidth, this.imageHeight);
    }

}
