package tfar.recipemakergui.network.server;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import tfar.recipemakergui.menu.RecipeMakerMenu;

public class C2SSetDoubleValuePacket implements C2SModPacket {

    public final double value;

    public C2SSetDoubleValuePacket(FriendlyByteBuf buf) {
        value = buf.readDouble();
    }

    public C2SSetDoubleValuePacket(double value) {
        this.value = value;
    }

    @Override
    public void handleServer(ServerPlayer player) {
        AbstractContainerMenu menu = player.containerMenu;
        if (menu instanceof RecipeMakerMenu recipeMakerMenu) {
            recipeMakerMenu.setServerSideDoubleValue(value);
        }
    }

    @Override
    public void write(FriendlyByteBuf to) {
        to.writeDouble(value);
    }
}
