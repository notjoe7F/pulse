package notjoe.pulse.common.content.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import notjoe.pulse.common.content.block.base.AbstractModBlock;
import notjoe.pulse.common.content.tile.TileHarmonicTransmutation;

import javax.annotation.Nullable;

public class BlockHarmonicTransmutation extends AbstractModBlock {
    public BlockHarmonicTransmutation() {
        super("harmonic_transmutation", Material.ROCK);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileHarmonicTransmutation();
    }
}
