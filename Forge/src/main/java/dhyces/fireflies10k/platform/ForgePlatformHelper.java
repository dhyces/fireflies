package dhyces.fireflies10k.platform;

import dhyces.fireflies10k.CommonFireflies;
import dhyces.fireflies10k.Register;
import dhyces.fireflies10k.client.entity.FirefliesRenderer;
import dhyces.fireflies10k.entity.FirefliesEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class ForgePlatformHelper implements PlatformHelper {
    public static final DeferredRegister<Item> ITEM_DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.Keys.ITEMS, CommonFireflies.MODID);
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPE_REGISTER = DeferredRegister.create(ForgeRegistries.Keys.ENTITY_TYPES, CommonFireflies.MODID);

    public static void registerRegistries(IEventBus modBus) {
        ITEM_DEFERRED_REGISTER.register(modBus);
        ENTITY_TYPE_REGISTER.register(modBus);
    }

    @Override
    public Supplier<Item> registerItem(String id, Supplier<Item> itemSupplier) {
        return ITEM_DEFERRED_REGISTER.register(id, itemSupplier);
    }

    @Override
    public <T extends Entity> Supplier<EntityType<T>> registerEntity(String id, Supplier<EntityType<T>> entityTypeSupplier) {
        return ENTITY_TYPE_REGISTER.register(id, entityTypeSupplier);
    }

    @Override
    public void registerEntityAttributes() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::attributeEntityEvent);
    }

    @Override
    public void clientInit() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::entityRendererEvent);
    }

    private void entityRendererEvent(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(Register.FIREFLIES.get(), FirefliesRenderer::new);
    }

    private void attributeEntityEvent(final EntityAttributeCreationEvent event) {
        event.put(Register.FIREFLIES.get(), FirefliesEntity.createFireflyAttributes().add(ForgeMod.ENTITY_GRAVITY.get()).build());
    }
}
