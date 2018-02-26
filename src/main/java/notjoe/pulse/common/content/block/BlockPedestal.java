package notjoe.pulse.common.content.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import notjoe.pulse.common.content.block.base.AbstractModBlock;
import notjoe.pulse.common.content.tile.TilePedestal;

import javax.annotation.Nullable;

public class BlockPedestal extends AbstractModBlock {
    public BlockPedestal() {
        super("pedestal", Material.ROCK);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntity tile = worldIn.getTileEntity(pos);
        if ((tile instanceof TilePedestal) && !worldIn.isRemote) {
            ((TilePedestal) tile).onActivated(playerIn);
        }

        return true;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TilePedestal();
    }
}
