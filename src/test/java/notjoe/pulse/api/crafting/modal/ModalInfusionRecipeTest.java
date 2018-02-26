package notjoe.pulse.api.crafting.modal;

import io.vavr.Tuple;
import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import notjoe.pulse.api.crafting.CraftingIngredient;
import notjoe.pulse.api.crafting.DummyIngredient;
import notjoe.pulse.api.musictheory.Note;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ModalInfusionRecipeTest {
    private static String output;
    private static Map<String, Integer> inputs;
    private static Map<CraftingIngredient<String>, Integer> inputIngredients;

    @BeforeAll
    static void before() {
        output = "hello";
        inputs = HashMap.of(
                "a", 3,
                "b", 1,
                "c", 8,
                "d", 1
        );
        inputIngredients = inputs.map((ingredient, amount) -> Tuple.of(new DummyIngredient(ingredient), amount));
    }

    @Test
    void GivenMatchingRecipe_MatchesReturnsTrue() {
        ModalInfusionRecipe<String> recipe = new ModalInfusionRecipe<>(output, Note.A_FLAT, inputIngredients, 10, 10);
        Assert.assertTrue("Matches works correctly", recipe.matches(Note.A_FLAT, inputs, 10, 10));
    }

    @Test
    void GivenMatchingIngredients_WithInvalidQuantities_MatchesReturnsFalse() {
        ModalInfusionRecipe<String> recipe = new ModalInfusionRecipe<>(output, Note.A_FLAT, inputIngredients, 10, 10);
        Assert.assertFalse("Matches returns false when there is not enough of an ingredient",
                recipe.matches(Note.A_FLAT, inputs.replace("a", 3, 1), 10, 10));
        Assert.assertFalse("Matches returns false when there is too much of an ingredient",
                recipe.matches(Note.A_FLAT, inputs.replace("a", 3, 10), 10, 10));
    }

    @Test
    void GivenInvalidRecipe_MatchesReturnsFalse() {
        ModalInfusionRecipe<String> recipe = new ModalInfusionRecipe<>(output, Note.A_FLAT, inputIngredients, 10, 10);
        Assert.assertFalse("Matches returns false when the recipe does not contain all required ingredients",
                recipe.matches(Note.A_FLAT, HashMap.of("z", 100, "q", 10, "a", 3), 10, 10));
    }

    @Test
    void GivenMatchingIngredients_WithInvalidInitiator_MatchesReturnsFalse() {
        ModalInfusionRecipe<String> recipe = new ModalInfusionRecipe<>(output, Note.A_FLAT, inputIngredients, 10, 10);
        Assert.assertFalse("Matches returns false when the recipe does not contain all required ingredients",
                recipe.matches(Note.A, inputs, 10, 10));
    }

}