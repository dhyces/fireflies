package dhyces.fireflies10k;

import dhyces.fireflies10k.client.ForgeFirefliesClient;
import dhyces.fireflies10k.datagen.BlockStateModelDatagen;
import dhyces.fireflies10k.datagen.RecipeDatagen;
import dhyces.fireflies10k.platform.ForgePlatformHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod(CommonFireflies.MODID)
public class ForgeFireflies {

    public ForgeFireflies() {
        CommonFireflies.commonInit();
        var modBus = FMLJavaModLoadingContext.get().getModEventBus();
        if (FMLEnvironment.dist == Dist.CLIENT) {
            ForgeFirefliesClient.init();
        }
        if (FMLLoader.getLaunchHandler().isData()) {
            modBus.addListener(this::datagen);
        }
    }

    private void datagen(GatherDataEvent event) {
        event.getGenerator().addProvider(new RecipeDatagen(event.getGenerator()));
        event.getGenerator().addProvider(new BlockStateModelDatagen(event.getGenerator(), event.getExistingFileHelper()));
    }
}
