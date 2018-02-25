package notjoe.pulse.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import notjoe.pulse.Pulse;
import notjoe.pulse.client.gui.base.AbstractModGuiContainer;
import notjoe.pulse.common.content.container.ContainerMelodicCrafting;
import notjoe.pulse.common.content.tile.TileMelodicCrafting;

@SideOnly(Side.CLIENT)
public class GuiMelodicCrafting extends AbstractModGuiContainer {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(Pulse.ID, "textures/gui/melodic_crafting.png");

    public GuiMelodicCrafting(TileMelodicCrafting tile, EntityPlayer player) {
        super(new ContainerMelodicCrafting(tile, player), BACKGROUND, "melodic_crafting", 176, 176);
    }
}
