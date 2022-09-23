package dhyces.fireflies10k.entity;

import com.google.common.collect.Sets;
import dhyces.fireflies10k.Register;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class FirefliesEntity extends Mob {

    public final Set<Firefly> fireflies = Sets.newHashSet(new Firefly(new Vec3(0,0,0), new Vec3(0,0,0), (byte)0));

    protected FirefliesEntity(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
    }

    public FirefliesEntity(Level level) {
        super(Register.FIREFLIES.get(), level);
    }

    @Override
    public void tick() {
        super.tick();
        for (Firefly firefly : fireflies) {
            firefly.tick();
        }
    }

    @Override
    protected void actuallyHurt(@NotNull DamageSource $$0, float $$1) {
        var numberToKill = random.nextInt(3) + 1;
        var iter = fireflies.iterator();
        while (numberToKill > 0 || !fireflies.isEmpty()) {
            var next = iter.next();
            if (random.nextFloat() < 0.25F) {
                numberToKill--;
                next.kill();
                iter.remove();
            }
        }
    }

    @Override
    public boolean canBeLeashed(@NotNull Player $$0) {
        return false;
    }

    public static AttributeSupplier.Builder createFireflyAttributes() {
        return AttributeSupplier.builder().add(Attributes.MAX_HEALTH).add(Attributes.KNOCKBACK_RESISTANCE).add(Attributes.MOVEMENT_SPEED).add(Attributes.ARMOR).add(Attributes.ARMOR_TOUGHNESS);
    }

    public class Firefly {

        Vec3 position;
        Vec3 velocity;
        byte opinion;

        Firefly(Vec3 position, Vec3 velocity, byte opinion) {

        }

        public void tick() {

        }

        public void kill() {

        }

        public Vec3 getPosition() {
            return position;
        }
    }
}
