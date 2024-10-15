package tfar.recipemakergui.client.client;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import tfar.recipemakergui.client.ModClient;

public class ModClientForge {

    public static void init(IEventBus bus) {
        bus.addListener(ModClientForge::setup);
    }

    static void setup(FMLClientSetupEvent event) {
        ModClient.screens();
    }

}
