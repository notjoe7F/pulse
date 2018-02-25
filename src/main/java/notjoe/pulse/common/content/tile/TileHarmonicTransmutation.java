package notjoe.pulse.common.content.tile;

import io.vavr.collection.Stream;
import io.vavr.control.Option;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import notjoe.pulse.Pulse;
import notjoe.pulse.api.crafting.harmonic.HarmonicTransmutationRecipe;
import notjoe.pulse.common.content.block.ModBlockContainer;
import notjoe.pulse.common.content.tile.base.AbstractModTileEntity;

public class TileHarmonicTransmutation extends AbstractModTileEntity {
    private boolean onBlockActivated(EntityPlayer player) {
        Option<TilePedestal> potentialPedestal = getInputPedestal();
        if (!potentialPedestal.isDefined()) {
            return false;
        }

        TilePedestal pedestal = potentialPedestal.get();
        ItemStack input = pedestal.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).getStackInSlot(0);
        ItemStack catalyst = player.getHeldItemMainhand();

        Option<HarmonicTransmutationRecipe<ItemStack>> potentialRecipe = Pulse.instance.getHarmonicTransmutationRegistry().findMatchingRecipe(input, catalyst, 0, 0);

        return true;
    }

    private Option<TilePedestal> getInputPedestal() {
        return Stream.of(EnumFacing.values())
                .find(face -> world.getBlockState(pos.offset(face)) == ModBlockContainer.HARMONIC_TRANSMUTATION)
                .map(face -> (TilePedestal) world.getTileEntity(pos.offset(face)));
    }

    @Override
    public void writeCustomDataToNbt(NBTTagCompound compound) {

    }

    @Override
    public void readCustomDataFromNbt(NBTTagCompound compound) {

    }
}
