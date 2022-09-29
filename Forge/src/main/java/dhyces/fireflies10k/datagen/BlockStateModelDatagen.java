package dhyces.fireflies10k.datagen;

import dhyces.fireflies10k.CommonFireflies;
import dhyces.fireflies10k.Register;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockStateModelDatagen extends BlockStateProvider {
    
    public BlockStateModelDatagen(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, CommonFireflies.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(Register.FIREFLY_LANTERN_BLOCK.get(), models().getExistingFile(CommonFireflies.id("firefly_lantern.json")));
    }
}
