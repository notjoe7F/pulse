package notjoe.pulse.common.capability;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.concurrent.Callable;

public class ModalRuneFactory implements Callable<ModalRune> {
    @Override
    public ModalRune call() throws Exception {
        return new ModalRune() {
            @Override
            public double getSpeedMultiplier(World world, BlockPos corePos) {
                return 1;
            }

            @Override
            public double getInstabilityMultiplier(World world, BlockPos corePos) {
                return 1;
            }

            @Override
            public double getEfficiencyMultiplier(World world, BlockPos corePos) {
                return 1;
            }

            @Override
            public boolean canFunction(World world, BlockPos corePos) {
                return true;
            }
        };
    }
}
