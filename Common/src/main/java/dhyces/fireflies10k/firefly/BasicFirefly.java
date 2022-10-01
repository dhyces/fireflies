package dhyces.fireflies10k.firefly;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class BasicFirefly implements IFirefly {

    Level level;

    Vec3 origin;
    Vec3 position;
    Vec3 oldPosition;
    Vec3 velocity;
    Vec3 intendedVelocity;
    int blinkTime = 0;
    long nextBlink = 0L;

    static final float MAX_VERTICAL_DIST = 0.65F;
    static final float MAX_HORIZONTAL_DIST = 0.65F;

    protected BasicFirefly(Level level, Vec3 origin, Vec3 position, Vec3 velocity) {
        this.level = level;
        this.origin = origin;
        this.position = position;
        this.oldPosition = position;
        this.velocity = velocity;
        this.intendedVelocity = velocity;
    }

    public static BasicFirefly createRandom(Level level, Vec3 origin, Random random) {
        var xPos = random.nextFloat() - 0.5;
        var yPos = random.nextFloat() - 0.5;
        var zPos = random.nextFloat() - 0.5;
        var position = new Vec3(xPos, yPos, zPos);
        var xVel = (random.nextFloat() - 0.5) * 0.25;
        var yVel = (random.nextFloat() - 0.5) * 0.25;
        var zVel = (random.nextFloat() - 0.5) * 0.25;
        var velocity = new Vec3(xVel, yVel, zVel);
        return new BasicFirefly(level, origin, position, velocity);
    }

    public void tick() {
        this.oldPosition = position;
        if (blinkTime > 0) {
            blinkTime++;
            if (blinkTime >= 14) {
                blinkTime = 0;
            }
        }
        if (nextBlink < level.getGameTime()) {
            nextBlink = level.getGameTime() + level.random.nextLong(1000);
            blinkTime = 1;
        }
        var distToMiddleVec = position.vectorTo(origin);
        if (distToMiddleVec.horizontalDistance() > MAX_HORIZONTAL_DIST) {
            var velNorm = intendedVelocity.normalize();
            var toOriginNorm = distToMiddleVec.normalize();
            var dotProd = velNorm.dot(toOriginNorm);
            if (dotProd < 0.3) {
                var halfAngle = (float)Math.acos(dotProd)/2F;
                this.intendedVelocity = this.velocity.xRot(level.random.nextInt(2) * halfAngle).yRot(level.random.nextInt(2) * halfAngle).zRot(level.random.nextInt(2) * halfAngle);
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

    public void remove(Entity.RemovalReason reason) {

    }

    public int getBlinkTime() {
        return blinkTime;
    }

    public Vec3 getOrigin() {
        return origin;
    }

    @Override
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
