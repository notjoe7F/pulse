package notjoe.pulse.api.crafting.melodic;

import io.vavr.collection.Vector;
import io.vavr.control.Option;
import net.minecraft.item.ItemStack;
import notjoe.pulse.api.crafting.AbstractNamedRegistry;
import notjoe.pulse.api.musictheory.Melody;

public class MelodicCraftingRegistry extends AbstractNamedRegistry<MelodicCraftingRecipe<ItemStack>> {
    public Option<MelodicCraftingRecipe<ItemStack>> findMatchingRecipe(Melody melody, Vector<ItemStack> inputs, int gridSize) {
        return objects.values().find(recipe -> recipe.matches(melody, inputs, gridSize));
    }
}
