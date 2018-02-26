package notjoe.pulse.api.crafting.harmonic;

import io.vavr.control.Option;
import notjoe.pulse.api.crafting.CraftingIngredient;

public class HarmonicTransmutationRecipe<T> {
    private final T output;
    private final CraftingIngredient<T> input;
    private final int inputCount;
    private final CraftingIngredient<T> catalyst;
    private final int catalystCount;
    private final int consonantEnergyRequired;
    private final int dissonantEnergyRequired;

    public HarmonicTransmutationRecipe(T output, CraftingIngredient<T> input, int inputCount, CraftingIngredient<T> catalyst, int catalystCount, int consonantEnergyRequired, int dissonantEnergyRequired) {
        this.output = output;
        this.input = input;
        this.inputCount = inputCount;
        this.catalyst = catalyst;
        this.catalystCount = catalystCount;
        this.consonantEnergyRequired = consonantEnergyRequired;
        this.dissonantEnergyRequired = dissonantEnergyRequired;
    }

    public T getOutput() {
        return output;
    }

    public int getConsonantEnergyRequired() {
        return consonantEnergyRequired;
    }

    public int getInputCount() {
        return inputCount;
    }

    public int getCatalystCount() {
        return catalystCount;
    }

    public int getDissonantEnergyRequired() {
        return dissonantEnergyRequired;
    }

    public boolean matches(T givenInput, int givenInputCount, T givenCatalyst, int givenCatalystCount, int availableConsonantEnergy, int availableDissonantEnergy) {
        return input.isInputValid(Option.of(givenInput)) && (givenInputCount >= inputCount) &&
                catalyst.isInputValid(Option.of(givenCatalyst)) && (givenCatalystCount >= catalystCount) &&
                (availableConsonantEnergy >= consonantEnergyRequired) &&
                (availableDissonantEnergy >= dissonantEnergyRequired);
    }
}
