package dhyces.fireflies10k;

import dhyces.fireflies10k.block.FireflyLanternBlock;
import dhyces.fireflies10k.blockentity.FireflyLanternBlockEntity;
import dhyces.fireflies10k.entity.FirefliesEntity;
import dhyces.fireflies10k.platform.Platforms;
import dhyces.fireflies10k.registration.BlockRegistryObject;
import dhyces.fireflies10k.registration.RegistrationProvider;
import dhyces.fireflies10k.registration.RegistryObject;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

import java.util.function.Supplier;

public class Register {

    private static final RegistrationProvider<Block> BLOCK_REG_PROVIDER = RegistrationProvider.get(Registry.BLOCK_REGISTRY, CommonFireflies.MODID);
    private static final RegistrationProvider<Item> ITEM_REG_PROVIDER = RegistrationProvider.get(Registry.ITEM_REGISTRY, CommonFireflies.MODID);
    private static final RegistrationProvider<EntityType<?>> ENTITY_TYPE_REG_PROVIDER = RegistrationProvider.get(Registry.ENTITY_TYPE_REGISTRY, CommonFireflies.MODID);
    private static final RegistrationProvider<BlockEntityType<?>> BLOCK_ENTITY_REGISTRATION_PROVIDER = RegistrationProvider.get(Registry.BLOCK_ENTITY_TYPE_REGISTRY, CommonFireflies.MODID);

    public static void init() {}

    public static final BlockRegistryObject<FireflyLanternBlock> FIREFLY_LANTERN_BLOCK = registerAndWrap("firefly_lantern", () -> new FireflyLanternBlock(BlockBehaviour.Properties.of(Material.BUILDABLE_GLASS).isViewBlocking((blockState, blockGetter, blockPos) -> false).lightLevel(blockState -> (int)(3.75F * blockState.getValue(FireflyLanternBlock.FIREFLIES)))));

    public static final RegistryObject<Item> FIREFLY_IN_A_BOTTLE = ITEM_REG_PROVIDER.register("firefly_in_a_bottle", () -> new BlockItem(FIREFLY_LANTERN_BLOCK.get(),  new Item.Properties().stacksTo(16)));

    public static final RegistryObject<EntityType<FirefliesEntity>> FIREFLIES = ENTITY_TYPE_REG_PROVIDER.register("fireflies", () -> EntityType.Builder.<FirefliesEntity>of((entityType, level) -> new FirefliesEntity(level), MobCategory.AMBIENT).sized(1, 2).build("fireflies10k:fireflies"));

    public static final RegistryObject<BlockEntityType<FireflyLanternBlockEntity>> FIREFLY_LANTERN_BE = BLOCK_ENTITY_REGISTRATION_PROVIDER.register("wall_firefly_lantern", () -> Platforms.PLATFORM.createBlockEntityType(FireflyLanternBlockEntity::new, FIREFLY_LANTERN_BLOCK.get()));

    private static <T extends Block> BlockRegistryObject<T> registerAndWrap(String id, Supplier<T> blockSupplier) {
        var registered = BLOCK_REG_PROVIDER.register(id, blockSupplier);
        return BlockRegistryObject.wrap(registered);
    }
}
