package notjoe.pulse.common.content.block.base;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import notjoe.pulse.common.content.tile.TileModalRune;

import javax.annotation.Nullable;
import java.util.List;

public class SimpleModalRune extends AbstractModBlock {
    private final double speedMultiplier;
    private final double instabilityMultiplier;
    private final double efficiencyMultiplier;

    public SimpleModalRune(String runeName, double speedMultiplier, double instabilityMultiplier, double efficiencyMultiplier) {
        super("modal_rune_" + runeName, Material.ROCK);
        this.speedMultiplier = speedMultiplier;
        this.instabilityMultiplier = instabilityMultiplier;
        this.efficiencyMultiplier = efficiencyMultiplier;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileModalRune(speedMultiplier, instabilityMultiplier, efficiencyMultiplier);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced) {
        tooltip.add(I18n.format("pulse.rune.speed", speedMultiplier));
        tooltip.add(I18n.format("pulse.rune.instability", instabilityMultiplier));
        tooltip.add(I18n.format("pulse.rune.efficiency", efficiencyMultiplier));
    }
}
