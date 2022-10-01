package dhyces.fireflies10k.blockentity;

import dhyces.fireflies10k.Register;
import dhyces.fireflies10k.entity.FirefliesEntity;
import dhyces.fireflies10k.firefly.BasicFirefly;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.Stack;

public class FireflyLanternBlockEntity extends BlockEntity {

    protected final Stack<BasicFirefly> fireflies = new Stack<>();

    public FireflyLanternBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(Register.FIREFLY_LANTERN_BE.get(), blockPos, blockState);
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);
        if (!fireflies.isEmpty()) {
            tag.putInt("FireflyCount", fireflies.size());
        }
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        if (tag.contains("FireflyCount")) {
            var rand = new Random();
            for (int i = 0; i < tag.getInt("FireflyCount"); i++) {
                addRandomFirefly(rand);
            }
        }
    }

    public boolean addRandomFirefly(Random random) {
        var firefly = BasicFirefly.createRandom(level, new Vec3(0, 0, 0), random);
        return this.fireflies.add(firefly);
    }

    public boolean addFirefly(BasicFirefly firefly) {
        return this.fireflies.add(firefly);
    }

    public BasicFirefly popFirefly() {
        return fireflies.pop();
    }
}
