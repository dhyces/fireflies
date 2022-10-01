package dhyces.fireflies10k.platform;

import net.minecraft.core.particles.SimpleParticleType;

public interface PlatformHelper {

    SimpleParticleType createSimpleParticleType(boolean alwaysShow);

    void registerEntityAttributes();

    void clientInit();
}
