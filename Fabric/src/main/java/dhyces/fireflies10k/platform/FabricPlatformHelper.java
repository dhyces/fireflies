package dhyces.fireflies10k.platform;

import dhyces.fireflies10k.CommonFireflies;
import dhyces.fireflies10k.Register;
import dhyces.fireflies10k.client.entity.FirefliesRenderer;
import dhyces.fireflies10k.entity.FirefliesEntity;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class FabricPlatformHelper implements PlatformHelper {
    @Override
    public Supplier<Item> registerItem(String id, Supplier<Item> itemSupplier) {
        var obj = itemSupplier.get();
        Registry.register(Registry.ITEM, new ResourceLocation(CommonFireflies.MODID, id), obj);
        return () -> obj;
    }

    @Override
    public <T extends Entity> Supplier<EntityType<T>> registerEntity(String id, Supplier<EntityType<T>> entityTypeSupplier) {
        var obj = entityTypeSupplier.get();
        Registry.register(Registry.ENTITY_TYPE, new ResourceLocation(CommonFireflies.MODID, id), obj);
        return () -> obj;
    }

    @Override
    public void registerEntityAttributes() {
        FabricDefaultAttributeRegistry.register(Register.FIREFLIES.get(), FirefliesEntity.createFireflyAttributes());
    }

    @Override
    public void clientInit() {
        EntityRendererRegistry.register(Register.FIREFLIES.get(), FirefliesRenderer::new);
    }
}
