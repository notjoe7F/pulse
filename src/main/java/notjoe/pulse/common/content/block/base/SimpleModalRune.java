package notjoe.pulse.common.content.block.base;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import notjoe.pulse.common.content.tile.TileModalRune;

import javax.annotation.Nullable;

public class SimpleModalRune extends AbstractModBlock {
    private final double speedMultiplier;
    private final double stabilityMultiplier;
    private final double efficiencyMultiplier;

    public SimpleModalRune(String runeName, double speedMultiplier, double stabilityMultiplier, double efficiencyMultiplier) {
        super("modal_rune_" + runeName, Material.ROCK);
        this.speedMultiplier = speedMultiplier;
        this.stabilityMultiplier = stabilityMultiplier;
        this.efficiencyMultiplier = efficiencyMultiplier;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileModalRune(speedMultiplier, stabilityMultiplier, efficiencyMultiplier);
    }
}
