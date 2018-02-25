package notjoe.pulse.api.crafting.melodic;

import io.vavr.collection.Vector;
import io.vavr.control.Option;
import net.minecraft.item.ItemStack;
import notjoe.pulse.api.musictheory.Melody;

public class MelodicCraftingRegistry {
    private Vector<MelodicCraftingRecipe<ItemStack>> recipes;

    public MelodicCraftingRegistry() {
        recipes = Vector.empty();
    }

    public void add(MelodicCraftingRecipe<ItemStack> recipe) {
        recipes = recipes.append(recipe);
    }

    public Option<MelodicCraftingRecipe<ItemStack>> findMatchingRecipe(Melody melody, Vector<ItemStack> inputs, int gridSize) {
        return recipes.find(recipe -> recipe.matches(melody, inputs, gridSize));
    }
}
