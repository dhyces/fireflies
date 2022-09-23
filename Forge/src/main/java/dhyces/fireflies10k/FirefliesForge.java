package dhyces.fireflies10k;

import dhyces.fireflies10k.platform.ForgePlatformHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Fireflies.MODID)
public class FirefliesForge {

    public FirefliesForge() {
        Fireflies.commonInit();
        var modBus = FMLJavaModLoadingContext.get().getModEventBus();
        ForgePlatformHelper.ENTITY_TYPE_REGISTER.register(modBus);
    }
}
