package notjoe.pulse.common.content.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import notjoe.pulse.common.content.block.base.AbstractModBlock;

public class BlockCrystalConsonant extends AbstractModBlock {
    public BlockCrystalConsonant() {
        super("crystal_consonant", Material.GLASS);
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }
}
