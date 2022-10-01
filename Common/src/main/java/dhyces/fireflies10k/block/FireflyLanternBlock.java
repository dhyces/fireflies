package dhyces.fireflies10k.block;

import dhyces.fireflies10k.Register;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class FireflyLanternBlock extends Block {

    public static final IntegerProperty FIREFLIES = IntegerProperty.create("fireflies", 1, 4);

    private static final VoxelShape SHAPE = Shapes.box(0.25, 0, 0.25, 0.75, 0.8125, 0.75);

    public FireflyLanternBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FIREFLIES, 1));
    }

    @Override
    public boolean propagatesSkylightDown(@NotNull BlockState blockState, @NotNull BlockGetter level, @NotNull BlockPos blockPos) {
        return true;
    }

    @Override
    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, Random random) {
        super.animateTick(blockState, level, blockPos, random);
        if (random.nextFloat() > 0.5) {
            var xOffset = (random.nextFloat() - 0.5) * 0.25;
            var zOffset = (random.nextFloat() - 0.5) * 0.25;
            level.addParticle(Register.FIREFLY_PARTICLE.get(), blockPos.getX() + 0.5 + xOffset, blockPos.getY() + 0.1, blockPos.getZ() + 0.5 + zOffset, -1, -1, -1);
        }
    }

    @Override
    public VoxelShape getShape(@NotNull BlockState blockState, @NotNull BlockGetter level, @NotNull BlockPos blockPos, @NotNull CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(@NotNull StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FIREFLIES);
    }
}
