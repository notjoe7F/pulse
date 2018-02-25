package notjoe.pulse.api.crafting.melodic;

import io.vavr.collection.Vector;
import notjoe.pulse.api.crafting.CraftingIngredient;
import notjoe.pulse.api.musictheory.Melody;

public class WildcardMelodicCraftingRecipe<T> extends MelodicCraftingRecipe<T> {
    public WildcardMelodicCraftingRecipe(T output, int minGridSize, Vector<CraftingIngredient<T>> inputIngredients) {
        super(output, Melody.empty(), minGridSize, inputIngredients);
    }

    @Override
    public boolean matches(Melody melody, Vector<T> inputs, int gridSideLength) {
        return super.matches(Melody.empty(), inputs, gridSideLength);
    }
}
