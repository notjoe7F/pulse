package notjoe.pulse.common.content.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import notjoe.pulse.common.content.tile.base.AbstractModTileEntity;
import notjoe.pulse.common.content.tile.base.ExtendedItemStackHandler;

import javax.annotation.Nullable;

public class TilePedestal extends AbstractModTileEntity {
    private ExtendedItemStackHandler stackHandler;

    public TilePedestal() {
        stackHandler = new ExtendedItemStackHandler(1, this::markDirty);
    }

    public void onActivated(EntityPlayer player) {
        ItemStack containedStack = stackHandler.getStackInSlot(0);
        if (containedStack.isEmpty() && !player.getHeldItemMainhand().isEmpty()) {
            stackHandler.setStackInSlot(0, player.getHeldItemMainhand());
            player.setHeldItem(EnumHand.MAIN_HAND, ItemStack.EMPTY);
            player.inventory.markDirty();
            syncToClient();
        } else if (!containedStack.isEmpty() && player.getHeldItemMainhand().isEmpty()) {
            player.setHeldItem(EnumHand.MAIN_HAND, containedStack);
            stackHandler.setStackInSlot(0, ItemStack.EMPTY);
            player.inventory.markDirty();
            syncToClient();
        }
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (hasCapability(capability, facing)) {
            return (T) stackHandler;
        }

        return super.getCapability(capability, facing);
    }

    @Override
    public void writeCustomDataToNbt(NBTTagCompound compound) {
        compound.setTag("inventory", stackHandler.serializeNBT());
    }

    @Override
    public void readCustomDataFromNbt(NBTTagCompound compound) {
        stackHandler.deserializeNBT(compound.getCompoundTag("inventory"));
    }
}
