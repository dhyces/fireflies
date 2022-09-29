package dhyces.fireflies10k.datagen;

import dhyces.fireflies10k.CommonFireflies;
import dhyces.fireflies10k.Register;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockStateModelDatagen extends BlockStateProvider {

    public BlockStateModelDatagen(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, CommonFireflies.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        getVariantBuilder(Register.FIREFLY_LANTERN_BLOCK.get()).forAllStates(blockState -> {
            return ConfiguredModel.builder().modelFile(models().getExistingFile(CommonFireflies.id("firefly_lantern"))).build();
        });
        getVariantBuilder(Register.WALL_FIREFLY_LANTERN_BLOCK.get()).forAllStates(blockState -> {
            var direction = blockState.getValue(BlockStateProperties.FACING);
            var isVertical = direction.getAxis().isVertical();
            if (isVertical) {
                return ConfiguredModel.builder()
                        .modelFile(models().getExistingFile(CommonFireflies.id("firefly_lantern")))
                        .build();
            } else {
                return ConfiguredModel.builder()
                        .modelFile(models().getExistingFile(CommonFireflies.id("wall_firefly_lantern")))
                        .rotationY((int)direction.toYRot())
                        .build();
            }
        });
    }
}
