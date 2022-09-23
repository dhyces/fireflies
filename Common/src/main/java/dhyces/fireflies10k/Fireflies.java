package dhyces.fireflies10k;

import dhyces.fireflies10k.platform.Platforms;

public class Fireflies {
    public static final String MODID = "fireflies10k";

    public static void commonInit() {
        Register.init();
        Platforms.PLATFORM.registerEntityAttributes();
    }
}
