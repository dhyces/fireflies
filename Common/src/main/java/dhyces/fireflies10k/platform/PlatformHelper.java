package dhyces.fireflies10k.platform;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public interface PlatformHelper {

    Supplier<Item> registerItem(String id, Supplier<Item> itemSupplier);

    <T extends Entity> Supplier<EntityType<T>> registerEntity(String id, Supplier<EntityType<T>> entityTypeSupplier);

    void registerEntityAttributes();

    void clientInit();
}
