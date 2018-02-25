package notjoe.pulse.api.crafting.melodic;

import io.vavr.collection.Vector;
import notjoe.pulse.api.crafting.CraftingIngredient;
import notjoe.pulse.api.crafting.DummyIngredient;
import notjoe.pulse.api.musictheory.Melody;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static notjoe.pulse.api.musictheory.Note.*;

class MelodicCraftingRecipeTest {
    private static String output;
    private static Melody melody;
    private static Vector<CraftingIngredient<String>> inputs;
    private static Vector<String> basicInputs;

    @BeforeAll
    static void before() {
        output = "hello";
        melody = new Melody(2, A, 6, C_LOW, 9, E);
        basicInputs = Vector.of("A", "B", "C");
        inputs = basicInputs.map(DummyIngredient::new);
    }

    @Test
    void GivenMatchingRecipe_MatchesReturnsTrue() {
        MelodicCraftingRecipe<String> recipe = new MelodicCraftingRecipe<>(output, melody, 3, inputs);
        Assert.assertTrue("Recipe matches properly.", recipe.matches(melody, basicInputs, 3));
    }

    @Test
    void GivenMatchingRecipe_WithSmallerGridSize_MatchesReturnsTrue() {
        MelodicCraftingRecipe<String> recipe = new MelodicCraftingRecipe<>(output, melody, 100, inputs);

        Assert.assertTrue("Recipe with a smaller grid size matches properly.", recipe.matches(melody, basicInputs, 3));
    }

    @Test
    void GivenMatchingRecipe_WithBiggerGridSize_MatchesReturnsFalse() {
        MelodicCraftingRecipe<String> recipe = new MelodicCraftingRecipe<>(output, melody, 3, inputs);
        Assert.assertFalse("Recipe with a smaller grid size matches properly.", recipe.matches(melody, basicInputs, 100));
    }

    @Test
    void GivenInvalidRecipeIngredients_WithSameGridSize_MatchesReturnsFalse() {
        MelodicCraftingRecipe<String> recipe = new MelodicCraftingRecipe<>(output, melody, 3, inputs);
        Assert.assertFalse("Recipe recognizes an invalid match.", recipe.matches(melody, Vector.of("a", "b", "d"), 3));
    }
}