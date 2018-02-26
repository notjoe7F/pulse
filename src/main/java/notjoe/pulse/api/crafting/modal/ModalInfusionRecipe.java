package notjoe.pulse.api.crafting.modal;

import io.vavr.collection.Map;
import io.vavr.control.Option;
import notjoe.pulse.api.crafting.CraftingIngredient;
import notjoe.pulse.api.musictheory.Note;

public class ModalInfusionRecipe<T> {
    private final T output;
    private final Note initiator;
    private final Map<CraftingIngredient<T>, Integer> inputs;
    private final int aeolianEssenceRequired;
    private final int ionianEssenceRequired;

    public ModalInfusionRecipe(T output, Note initiator, Map<CraftingIngredient<T>, Integer> inputs, int aeolianEssenceRequired,
                               int ionianEssenceRequired) {
        this.output = output;
        this.initiator = initiator;
        this.inputs = inputs;
        this.aeolianEssenceRequired = aeolianEssenceRequired;
        this.ionianEssenceRequired = ionianEssenceRequired;
    }

    public T getOutput() {
        return output;
    }

    public int getAeolianEssenceRequired() {
        return aeolianEssenceRequired;
    }

    public int getIonianEssenceRequired() {
        return ionianEssenceRequired;
    }

    public boolean matches(Note givenInitiator, Map<T, Integer> givenInputs, int aeolianEssenceAvailable, int ionianEssenceAvailable) {
        return (initiator == givenInitiator) &&
                (inputs.length() == givenInputs.length()) &&
                (aeolianEssenceAvailable >= aeolianEssenceRequired) &&
                (ionianEssenceAvailable >= ionianEssenceRequired) &&
                allInputsPresent(givenInputs);
    }

    private boolean allInputsPresent(Map<T, Integer> givenInputs) {
        return inputs
                .forAll(input -> givenInputs
                        .find(givenInput -> input._1.isInputValid(Option.of(givenInput._1)) &&
                                input._2.equals(givenInput._2))
                        .isDefined());
    }
}
