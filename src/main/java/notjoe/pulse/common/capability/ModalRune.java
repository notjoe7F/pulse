package notjoe.pulse.common.capability;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ModalRune {
    double getSpeedMultiplier(World world, BlockPos corePos);
    double getInstabilityMultiplier(World world, BlockPos corePos);
    double getEfficiencyMultiplier(World world, BlockPos corePos);
    boolean canFunction(World world, BlockPos corePos);
}
