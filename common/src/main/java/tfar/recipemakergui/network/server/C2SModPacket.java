package tfar.recipemakergui.network.server;

import net.minecraft.server.level.ServerPlayer;
import tfar.recipemakergui.network.ModPacket;

public interface C2SModPacket extends ModPacket {

    void handleServer(ServerPlayer player);

}
