package notjoe.pulse.api.crafting;

import io.vavr.control.Option;

public class DummyIngredient implements CraftingIngredient<String> {
    private final String key;

    public DummyIngredient(String key) {
        this.key = key;
    }

    @Override
    public String[] getSuggestedInput() {
        return new String[] {key};
    }

    @Override
    public boolean isInputValid(Option<String> other) {
        return other.isDefined() && other.get().equals(key);
    }
}
