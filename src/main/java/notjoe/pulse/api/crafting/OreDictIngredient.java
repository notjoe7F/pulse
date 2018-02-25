package notjoe.pulse.api.crafting;

import io.vavr.collection.Stream;
import io.vavr.control.Option;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictIngredient implements CraftingIngredient<ItemStack> {
    private final String oreDictKey;

    public OreDictIngredient(String oreDictKey) {
        this.oreDictKey = oreDictKey;
    }

    @Override
    public ItemStack[] getSuggestedInput() {
        return OreDictionary.getOres(oreDictKey).toArray(new ItemStack[]{});
    }

    @Override
    public boolean isInputValid(Option<ItemStack> other) {
        return other.isDefined() && Stream.ofAll(OreDictionary.getOres(oreDictKey))
                .find(validStack -> validStack.isItemEqual(other.get()))
                .isDefined();

    }
}
