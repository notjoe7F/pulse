package notjoe.pulse.api.crafting;

import io.vavr.control.Option;
import net.minecraft.item.ItemStack;

public class ItemStackIngredient implements CraftingIngredient<ItemStack> {
    private final ItemStack stack;

    public ItemStackIngredient(ItemStack stack) {
        this.stack = stack;
    }

    @Override
    public ItemStack[] getSuggestedInput() {
        return new ItemStack[] {stack};
    }

    @Override
    public boolean isInputValid(Option<ItemStack> other) {
        if (!other.isDefined()) {
            return stack.isEmpty();
        }

        return stack.isItemEqual(other.get());
    }
}
