package notjoe.pulse.api.concept;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import notjoe.pulse.common.capability.HarmonicEnergyProvider;
import notjoe.pulse.common.capability.PulseCapabilities;

public class HarmonicEnergyGetter {
    public static final int TRANSMISSION_RANGE = 7;

    private final World world;

    public HarmonicEnergyGetter(World world) {
        this.world = world;
    }

    public int getAvailableEnergy(BlockPos pos, HarmonicEnergyType type) {
        return getAvailableEnergy(pos, TRANSMISSION_RANGE, type);
    }

    private int getAvailableEnergy(BlockPos pos, int range, HarmonicEnergyType type) {
        int available = 0;
        for (int i = -range; i <= range; i++) {
            for (int j = -range; j <= range; j++) {
                for (int k = -range; k <= range; k++) {
                    available += getEnergyProvidedByBlock(pos.add(i, j, k), type);
                }
            }
        }

        return available;
    }

    private int getEnergyProvidedByBlock(BlockPos pos, HarmonicEnergyType type) {
        TileEntity tile = world.getTileEntity(pos);
        if ((tile != null) && tile.hasCapability(PulseCapabilities.HARMONIC_ENERGY_PROVIDER, null)) {
            HarmonicEnergyProvider provider = tile.getCapability(PulseCapabilities.HARMONIC_ENERGY_PROVIDER, null);
            if (provider.getEnergyType() == type) {
                return provider.getAvailableEnergy();
            }
        }

        return 0;
    }
}
