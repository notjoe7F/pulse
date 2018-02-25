package notjoe.pulse.common.content.block.base;

import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import notjoe.pulse.Pulse;
import notjoe.pulse.common.PulseTab;
import notjoe.pulse.common.content.CustomItemModelProvider;

public abstract class AbstractModBlock extends Block implements CustomItemModelProvider {
    private final String internalName;

    public AbstractModBlock(String internalName, Material blockMaterialIn) {
        super(blockMaterialIn);
        this.internalName = internalName;

        setUnlocalizedName(Pulse.ID + "." + internalName);
        setRegistryName(Pulse.ID, internalName);

        setCreativeTab(PulseTab.CREATIVE_TAB);
    }

    @Override
    public Map<Integer, ModelResourceLocation> getItemModelLocations() {
        return HashMap.of(0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }
}
