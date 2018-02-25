package notjoe.pulse.api.crafting;

import io.vavr.collection.Vector;
import io.vavr.control.Option;
import net.minecraft.item.ItemStack;

public interface CraftingIngredient<T> {
    T[] getSuggestedInput();
    boolean isInputValid(Option<T> other);

    static Vector<CraftingIngredient<ItemStack>> createIngredientList(Object... rawIngredients) {
        return Vector.of(rawIngredients).map(obj -> {
            if (obj instanceof ItemStack) {
                return new ItemStackIngredient((ItemStack) obj);
            } else if (obj instanceof String) {
                return new OreDictIngredient((String) obj);
            } else {
                return new ItemStackIngredient(ItemStack.EMPTY);
            }
        });
    }

    static CraftingIngredient<ItemStack> ofItemStack(ItemStack stack) {
        return new ItemStackIngredient(stack);
    }

    static CraftingIngredient<ItemStack> ofOreDictEntry(String oreDict) {
        return new OreDictIngredient(oreDict);
    }
}
