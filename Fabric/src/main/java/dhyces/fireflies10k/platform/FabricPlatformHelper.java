package dhyces.fireflies10k.platform;

import dhyces.fireflies10k.Register;
import dhyces.fireflies10k.client.entity.FirefliesRenderer;
import dhyces.fireflies10k.entity.FirefliesEntity;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;

public class FabricPlatformHelper implements PlatformHelper {

    @Override
    public void registerEntityAttributes() {
        FabricDefaultAttributeRegistry.register(Register.FIREFLIES.get(), FirefliesEntity.createFireflyAttributes());
    }

    @Override
    public void clientInit() {
        EntityRendererRegistry.register(Register.FIREFLIES.get(), FirefliesRenderer::new);
    }
}
