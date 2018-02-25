package notjoe.pulse.common.content.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import notjoe.pulse.api.musictheory.Scales;
import notjoe.pulse.common.content.block.base.AbstractModDirectionalBlock;
import notjoe.pulse.common.content.tile.TilePitcher;

import javax.annotation.Nullable;

public class BlockPitcherChromatic extends AbstractModDirectionalBlock {
    public BlockPitcherChromatic() {
        super("pitcher_chromatic", Material.WOOD);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TilePitcher(Scales.CHROMATIC);
    }
}
