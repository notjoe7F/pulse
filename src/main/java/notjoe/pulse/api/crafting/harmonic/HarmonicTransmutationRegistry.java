package notjoe.pulse.api.crafting.harmonic;

import io.vavr.collection.Vector;
import io.vavr.control.Option;
import net.minecraft.item.ItemStack;

public class HarmonicTransmutationRegistry {
    private Vector<HarmonicTransmutationRecipe<ItemStack>> recipes;

    public HarmonicTransmutationRegistry() {
        recipes = Vector.empty();
    }

    public void add(HarmonicTransmutationRecipe<ItemStack> recipe) {
        recipes = recipes.append(recipe);
    }

    public Option<HarmonicTransmutationRecipe<ItemStack>> findMatchingRecipe(ItemStack givenInput, ItemStack givenCatalyst, int availableConsonantEnergy, int availableDissonantEnergy) {
        return recipes.find(recipe -> recipe.matches(givenInput, givenInput.getCount(), givenCatalyst, givenCatalyst.getCount(), availableConsonantEnergy, availableDissonantEnergy));
    }
}
