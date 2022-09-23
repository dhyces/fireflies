package dhyces.fireflies10k;

import dhyces.fireflies10k.entity.FirefliesEntity;
import dhyces.fireflies10k.platform.Platforms;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import java.util.function.Supplier;

public class Register {

    public static void init() {}

    public static final Supplier<EntityType<FirefliesEntity>> FIREFLIES = Platforms.PLATFORM.registerEntity("fireflies", () -> EntityType.Builder.<FirefliesEntity>of(EntityType::create, MobCategory.AMBIENT).sized(1, 2).build("fireflies10k:fireflies"));
}
