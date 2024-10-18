package tfar.recipemakergui.client;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import tfar.recipemakergui.menu.GlobalMenuButton;
import tfar.recipemakergui.menu.RecipeMakerMenu;

import java.util.HashMap;
import java.util.Map;

public abstract class RecipeMakerScreen<RMM extends RecipeMakerMenu> extends AbstractContainerScreen<RMM> {
    private DetailsList list;

    protected Button save;

    public RecipeMakerScreen(RMM $$0, Inventory $$1, Component $$2) {
        super($$0, $$1, $$2);
    }

    @Override
    protected void init() {
        super.init();
        initList();
    }

    public void initList() {
        this.list = new DetailsList(0,78);
        list.setRenderBackground(false);
        list.setLeftPos(leftPos - 24);
        list.setRenderTopAndBottom(false);
        this.addRenderableWidget(this.list);
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pGuiGraphics);
        this.renderBg(pGuiGraphics, pPartialTick, pMouseX, pMouseY);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        this.renderFg(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }

    @Override
    protected void renderTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        super.renderTooltip(guiGraphics, mouseX, mouseY);
        if (save != null && save.isHovered()) {
            if (menu.craftingInventory.getItem(0).isEmpty()) {
                guiGraphics.renderTooltip(this.font, Component.literal("Missing output").withStyle(ChatFormatting.RED), mouseX, mouseY);
            } else if (!menu.hasValidInput()) {
                guiGraphics.renderTooltip(this.font, Component.literal("Missing input(s)").withStyle(ChatFormatting.RED), mouseX, mouseY);
            }
        }
    }

    protected void renderFg(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
    }

    protected void sendGlobalButtonToServer(GlobalMenuButton action) {
        this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, action.ordinal());
    }

    static final Map<Item, GlobalMenuButton> map = new HashMap<>();

    static {
        map.put(Items.CRAFTING_TABLE, GlobalMenuButton.CRAFTING);
        map.put(Items.FURNACE, GlobalMenuButton.FURNACE);
    }

    protected class DetailsList extends ObjectSelectionList<DetailsList.Entry> {

        public DetailsList(int yPos, int height) {
            super(RecipeMakerScreen.this.minecraft, 24, imageHeight, topPos +yPos,topPos +yPos + height, 18);

            for (Map.Entry<Item, GlobalMenuButton> predicate : map.entrySet()) {
                Item s = predicate.getKey();
                    this.addEntry(new Entry(s.getDefaultInstance(),predicate.getValue()));
            }
        }

        @Override
        public boolean isFocused() {
            return RecipeMakerScreen.this.getFocused() == this;
        }

        @Override
        public int getRowWidth() {
            return 24;
        }

        @Override
        protected int getScrollbarPosition() {
            return 1000;//this.width/2 + 60;
        }

        protected class Entry extends ObjectSelectionList.Entry<Entry> {
            private final ItemStack stacks;
            private final GlobalMenuButton button;

            public Entry(ItemStack stacks, GlobalMenuButton button) {
                this.stacks = stacks;
                this.button = button;
            }

            @Override
            public void  render(GuiGraphics pGuiGraphics, int pIndex, int pTop, int pLeft, int pWidth, int pHeight, int pMouseX, int pMouseY, boolean pHovering, float pPartialTick) {
                pGuiGraphics.renderFakeItem(stacks, pLeft + 1, pTop + 1);
            }

            @Override
            public Component getNarration() {
                ItemStack itemstack = stacks;
                return !itemstack.isEmpty() ? Component.translatable("narrator.select", itemstack.getHoverName()) : CommonComponents.EMPTY;
            }

            @Override
            public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
                if (pButton == 0) {
                    DetailsList.this.setSelected(this);
                    sendGlobalButtonToServer(button);
                    return true;
                } else {
                    return false;
                }
            }
        }
    }

    protected void trySave() {
        if (menu.craftingInventory.getItem(0).isEmpty()) {
            return;
        }
        if (!menu.hasValidInput()) {
            return;
        }
        sendGlobalButtonToServer(GlobalMenuButton.SAVE);
    }

}
