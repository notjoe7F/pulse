package notjoe.pulse.api.crafting.modal;

import io.vavr.collection.Vector;
import io.vavr.control.Option;
import notjoe.pulse.api.crafting.CraftingIngredient;
import notjoe.pulse.api.musictheory.Note;

public class ModalInfusionRecipe<T> {
    private final T output;
    private final Note initiator;
    private final Vector<CraftingIngredient<T>> inputs;
    private final int aeolianEssenceRequired;
    private final int ionianEssenceRequired;
    private final int tierRequired;

    public ModalInfusionRecipe(T output, Note initiator, Vector<CraftingIngredient<T>> inputs, int aeolianEssenceRequired,
                               int ionianEssenceRequired, int tierRequired) {
        this.output = output;
        this.initiator = initiator;
        this.inputs = inputs;
        this.aeolianEssenceRequired = aeolianEssenceRequired;
        this.ionianEssenceRequired = ionianEssenceRequired;
        this.tierRequired = tierRequired;
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

    public int getTierRequired() {
        return tierRequired;
    }

    public boolean matches(Note givenInitiator, Vector<T> givenInputs, int aeolianEssenceAvailable, int ionianEssenceAvailable, int tierAvailable) {
        return (initiator == givenInitiator) &&
                (inputs.length() == givenInputs.length()) &&
                (aeolianEssenceAvailable >= aeolianEssenceRequired) &&
                (ionianEssenceAvailable >= ionianEssenceRequired) &&
                (tierAvailable >= tierRequired) &&
                allInputsPresent(givenInputs);
    }

    public boolean allInputsPresent(Vector<T> givenInputs) {
        return inputs
                .forAll(input -> givenInputs
                        .find(givenInput -> input.isInputValid(Option.of(givenInput)))
                        .isDefined());
    }
}
