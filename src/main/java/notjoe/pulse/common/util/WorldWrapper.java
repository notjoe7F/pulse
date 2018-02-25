package notjoe.pulse.common.util;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldWrapper {
    private final World world;

    public WorldWrapper(World world) {
        this.world = world;
    }

    public void tossItemStack(ItemStack stack, BlockPos pos, boolean throwUpwards) {
        EntityItem entityItem = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.1, pos.getZ() + 0.5, stack);
        if (throwUpwards) {
            entityItem.setVelocity(0d, 0.03d, 0);
        }

        world.spawnEntity(entityItem);
    }

    public void tossMultipleItemStacks(ItemStack stack, int times, BlockPos pos, boolean throwUpwards) {
        int totalItemQuantity = stack.getCount() * times;
        int fullStacks = totalItemQuantity / stack.getMaxStackSize();
        int remainder = totalItemQuantity % stack.getMaxStackSize();

        for (int i = 0; i < fullStacks; i++) {
            ItemStack tempStack = stack.copy();
            tempStack.setCount(stack.getMaxStackSize());
            tossItemStack(tempStack, pos, throwUpwards);
        }

        ItemStack remainderStack = stack.copy();
        remainderStack.setCount(remainder);
        tossItemStack(remainderStack, pos, throwUpwards);
    }

    public void ifClient(Runnable task) {
        if (world.isRemote) {
            task.run();
        }
    }

    public void ifServer(Runnable task) {
        if (!world.isRemote) {
            task.run();
        }
    }
}
