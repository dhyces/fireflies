package dhyces.fireflies10k.platform;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

import java.util.function.Supplier;

public interface PlatformHelper {

    <T extends Entity> Supplier<EntityType<T>> registerEntity(String id, Supplier<EntityType<T>> entityTypeSupplier);

    void registerEntityAttributes();
}
