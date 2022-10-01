package dhyces.fireflies10k.blockentity;

import dhyces.fireflies10k.Register;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class WallFireflyLanternBlockEntity extends BlockEntity {

    public WallFireflyLanternBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(Register.WALL_FIREFLY_LANTERN_BE.get(), blockPos, blockState);
    }
}
