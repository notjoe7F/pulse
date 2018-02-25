package notjoe.pulse.common;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import notjoe.pulse.common.content.item.ModItemContainer;

public class PulseTab extends CreativeTabs {
    public static final CreativeTabs CREATIVE_TAB = new PulseTab();

    private PulseTab() {
        super("pulse");
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(ModItemContainer.TUNING_FORK);
    }
}
