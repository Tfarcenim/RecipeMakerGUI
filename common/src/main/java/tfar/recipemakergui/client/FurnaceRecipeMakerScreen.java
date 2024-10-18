package tfar.recipemakergui.client;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import tfar.recipemakergui.RecipeMakerGUI;
import tfar.recipemakergui.menu.CookingRecipeMakerMenu;
import tfar.recipemakergui.menu.FurnaceMenuButton;
import tfar.recipemakergui.network.server.C2SSetDataValuePacket;
import tfar.recipemakergui.network.server.C2SSetDoubleValuePacket;
import tfar.recipemakergui.platform.Services;

public class FurnaceRecipeMakerScreen extends RecipeMakerScreen<CookingRecipeMakerMenu> {

    public FurnaceRecipeMakerScreen(CookingRecipeMakerMenu $$0, Inventory $$1, Component $$2) {
        super($$0, $$1, $$2);
    }

    private static final ResourceLocation CRAFTING_TABLE_LOCATION = RecipeMakerGUI.id("textures/gui/furnace.png");

    protected EditBox cooktimeBox;
    protected EditBox xpBox;

    @Override
    protected void init() {
        super.init();
        subInit();

        save = Button.builder(Component.literal("Save"), button -> trySave())
                .size(30,18)
                .pos(leftPos + 116,topPos + 62)
                .build();
        addRenderableWidget(save);

        SmallXButton outputNBT = new SmallXButton(leftPos+159,topPos+20,12,12,Component.literal("x"),button -> {
            sendButtonToServer(FurnaceMenuButton.TOGGLE_NBT_SAVE);
        }, menu.saveNBT());
        outputNBT.setTooltip(Tooltip.create(Component.literal("Save Output NBT")));

        addRenderableWidget(outputNBT);
    }

    protected void subInit() {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        this.cooktimeBox = new EditBox(this.font, i + 75, j + 20, 33, 12, Component.literal("Cooktime"));
        this.cooktimeBox.setTextColor(-1);
        this.cooktimeBox.setTextColorUneditable(-1);
        //this.cooktimeBox.setBordered(false);
        this.cooktimeBox.setMaxLength(9);
        this.cooktimeBox.setResponder(this::onCooktimeChanged);
        this.cooktimeBox.setValue("200");
        this.addWidget(this.cooktimeBox);
        cooktimeBox.setFilter(this::isValidCooktime);

        this.xpBox = new EditBox(this.font, i + 138, j + 40, 35, 12, Component.literal("Experience"));
        this.xpBox.setTextColor(-1);
        this.xpBox.setTextColorUneditable(-1);
        //this.xpBox.setBordered(false);
        this.xpBox.setMaxLength(9);
        this.xpBox.setResponder(this::onXpChanged);
        this.xpBox.setValue("0");
        this.addWidget(this.xpBox);
   //     xpBox.setFilter(this::isValidXp);

    }

    protected boolean isValidCooktime(String s) {
        if (s.isBlank()) return true;
        return isPositiveInteger(s);
    }

    protected boolean isValidXp(String s) {
        if (s.isBlank()) return true;
        return isPositiveDouble(s);
    }

    public static boolean isPositiveInteger(String s) {
        return s.matches("(0|[1-9]\\d*)");
    }

    public static boolean isPositiveDouble(String s) {
        return s.matches("[0-9]*\\.?[0-9]+");
    }


    private void onCooktimeChanged(String s) {
        Services.PLATFORM.sendToServer(new C2SSetDataValuePacket(CookingRecipeMakerMenu.DATA_COOKTIME, s.isEmpty() ? 200 : (short) Integer.parseInt(s)));
    }

    private void onXpChanged(String s) {
        try {
            Services.PLATFORM.sendToServer(new C2SSetDoubleValuePacket(s.isEmpty() ? 0 : Double.parseDouble(s)));
        } catch (NumberFormatException e) {

        }
    }

    @Override
    protected void renderTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        super.renderTooltip(guiGraphics, mouseX, mouseY);
        if (cooktimeBox != null && cooktimeBox.isHovered()) {
            guiGraphics.renderTooltip(this.font, cooktimeBox.getMessage(), mouseX, mouseY);
        }
        if (xpBox != null && xpBox.isHovered()) {
            guiGraphics.renderTooltip(this.font, xpBox.getMessage(), mouseX, mouseY);
        }
    }

    @Override
    public void renderFg(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        cooktimeBox.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        xpBox.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        int i = this.leftPos;
        int j = (this.height - this.imageHeight) / 2;
        pGuiGraphics.blit(CRAFTING_TABLE_LOCATION, i, j, 0, 0, this.imageWidth, this.imageHeight);
    }

    protected void sendButtonToServer(FurnaceMenuButton action) {
        this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId,-(action.ordinal()+1));
        //negate and subtract one to avoid interference with global values
    }

}
