package dhyces.fireflies10k.entity;

import com.google.common.collect.Sets;
import dhyces.fireflies10k.Register;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class FirefliesEntity extends Mob {

    private static final EntityDataAccessor<Integer> NUM_OF_FIREFLIES = SynchedEntityData.defineId(FirefliesEntity.class, EntityDataSerializers.INT);
    public static final String FIREFLY_COUNT_TAG = "FireflyCount";

    public final Set<Firefly> fireflies = Sets.newHashSet();

    protected FirefliesEntity(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
    }

    public FirefliesEntity(Level level) {
        super(Register.FIREFLIES.get(), level);
        this.fireflies.add(new Firefly(new Vec3(0,this.getEyeY(),0), new Vec3(0,0.01,0)));
    }

    @Override
    public void tick() {
        super.tick();
        if (fireflies.isEmpty()) {
            this.remove(RemovalReason.DISCARDED);
        }
        fireflies.forEach(Firefly::tick);
        for (Entity otherEntity : level.getEntities(this, getBoundingBox(), entity -> entity instanceof FirefliesEntity)) {
            var other = (FirefliesEntity)otherEntity;
            this.fireflies.addAll(other.fireflies);
            other.remove(RemovalReason.DISCARDED);
        }
    }

    @Override
    protected void actuallyHurt(@NotNull DamageSource damageSource, float damage) {
        if (damageSource == DamageSource.OUT_OF_WORLD) {
            remove(RemovalReason.KILLED);
            return;
        }
        var numberToKill = Math.round(damage);
        while (numberToKill > 0) {
            if (random.nextFloat() < 0.25F) {
                numberToKill--;
                updateFireflies(getFireflyCount()-1);
            }
        }
        if (fireflies.isEmpty()) {
            remove(RemovalReason.KILLED);
        }
    }

    @Override
    public void remove(RemovalReason $$0) {
        this.fireflies.clear();
        super.remove($$0);
    }

    @Override
    public @NotNull InteractionResult interactAt(@NotNull Player player, @NotNull Vec3 interactPoint, @NotNull InteractionHand usedHand) {
        if (player.getItemInHand(usedHand).is(Items.GLASS_BOTTLE)) {
            player.getInventory().add(new ItemStack(Register.FIREFLY_IN_A_BOTTLE.get()));
            if (!player.getAbilities().instabuild) {
                player.getItemInHand(usedHand).shrink(1);
            }
            updateFireflies(getFireflyCount()-1);
        }
        return super.interactAt(player, interactPoint, usedHand);
    }

    protected void removeFirefly(RemovalReason reason) {
        var iter = fireflies.iterator();
        if (iter.hasNext()) {
            var next = iter.next();
            next.remove(reason);
            iter.remove();
        }
        if (fireflies.isEmpty()) {
            this.remove(reason);
        }
    }

    @Override
    public boolean canHoldItem(ItemStack $$0) {
        return false;
    }

    @Override
    public void push(Entity $$0) {

    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    protected void doPush(Entity $$0) {

    }

    @Override
    protected boolean isAffectedByFluids() {
        return false;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(NUM_OF_FIREFLIES, 1);
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> dataAccessor) {
        super.onSyncedDataUpdated(dataAccessor);
        if (NUM_OF_FIREFLIES.equals(dataAccessor)) {
            updateFireflies(getFireflyCount());
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        this.setNoAi(tag.getBoolean("NoAI"));
        if (tag.contains(FIREFLY_COUNT_TAG)) {
            this.updateFireflies(tag.getInt(FIREFLY_COUNT_TAG));
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        if (this.isNoAi()) {
            tag.putBoolean("NoAI", this.isNoAi());
        }
        tag.putInt(FIREFLY_COUNT_TAG, this.getFireflyCount());
    }

    private void updateFireflies(int fireflyCount) {
        if (fireflyCount <= 0) {
            this.remove(RemovalReason.DISCARDED);
            return;
        }
        setFireflyCount(fireflyCount);
        var change = fireflyCount - fireflies.size();
        while (change != 0 && fireflies.size() != fireflyCount) {
            if (change > 0) {
                var pos = Math.sin((float) fireflies.size() / (float) fireflyCount);
                fireflies.add(new Firefly(new Vec3(pos, pos, pos), new Vec3(pos, pos, pos).normalize().scale(0.035)));
            } else {
                removeFirefly(RemovalReason.DISCARDED);
            }
        }
    }

    public void setFireflyCount(int fireflyCount) {
        this.getEntityData().set(NUM_OF_FIREFLIES, fireflyCount);
    }

    public int getFireflyCount() {
        return this.getEntityData().get(NUM_OF_FIREFLIES);
    }

    @Override
    public boolean canBeLeashed(@NotNull Player $$0) {
        return false;
    }

    @Override
    public void knockback(double $$0, double $$1, double $$2) {

    }

    public static AttributeSupplier.Builder createFireflyAttributes() {
        return AttributeSupplier.builder()
                .add(Attributes.MAX_HEALTH)
                .add(Attributes.KNOCKBACK_RESISTANCE)
                .add(Attributes.MOVEMENT_SPEED)
                .add(Attributes.ARMOR)
                .add(Attributes.ARMOR_TOUGHNESS)
                .add(Attributes.FOLLOW_RANGE, 16.0D)
                .add(Attributes.ATTACK_KNOCKBACK);
    }

    public class Firefly {

        Vec3 position;
        Vec3 oldPosition;
        Vec3 velocity;
        Vec3 intendedVelocity;
        int blinkTime = 0;
        long nextBlink = 0L;

        static final float MAX_VERTICAL_DIST = 0.65F;
        float horizontalDist;

        Firefly(Vec3 position, Vec3 velocity) {
            this.position = position;
            this.oldPosition = position;
            this.velocity = velocity;
            this.intendedVelocity = velocity;
        }

        public void tick() {
            if (isNoAi()) {
                return;
            }
            this.oldPosition = position;
            if (blinkTime > 0) {
                blinkTime++;
                if (blinkTime >= 14) {
                    blinkTime = 0;
                }
            }
            if (nextBlink < level.getGameTime()) {
                nextBlink = level.getGameTime() + random.nextLong(1000);
                blinkTime = 1;
            }
            var entityMiddle = FirefliesEntity.this.getBbHeight()/2F;
            var entityMiddlePosition = new Vec3(0, entityMiddle, 0);
            var distToMiddleVec = position.vectorTo(entityMiddlePosition);
            if (distToMiddleVec.horizontalDistance() > FirefliesEntity.this.getBoundingBox().getXsize() / 2D) {
                var velNorm = intendedVelocity.normalize();
                var toOriginNorm = distToMiddleVec.normalize();
                var dotProd = velNorm.dot(toOriginNorm);
                if (dotProd < 0.3) {
                    var halfAngle = (float)Math.acos(dotProd)/2F;
                    this.intendedVelocity = this.velocity.xRot(random.nextInt(2) * halfAngle).yRot(random.nextInt(2) * halfAngle).zRot(random.nextInt(2) * halfAngle);
                }
            }
            if (Math.abs(distToMiddleVec.y) > MAX_VERTICAL_DIST && this.intendedVelocity.normalize().dot(distToMiddleVec.normalize()) < 0) {
                this.intendedVelocity = this.velocity.zRot(1.5F);
            }
            this.velocity = velocity.lerp(intendedVelocity, 0.05);
            this.velocity = position.y <= 0 ? this.velocity.multiply(1, 0, 1) : this.velocity;
            if (Mth.equal(this.intendedVelocity.length(), 0)) {
                this.intendedVelocity = new Vec3(0.05, 0.05, 0.05);
            }
            this.position = position.add(velocity.x, velocity.y, velocity.z);
        }

        public void remove(RemovalReason reason) {

        }

        public int getBlinkTime() {
            return blinkTime;
        }

        public Vec3 getPosition() {
            return position;
        }

        public Vec3 getOldPosition() {
            return oldPosition;
        }

        public Vec3 getVelocity() {
            return velocity;
        }
    }
}
