package dhyces.fireflies10k;

import dhyces.fireflies10k.entity.FirefliesEntity;
import dhyces.fireflies10k.platform.Platforms;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class Register {

    public static void init() {}

    public static final Supplier<Item> FIREFLY_IN_A_BOTTLE = Platforms.PLATFORM.registerItem("firefly_in_a_bottle", () -> new Item(new Item.Properties().stacksTo(16)));

    public static final Supplier<EntityType<FirefliesEntity>> FIREFLIES = Platforms.PLATFORM.registerEntity("fireflies", () -> EntityType.Builder.<FirefliesEntity>of((entityType, level) -> new FirefliesEntity(level), MobCategory.AMBIENT).sized(1, 2).build("fireflies10k:fireflies"));
}
