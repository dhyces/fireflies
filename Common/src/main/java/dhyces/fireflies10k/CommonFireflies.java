package dhyces.fireflies10k;

import dhyces.fireflies10k.platform.Platforms;
import net.minecraft.resources.ResourceLocation;

public class CommonFireflies {
    public static final String MODID = "fireflies10k";

    public static void commonInit() {
        Register.init();
        Platforms.PLATFORM.registerEntityAttributes();
    }

    public static ResourceLocation id(String id) {
        return new ResourceLocation(MODID, id);
    }
}
