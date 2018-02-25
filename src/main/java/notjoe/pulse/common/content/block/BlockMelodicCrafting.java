package notjoe.pulse.common.content.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import notjoe.pulse.Pulse;
import notjoe.pulse.common.content.block.base.AbstractModBlock;
import notjoe.pulse.common.content.tile.TileMelodicCrafting;

import javax.annotation.Nullable;

public class BlockMelodicCrafting extends AbstractModBlock {
    public BlockMelodicCrafting() {
        super("melodic_crafting_table", Material.WOOD);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileMelodicCrafting();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            playerIn.openGui(Pulse.instance, -1, worldIn, pos.getX(), pos.getY(), pos.getZ());
        }

        return true;
    }
}
