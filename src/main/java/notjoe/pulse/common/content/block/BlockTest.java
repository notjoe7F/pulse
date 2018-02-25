package notjoe.pulse.common.content.block;

import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import notjoe.pulse.api.musictheory.Note;
import notjoe.pulse.common.content.block.base.AbstractModBlock;
import notjoe.pulse.common.content.entity.EntityNote;

public class BlockTest extends AbstractModBlock {
    public BlockTest() {
        super("test", Material.IRON);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        EntityNote note = new EntityNote(worldIn, pos, facing.getOpposite(), Note.C_LOW);
        worldIn.spawnEntity(note);
        return true;
    }

    @Override
    public Map<Integer, ModelResourceLocation> getItemModelLocations() {
        return HashMap.of(0, new ModelResourceLocation("minecraft:iron_block", "inventory"));
    }
}
