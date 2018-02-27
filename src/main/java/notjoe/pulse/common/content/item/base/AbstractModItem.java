package notjoe.pulse.common.content.item.base;

import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import notjoe.pulse.Pulse;
import notjoe.pulse.common.PulseTab;
import notjoe.pulse.common.content.CustomItemModelProvider;

public class AbstractModItem extends Item implements CustomItemModelProvider {
    private final String internalName;

    public AbstractModItem(String internalName) {
        this.internalName = internalName;

        setUnlocalizedName(Pulse.ID + "." + internalName);
        setRegistryName(Pulse.ID, internalName);

        setCreativeTab(PulseTab.CREATIVE_TAB);
    }

    public String getInternalName() {
        return internalName;
    }

    @Override
    public Map<Integer, ModelResourceLocation> getItemModelLocations() {
        return HashMap.of(0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }
}
