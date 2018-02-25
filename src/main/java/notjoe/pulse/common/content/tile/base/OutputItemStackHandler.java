package notjoe.pulse.common.content.tile.base;

import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class OutputItemStackHandler extends ExtendedItemStackHandler {
    public OutputItemStackHandler(int inventorySize, Runnable contentsChangedAction) {
        super(inventorySize, contentsChangedAction);
    }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        return stack;
    }
}
