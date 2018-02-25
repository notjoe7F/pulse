package notjoe.pulse.common.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class HarmonicEnergyCapabilityStorage implements Capability.IStorage<HarmonicEnergyProvider> {
    @Nullable
    @Override
    public NBTBase writeNBT(Capability<HarmonicEnergyProvider> capability, HarmonicEnergyProvider instance, EnumFacing side) {
        return new NBTTagCompound();
    }

    @Override
    public void readNBT(Capability<HarmonicEnergyProvider> capability, HarmonicEnergyProvider instance, EnumFacing side, NBTBase nbt) {
        // NO-OP, nothing to read.
    }
}
