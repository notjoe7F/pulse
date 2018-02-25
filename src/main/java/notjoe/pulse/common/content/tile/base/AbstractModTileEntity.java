package notjoe.pulse.common.content.tile.base;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import notjoe.pulse.common.util.WorldWrapper;

import javax.annotation.Nullable;

public abstract class AbstractModTileEntity extends TileEntity {
    public WorldWrapper getWorldWrapper() {
        return new WorldWrapper(world);
    }
    
    @Override
    public NBTTagCompound getUpdateTag() {
        return serializeNBT();
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(getPos(), 1, serializeNBT());
    }

    public boolean canPlayerInteract(EntityPlayer player) {
        return player.getDistanceSq(pos) < 64;
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        deserializeNBT(pkt.getNbtCompound());
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        writeCustomDataToNbt(compound);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        readCustomDataFromNbt(compound);
    }

    public abstract void writeCustomDataToNbt(NBTTagCompound compound);
    public abstract void readCustomDataFromNbt(NBTTagCompound compound);

    protected void syncToClient() {
        IBlockState state = world.getBlockState(pos);
        world.markBlockRangeForRenderUpdate(pos, pos);
        world.notifyBlockUpdate(pos, state, state, 3);
        world.scheduleBlockUpdate(pos, getBlockType(), 0, 0);
        markDirty();
    }
}
