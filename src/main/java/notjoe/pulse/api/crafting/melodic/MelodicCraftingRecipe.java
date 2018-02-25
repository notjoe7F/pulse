package notjoe.pulse.api.crafting.melodic;

import io.vavr.collection.Vector;
import io.vavr.control.Option;
import notjoe.pulse.api.crafting.CraftingIngredient;
import notjoe.pulse.api.musictheory.Melody;

public class MelodicCraftingRecipe<T> {
    private final T output;
    private final Melody inputMelody;
    private final int minGridSize;
    private final Vector<CraftingIngredient<T>> inputIngredients;

    public MelodicCraftingRecipe(T output, Melody inputMelody, int minGridSize, Vector<CraftingIngredient<T>> inputIngredients) {
        this.output = output;
        this.inputMelody = inputMelody;
        this.minGridSize = minGridSize;
        this.inputIngredients = inputIngredients.take(minGridSize * minGridSize);
    }

    public T getOutput() {
        return output;
    }

    public Melody getInputMelody() {
        return inputMelody;
    }

    public int getMinGridSize() {
        return minGridSize;
    }

    public boolean matches(Melody melody, Vector<T> inputs, int gridSideLength) {
        if (!inputMelody.equals(melody) || (getMinGridSize() < gridSideLength)) {
            return false;
        }

        Vector<Option<T>> paddedInputs = inputs.map(Option::of).padTo(inputIngredients.length(), Option.none());
        Vector<Boolean> matchValidity = inputIngredients.zipWith(paddedInputs, CraftingIngredient::isInputValid);

        return !matchValidity.contains(false) && !matchValidity.isEmpty();
    }
}