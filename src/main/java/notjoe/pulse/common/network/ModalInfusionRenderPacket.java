package notjoe.pulse.common.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import notjoe.pulse.common.content.tile.TileModalInfusion;

public class ModalInfusionRenderPacket implements IMessage {
    private BlockPos pos;
    private Action action;

    public ModalInfusionRenderPacket(BlockPos pos, Action action) {
        this.pos = pos;
        this.action = action;
    }

    public ModalInfusionRenderPacket() {
        pos = new BlockPos(0, 0, 0);
        action = Action.CRAFTING_TICK;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
        action = Action.values()[buf.readInt()];
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(pos.getX());
        buf.writeInt(pos.getY());
        buf.writeInt(pos.getZ());
        buf.writeInt(action.ordinal());
    }

    public BlockPos getPos() {
        return pos;
    }

    public Action getAction() {
        return action;
    }

    public static class Handler implements IMessageHandler<ModalInfusionRenderPacket, IMessage> {

        @Override
        public IMessage onMessage(ModalInfusionRenderPacket message, MessageContext ctx) {
            Minecraft minecraft = Minecraft.getMinecraft();
            minecraft.addScheduledTask(() -> {
                World world = minecraft.world;
                TileEntity drawTile = world.getTileEntity(message.getPos());
                if (drawTile instanceof TileModalInfusion) {
                    if (message.getAction() == Action.CRAFTING_TICK) {
                        ((TileModalInfusion) drawTile).onCraftingRenderTick();
                    } else if (message.getAction() == Action.FINISH_EFFECT) {
                        ((TileModalInfusion) drawTile).finishCraftingClient();
                    }
                }
            });

            return null;
        }
    }

    public enum Action {
        CRAFTING_TICK,
        FINISH_EFFECT
    }
}
