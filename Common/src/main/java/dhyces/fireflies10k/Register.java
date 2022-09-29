package dhyces.fireflies10k;

import dhyces.fireflies10k.entity.FirefliesEntity;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class Register {

    private static final RegistrationProvider<Block> BLOCK_REG_PROVIDER = RegistrationProvider.get(Registry.BLOCK_REGISTRY, CommonFireflies.MODID);
    private static final RegistrationProvider<Item> ITEM_REG_PROVIDER = RegistrationProvider.get(Registry.ITEM_REGISTRY, CommonFireflies.MODID);
    private static final RegistrationProvider<EntityType<?>> ENTITY_TYPE_REG_PROVIDER = RegistrationProvider.get(Registry.ENTITY_TYPE_REGISTRY, CommonFireflies.MODID);

    public static void init() {}

    public static final Supplier<Item> FIREFLY_IN_A_BOTTLE = ITEM_REG_PROVIDER.register("firefly_in_a_bottle", () -> new Item(new Item.Properties().stacksTo(16)));

    public static final Supplier<EntityType<FirefliesEntity>> FIREFLIES = ENTITY_TYPE_REG_PROVIDER.register("fireflies", () -> EntityType.Builder.<FirefliesEntity>of((entityType, level) -> new FirefliesEntity(level), MobCategory.AMBIENT).sized(1, 2).build("fireflies10k:fireflies"));
}
