package tfar.recipemakergui.client;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;

import javax.annotation.Nullable;

public class IntEditbox extends EditBox {
    public IntEditbox(Font pFont, int pX, int pY, int pWidth, int pHeight, Component pMessage) {
        this(pFont, pX, pY, pWidth, pHeight, null, pMessage);
    }

    public IntEditbox(Font pFont, int pX, int pY, int pWidth, int pHeight, @Nullable EditBox pEditBox, Component pMessage) {
        super(pFont,pX, pY, pWidth, pHeight, pEditBox,pMessage);
    }
}
