package notjoe.pulse.api.crafting.harmonic;

import notjoe.pulse.api.crafting.CraftingIngredient;
import notjoe.pulse.api.crafting.DummyIngredient;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class HarmonicTransmutationRecipeTest {
    private static String output;
    private static String catalyst;
    private static CraftingIngredient<String> catalystIngredient;
    private static String input;
    private static CraftingIngredient<String> inputIngredient;

    @BeforeAll
    static void before() {
        output = "hello";
        catalyst = "catalyst";
        input = "input";
        catalystIngredient = new DummyIngredient(catalyst);
        inputIngredient = new DummyIngredient(input);
    }

    @Test
    void GivenCorrectInputs_MatchesReturnsTrue() {
        HarmonicTransmutationRecipe<String> recipe = new HarmonicTransmutationRecipe<>(output, inputIngredient, 1, catalystIngredient, 1, 100, 100);
        Assert.assertTrue("Recipe matches correctly", recipe.matches(input, 1, catalyst, 1, 100, 100));
    }

    @Test
    void GivenCorrectInputs_WithHigherCount_MatchesReturnsTrue() {
        HarmonicTransmutationRecipe<String> recipe = new HarmonicTransmutationRecipe<>(output, inputIngredient, 1, catalystIngredient, 1, 100, 100);
        Assert.assertTrue("Recipe with more inputs than required matches correctly", recipe.matches(input, 100, catalyst, 100, 100, 100));
    }

    @Test
    void GivenCorrectInputs_WithMoreEnergy_MatchesRetursnTrue() {
        HarmonicTransmutationRecipe<String> recipe = new HarmonicTransmutationRecipe<>(output, inputIngredient, 1, catalystIngredient, 1, 100, 100);
        Assert.assertTrue("Recipe with more energy than required matches correctly", recipe.matches(input, 1, catalyst, 1, 500, 500));
    }

    @Test
    void GivenIncorrectInputs_MatchesReturnsFalse() {
        HarmonicTransmutationRecipe<String> recipe = new HarmonicTransmutationRecipe<>(output, inputIngredient, 1, catalystIngredient, 1, 100, 100);
        Assert.assertFalse("Recipe with invalid inputs does not match", recipe.matches("bad input", 1, "bad catalyst", 1, 100, 100));
    }

    @Test
    void GivenCorrectInputs_WithInsufficientCount_MatchesReturnsFalse() {
        HarmonicTransmutationRecipe<String> recipe = new HarmonicTransmutationRecipe<>(output, inputIngredient, 100, catalystIngredient, 100, 100, 100);
        Assert.assertFalse("Recipe with fewer inputs than required does not match", recipe.matches(input, 1, catalyst, 1, 100, 100));
    }

    @Test
    void GivenCorrectInputs_WithInsufficientEnergy_MatchesReturnsFalse() {
        HarmonicTransmutationRecipe<String> recipe = new HarmonicTransmutationRecipe<>(output, inputIngredient, 1, catalystIngredient, 1, 100, 100);
        Assert.assertFalse("Recipe with less energy than required does not match", recipe.matches(input, 1, catalyst, 1, 1, 1));
    }
}
