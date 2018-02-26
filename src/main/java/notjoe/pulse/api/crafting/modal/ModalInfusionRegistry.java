package notjoe.pulse.api.crafting.modal;

import io.vavr.collection.Map;
import io.vavr.collection.Vector;
import io.vavr.control.Option;
import io.vavr.control.Try;
import net.minecraft.item.ItemStack;
import notjoe.pulse.api.musictheory.Note;

public class ModalInfusionRegistry {
    private Vector<ModalInfusionRecipe<ItemStack>> recipes;

    public ModalInfusionRegistry() {
        recipes = Vector.empty();
    }

    public void add(ModalInfusionRecipe<ItemStack> recipe) {
        recipes = recipes.append(recipe);
    }

    public Option<ModalInfusionRecipe<ItemStack>> findMatchingRecipe(Note givenInitiator, Map<ItemStack, Integer> givenInputs, int aeolianEssenceAvailable, int ionianEssenceAvailable) {
        return recipes.find(recipe -> recipe.matches(givenInitiator, givenInputs, aeolianEssenceAvailable, ionianEssenceAvailable));
    }

    public int indexOfRecipe(ModalInfusionRecipe<ItemStack> recipe) {
        return recipes.indexOf(recipe);
    }

    public Option<ModalInfusionRecipe<ItemStack>> getByIndex(int index) {
        return Try.of(() -> recipes.get(index)).toOption();
    }
}
