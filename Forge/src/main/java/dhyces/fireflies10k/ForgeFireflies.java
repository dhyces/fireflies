package dhyces.fireflies10k;

import dhyces.fireflies10k.client.ForgeFirefliesClient;
import dhyces.fireflies10k.platform.ForgePlatformHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(CommonFireflies.MODID)
public class ForgeFireflies {

    public ForgeFireflies() {
        CommonFireflies.commonInit();
        var modBus = FMLJavaModLoadingContext.get().getModEventBus();
        ForgePlatformHelper.registerRegistries(modBus);
        if (FMLEnvironment.dist == Dist.CLIENT) {
            ForgeFirefliesClient.init();
        }
    }
}
