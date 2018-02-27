package notjoe.pulse.common.content.item;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import notjoe.pulse.common.content.item.base.AbstractModItem;

public class ItemModalDust extends AbstractModItem {
    private final Block transmutationBlock;
    private final Block transmutationResult;

    public ItemModalDust(String resourceName, Block transmutationBlock, BlockFluidClassic transmutationResult) {
        super("resource_" + resourceName + "_dust");
        this.transmutationBlock = transmutationBlock;
        this.transmutationResult = transmutationResult;
    }

    @Override
    public boolean onEntityItemUpdate(EntityItem entityItem) {
        World world = entityItem.getEntityWorld();
        BlockPos entityPos = entityItem.getPosition();
        Block blockIn = world.getBlockState(entityPos).getBlock();
        Block blockBelow = world.getBlockState(entityPos.down()).getBlock();

        if (blockIn == Blocks.WATER && blockBelow == transmutationBlock) {
            world.setBlockState(entityPos, transmutationResult.getDefaultState().withProperty(BlockFluidClassic.LEVEL, 0));
            entityItem.getItem().shrink(1);
        }

        return false;
    }
}
