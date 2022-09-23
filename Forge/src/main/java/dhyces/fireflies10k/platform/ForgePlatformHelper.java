package dhyces.fireflies10k.platform;

import dhyces.fireflies10k.Fireflies;
import dhyces.fireflies10k.Register;
import dhyces.fireflies10k.entity.FirefliesEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class ForgePlatformHelper implements PlatformHelper {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPE_REGISTER = DeferredRegister.create(ForgeRegistries.ENTITIES, Fireflies.MODID);

    @Override
    public <T extends Entity> Supplier<EntityType<T>> registerEntity(String id, Supplier<EntityType<T>> entityTypeSupplier) {
        return ENTITY_TYPE_REGISTER.register(id, entityTypeSupplier);
    }

    @Override
    public void registerEntityAttributes() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::attributeEntityEvent);
    }

    private void attributeEntityEvent(final EntityAttributeCreationEvent event) {
        event.put(Register.FIREFLIES.get(), FirefliesEntity.createFireflyAttributes().build());
    }
}
