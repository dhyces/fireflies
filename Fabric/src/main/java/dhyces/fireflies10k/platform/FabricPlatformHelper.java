package dhyces.fireflies10k.platform;

import dhyces.fireflies10k.Register;
import dhyces.fireflies10k.client.blockentity.FireflyLanternBER;
import dhyces.fireflies10k.client.entity.FirefliesRenderer;
import dhyces.fireflies10k.entity.FirefliesEntity;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.BiFunction;

public class FabricPlatformHelper implements PlatformHelper {

    @Override
    public <T extends BlockEntity> BlockEntityType<T> createBlockEntityType(BiFunction<BlockPos, BlockState, ? extends T> blockEntitySupplier, Block... blocks) {
        return FabricBlockEntityTypeBuilder.<T>create(blockEntitySupplier::apply, blocks).build();
    }

    @Override
    public void registerEntityAttributes() {
        FabricDefaultAttributeRegistry.register(Register.FIREFLIES.get(), FirefliesEntity.createFireflyAttributes());
    }

    @Override
    public void clientInit() {
        EntityRendererRegistry.register(Register.FIREFLIES.get(), FirefliesRenderer::new);
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.cutout(), Register.FIREFLY_LANTERN_BLOCK.get());
        BlockEntityRendererRegistry.register(Register.FIREFLY_LANTERN_BE.get(), FireflyLanternBER::new);
    }
}
