package dhyces.fireflies10k.platform;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.BiFunction;

public interface PlatformHelper {

    <T extends BlockEntity> BlockEntityType<T> createBlockEntityType(BiFunction<BlockPos, BlockState, ? extends T> blockEntitySupplier, Block... blocks);

    void registerEntityAttributes();

    void clientInit();
}
