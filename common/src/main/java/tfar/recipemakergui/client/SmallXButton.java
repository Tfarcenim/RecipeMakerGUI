package tfar.recipemakergui.client;

import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

public class SmallXButton extends Button {
    private boolean selected;

    protected SmallXButton(int $$0, int $$1, int $$2, int $$3, Component $$4, OnPress $$5, boolean selected) {
        super($$0, $$1, $$2, $$3, $$4, $$5, DEFAULT_NARRATION);
        this.selected = selected;
    }

    @Override
    public void onClick(double $$0, double $$1) {
        super.onClick($$0, $$1);
        selected = !selected;
    }

    @Override
    public Component getMessage() {
        return  selected ? super.getMessage(): Component.empty();
    }
}
