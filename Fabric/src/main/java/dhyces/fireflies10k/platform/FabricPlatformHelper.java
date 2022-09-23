package dhyces.fireflies10k.platform;

import dhyces.fireflies10k.Fireflies;
import dhyces.fireflies10k.Register;
import dhyces.fireflies10k.entity.FirefliesEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

import java.util.function.Supplier;

public class FabricPlatformHelper implements PlatformHelper {
    @Override
    public <T extends Entity> Supplier<EntityType<T>> registerEntity(String id, Supplier<EntityType<T>> entityTypeSupplier) {
        var obj = entityTypeSupplier.get();
        Registry.register(Registry.ENTITY_TYPE, new ResourceLocation(Fireflies.MODID, id), obj);
        return () -> obj;
    }

    @Override
    public void registerEntityAttributes() {
        FabricDefaultAttributeRegistry.register(Register.FIREFLIES.get(), FirefliesEntity.createFireflyAttributes());
    }
}
