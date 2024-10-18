package tfar.recipemakergui.network.server;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import tfar.recipemakergui.menu.RecipeMakerMenu;

public class C2SSetDataValuePacket implements C2SModPacket {

    public final int slot;
    public final short value;//mojang truncates to shorts

    public C2SSetDataValuePacket(FriendlyByteBuf buf) {
        slot = buf.readInt();
        value = buf.readShort();
    }

    public C2SSetDataValuePacket(int slot, short value) {
        this.slot = slot;
        this.value = value;
    }

    @Override
    public void handleServer(ServerPlayer player) {
        AbstractContainerMenu menu = player.containerMenu;
        if (menu instanceof RecipeMakerMenu recipeMakerMenu) {
            recipeMakerMenu.setServerSideDataValue(slot,value);
        }
    }

    @Override
    public void write(FriendlyByteBuf to) {
        to.writeInt(slot);
        to.writeShort(value);
    }
}
