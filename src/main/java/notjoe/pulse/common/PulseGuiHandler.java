package notjoe.pulse.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import notjoe.pulse.client.gui.GuiMelodicCrafting;
import notjoe.pulse.common.content.container.ContainerMelodicCrafting;
import notjoe.pulse.common.content.tile.TileMelodicCrafting;

import javax.annotation.Nullable;

import static io.vavr.API.*;
import static io.vavr.Predicates.instanceOf;
import static io.vavr.Predicates.isNull;

public class PulseGuiHandler implements IGuiHandler {
    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
        return Match(tile).of(
                Case($(isNull()), () -> null),
                Case($(instanceOf(TileMelodicCrafting.class)),
                        new ContainerMelodicCrafting((TileMelodicCrafting) tile, player))
        );
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
        return Match(tile).of(
                Case($(isNull()), () -> null),
                Case($(instanceOf(TileMelodicCrafting.class)),
                        new GuiMelodicCrafting((TileMelodicCrafting) tile, player))
        );
    }
}
