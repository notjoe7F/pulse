package notjoe.pulse.common.content.fluid;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import notjoe.pulse.Pulse;
import notjoe.pulse.common.content.block.ModBlockContainer;
import notjoe.pulse.common.content.fluid.block.AbstractModFluidBlock;

public class BlockFluidIonianEssence extends AbstractModFluidBlock {
    public BlockFluidIonianEssence() {
        super(FluidIonianEssence.FLUID_IONIAN_ESSENCE);
        setRegistryName(Pulse.ID, "fluid_ionian_essence");
    }

    @Override
    protected void flowIntoBlock(World world, BlockPos pos, int meta) {
        IBlockState targetState = world.getBlockState(pos);
        if (targetState.getBlock() == ModBlockContainer.FLUID_AEOLIAN_ESSENCE) {
            if (targetState.getValue(BlockFluidClassic.LEVEL) == 0) {
                world.setBlockState(pos, Blocks.GLASS.getDefaultState());
            } else {
                world.setBlockState(pos, ModBlockContainer.CRYSTAL.getDefaultState());
            }
        } else {
            super.flowIntoBlock(world, pos, meta);
        }
    }
}
