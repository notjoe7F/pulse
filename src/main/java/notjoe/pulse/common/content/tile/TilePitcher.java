package notjoe.pulse.common.content.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import notjoe.pulse.api.musictheory.Note;
import notjoe.pulse.api.musictheory.Scales;
import notjoe.pulse.common.capability.NoteHandler;
import notjoe.pulse.common.capability.PulseCapabilities;
import notjoe.pulse.common.content.block.base.AbstractModDirectionalBlock;
import notjoe.pulse.common.content.entity.EntityNote;
import notjoe.pulse.common.content.tile.base.AbstractModTileEntity;

import javax.annotation.Nullable;

public class TilePitcher extends AbstractModTileEntity {
    private Scales scale;
    private final NoteHandler noteHandler = (noteEntity, face) -> {
        if (!world.isAirBlock(pos.offset(face))) {
            return;
        }

        Note nextNote = scale.offset(noteEntity.getNote(), 1);
        EntityNote nextEntityNote = new EntityNote(world, pos, getBlockFacing(), noteEntity, nextNote);
        world.spawnEntity(nextEntityNote);
        nextEntityNote.playSound();
    };

    public TilePitcher(Scales scale) {
        this.scale = scale;
    }

    public TilePitcher() {
        scale = Scales.PENTATONIC_MAJOR;
    }

    private EnumFacing getBlockFacing() {
        return world.getBlockState(pos).getValue(AbstractModDirectionalBlock.FACING);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return (capability == PulseCapabilities.NOTE_HANDLER) && (getBlockFacing() != facing);
    }

    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (hasCapability(capability, facing)) {
            return (T) noteHandler;
        }

        return super.getCapability(capability, facing);
    }

    @Override
    public void writeCustomDataToNbt(NBTTagCompound compound) {
        compound.setString("scale", scale.name());
    }

    @Override
    public void readCustomDataFromNbt(NBTTagCompound compound) {
        scale = Scales.valueOf(compound.getString("scale"));
    }
}
