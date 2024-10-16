package tfar.recipemakergui.client;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import tfar.recipemakergui.RecipeMakerGUI;
import tfar.recipemakergui.menu.CraftingMenuButton;
import tfar.recipemakergui.menu.CraftingRecipeMakerMenu;
import tfar.recipemakergui.menu.GlobalMenuButton;

public class CraftingRecipeMakerScreen extends RecipeMakerScreen<CraftingRecipeMakerMenu> {


    public CraftingRecipeMakerScreen(CraftingRecipeMakerMenu $$0, Inventory $$1, Component $$2) {
        super($$0, $$1, $$2);
    }

    private static final ResourceLocation CRAFTING_TABLE_LOCATION = RecipeMakerGUI.id("textures/gui/crafting.png");

    @Override
    protected void init() {
        super.init();
        save = Button.builder(Component.literal("Save"), button -> trySave())
                .size(30,18)
                .pos(leftPos + 116,topPos + 62)
                .build();
        addRenderableWidget(save);

        SmallXButton toggleShapeless = new SmallXButton(leftPos+159,topPos+6,12,12,Component.literal("x"),button -> {
            sendButtonToServer(CraftingMenuButton.TOGGLE_SHAPELESS);
        }, menu.isShapeless());

        toggleShapeless.setTooltip(Tooltip.create(Component.literal("Shapeless")));
        addRenderableWidget(toggleShapeless);

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
        pGuiGraphics.blit(CRAFTING_TABLE_LOCATION, i, j, 0, 0, this.imageWidth, this.imageHeight);
    }

    protected void sendButtonToServer(CraftingMenuButton action) {
        this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId,-(action.ordinal()+1));
        //negate and subtract one to avoid interference with global values
    }

}
