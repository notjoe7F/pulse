package notjoe.pulse.api.crafting.harmonic;

import io.vavr.control.Option;
import net.minecraft.item.ItemStack;
import notjoe.pulse.api.crafting.AbstractNamedRegistry;

public class HarmonicTransmutationRegistry extends AbstractNamedRegistry<HarmonicTransmutationRecipe<ItemStack>> {
    public Option<HarmonicTransmutationRecipe<ItemStack>> findMatchingRecipe(ItemStack givenInput, ItemStack givenCatalyst, int availableConsonantEnergy, int availableDissonantEnergy) {
        return objects.values().find(recipe -> recipe.matches(givenInput, givenInput.getCount(), givenCatalyst, givenCatalyst.getCount(), availableConsonantEnergy, availableDissonantEnergy));
    }
}
