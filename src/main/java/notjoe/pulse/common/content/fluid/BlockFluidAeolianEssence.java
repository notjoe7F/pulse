package notjoe.pulse.common.content.fluid;

import notjoe.pulse.Pulse;
import notjoe.pulse.common.content.fluid.block.AbstractModFluidBlock;

public class BlockFluidAeolianEssence extends AbstractModFluidBlock {
    public BlockFluidAeolianEssence() {
        super(FluidAeolianEssence.FLUID_AEOLIAN_ESSENCE);
        setRegistryName(Pulse.ID, "fluid_aeolian_essence");
    }
}
