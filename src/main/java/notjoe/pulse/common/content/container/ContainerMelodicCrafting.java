package notjoe.pulse.common.content.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.items.CapabilityItemHandler;
import notjoe.pulse.common.content.container.base.AbstractModContainer;
import notjoe.pulse.common.content.tile.TileMelodicCrafting;

public class ContainerMelodicCrafting extends AbstractModContainer {
    private final TileMelodicCrafting tile;

    public ContainerMelodicCrafting(TileMelodicCrafting tile, EntityPlayer player) {
        super(tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null), player, 18, 18);
        this.tile = tile;
        addStackHandlerSlots(62, 22, 3, 3, 0);
        addPlayerInventory(8, 94);
        addPlayerHotbar(8, 152);
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return tile.canPlayerInteract(playerIn);
    }
}
