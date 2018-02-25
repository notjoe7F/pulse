package notjoe.pulse.common.content.tile.base;

import io.vavr.collection.Stream;
import io.vavr.collection.Vector;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class ExtendedItemStackHandler extends ItemStackHandler {
    private final Runnable contentsChangedAction;

    public ExtendedItemStackHandler(int inventorySize, Runnable contentsChangedAction) {
        super(inventorySize);
        this.contentsChangedAction = contentsChangedAction;
    }

    public Vector<ItemStack> getItemStacks() {
        return Vector.ofAll(stacks);
    }

    public void decrementAll(int amount) {
        Stream.range(0, getSlots()).forEach(i -> {
            getStackInSlot(i).shrink(amount);
            onContentsChanged(i);
        });
    }

    @Override
    protected void onContentsChanged(int slot) {
        contentsChangedAction.run();
    }
}