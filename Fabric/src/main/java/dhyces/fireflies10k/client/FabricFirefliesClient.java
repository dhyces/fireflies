package dhyces.fireflies10k.client;

import net.fabricmc.api.ClientModInitializer;

public class FabricFirefliesClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        CommonFirefliesClient.commonClientInit();
    }
}
