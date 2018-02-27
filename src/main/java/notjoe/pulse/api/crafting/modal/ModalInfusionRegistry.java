package notjoe.pulse.api.crafting.modal;

import io.vavr.collection.Vector;
import io.vavr.control.Option;
import net.minecraft.item.ItemStack;
import notjoe.pulse.api.crafting.AbstractNamedRegistry;
import notjoe.pulse.api.musictheory.Note;

public class ModalInfusionRegistry extends AbstractNamedRegistry<ModalInfusionRecipe<ItemStack>> {
    public Option<ModalInfusionRecipe<ItemStack>> findMatchingRecipe(Note givenInitiator, Vector<ItemStack> givenInputs, int aeolianEssenceAvailable, int ionianEssenceAvailable, int tierAvailable) {
        return objects.values().find(recipe -> recipe.matches(givenInitiator, givenInputs, aeolianEssenceAvailable, ionianEssenceAvailable, tierAvailable));
    }
}
