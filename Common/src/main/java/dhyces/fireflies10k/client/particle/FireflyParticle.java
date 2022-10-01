package dhyces.fireflies10k.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FireflyParticle extends TextureSheetParticle {

    final double radius;

    FireflyParticle(ClientLevel level, double x, double y, double z) {
        super(level, x, y, z);
        this.hasPhysics = false;
        this.radius = 0;
    }

    FireflyParticle(ClientLevel level, double x, double y, double z, double xOff, double yOff, double zOff) {
        super(level, x, y, z, xOff, yOff, zOff);
        this.hasPhysics = false;
        this.radius = xOff;
    }

    @Override
    public void tick() {
        if (this.age++ >= this.lifetime) {
            this.remove();
        }
    }

    @NotNull
    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static final class Provider implements ParticleProvider<SimpleParticleType> {

        final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Nullable
        @Override
        public Particle createParticle(@NotNull SimpleParticleType simpleParticleType, @NotNull ClientLevel clientLevel, double x, double y, double z, double xVel, double yVel, double zVel) {
            var particle = new FireflyParticle(clientLevel, x, y, z, xVel, yVel, zVel);
            particle.pickSprite(this.spriteSet);
            return particle;
        }
    }
}
