package notjoe.pulse.common.content;

import io.vavr.collection.Map;
import net.minecraft.item.ItemStack;

public interface OreDictProvider {
    Map<String, ItemStack> getOreDictEntries();
}
