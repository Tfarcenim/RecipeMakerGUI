package tfar.recipemakergui.network.client;


import tfar.recipemakergui.network.ModPacket;

public interface S2CModPacket extends ModPacket {

    void handleClient();

}
