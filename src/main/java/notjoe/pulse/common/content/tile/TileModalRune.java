package notjoe.pulse.common.content.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import notjoe.pulse.common.capability.ModalRune;
import notjoe.pulse.common.capability.PulseCapabilities;
import notjoe.pulse.common.content.tile.base.AbstractModTileEntity;

import javax.annotation.Nullable;

public class TileModalRune extends AbstractModTileEntity {
    private double speedMultiplier;
    private double stabilityMultiplier;
    private double efficiencyMultiplier;

    private final ModalRune modalRune = new ModalRune() {
        @Override
        public double getSpeedMultiplier(World world, BlockPos corePos) {
            return speedMultiplier;
        }

        @Override
        public double getInstabilityMultiplier(World world, BlockPos corePos) {
            return stabilityMultiplier;
        }

        @Override
        public double getEfficiencyMultiplier(World world, BlockPos corePos) {
            return efficiencyMultiplier;
        }

        @Override
        public boolean canFunction(World world, BlockPos corePos) {
            return true;
        }
    };

    public TileModalRune(double speedMultiplier, double stabilityMultiplier, double efficiencyMultiplier) {
        this.speedMultiplier = speedMultiplier;
        this.stabilityMultiplier = stabilityMultiplier;
        this.efficiencyMultiplier = efficiencyMultiplier;
    }

    public TileModalRune() {
        this(1, 1, 1);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == PulseCapabilities.MODAL_RUNE;
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (hasCapability(capability, facing)) {
            return (T) modalRune;
        }

        return super.getCapability(capability, facing);
    }

    @Override
    public void writeCustomDataToNbt(NBTTagCompound compound) {
        compound.setDouble("speed", speedMultiplier);
        compound.setDouble("stability", stabilityMultiplier);
        compound.setDouble("efficiency", efficiencyMultiplier);
    }

    @Override
    public void readCustomDataFromNbt(NBTTagCompound compound) {
        speedMultiplier = compound.getDouble("speed");
        stabilityMultiplier = compound.getDouble("stability");
        efficiencyMultiplier = compound.getDouble("efficiency");
    }
}
