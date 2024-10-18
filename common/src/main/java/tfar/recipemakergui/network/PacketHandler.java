package tfar.recipemakergui.network;

import net.minecraft.resources.ResourceLocation;
import tfar.recipemakergui.RecipeMakerGUI;
import tfar.recipemakergui.network.server.C2SSetDataValuePacket;
import tfar.recipemakergui.network.server.C2SSetDoubleValuePacket;
import tfar.recipemakergui.platform.Services;

import java.util.Locale;

public class PacketHandler {

    public static void registerPackets() {
        Services.PLATFORM.registerServerPacket(C2SSetDataValuePacket.class, C2SSetDataValuePacket::new);
        Services.PLATFORM.registerServerPacket(C2SSetDoubleValuePacket.class, C2SSetDoubleValuePacket::new);

    }

    public static ResourceLocation packet(Class<?> clazz) {
        return RecipeMakerGUI.id(clazz.getName().toLowerCase(Locale.ROOT));
    }

}
