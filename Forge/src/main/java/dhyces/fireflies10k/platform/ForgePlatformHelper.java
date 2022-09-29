package dhyces.fireflies10k.platform;

import dhyces.fireflies10k.Register;
import dhyces.fireflies10k.client.entity.FirefliesRenderer;
import dhyces.fireflies10k.entity.FirefliesEntity;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class ForgePlatformHelper implements PlatformHelper {

    @Override
    public void registerEntityAttributes() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::attributeEntityEvent);
    }

    @Override
    public void clientInit() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::entityRendererEvent);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
    }

    private void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemBlockRenderTypes.setRenderLayer(Register.FIREFLY_LANTERN_BLOCK.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(Register.WALL_FIREFLY_LANTERN_BLOCK.get(), RenderType.cutout());
        });
    }

    private void entityRendererEvent(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(Register.FIREFLIES.get(), FirefliesRenderer::new);
    }

    private void attributeEntityEvent(final EntityAttributeCreationEvent event) {
        event.put(Register.FIREFLIES.get(), FirefliesEntity.createFireflyAttributes().add(ForgeMod.ENTITY_GRAVITY.get()).build());
    }
}
