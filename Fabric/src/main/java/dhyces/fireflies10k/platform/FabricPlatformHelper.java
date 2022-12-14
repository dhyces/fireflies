package dhyces.fireflies10k.platform;

import dhyces.fireflies10k.Register;
import dhyces.fireflies10k.client.entity.FirefliesRenderer;
import dhyces.fireflies10k.entity.FirefliesEntity;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.client.renderer.RenderType;

public class FabricPlatformHelper implements PlatformHelper {

    @Override
    public void registerEntityAttributes() {
        FabricDefaultAttributeRegistry.register(Register.FIREFLIES.get(), FirefliesEntity.createFireflyAttributes());
    }

    @Override
    public void clientInit() {
        EntityRendererRegistry.register(Register.FIREFLIES.get(), FirefliesRenderer::new);
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.cutout(), Register.FIREFLY_LANTERN_BLOCK.get(), Register.WALL_FIREFLY_LANTERN_BLOCK.get());
    }
}
