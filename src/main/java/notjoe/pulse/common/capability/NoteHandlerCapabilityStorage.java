package notjoe.pulse.common.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class NoteHandlerCapabilityStorage implements Capability.IStorage<NoteHandler> {
    @Nullable
    @Override
    public NBTBase writeNBT(Capability<NoteHandler> capability, NoteHandler instance, EnumFacing side) {
        return new NBTTagCompound();
    }

    @Override
    public void readNBT(Capability<NoteHandler> capability, NoteHandler instance, EnumFacing side, NBTBase nbt) {
        // NO-OP, nothing to read.
    }
}
