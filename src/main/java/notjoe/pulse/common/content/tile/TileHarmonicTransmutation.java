package notjoe.pulse.common.content.tile;

import io.vavr.collection.Stream;
import io.vavr.control.Option;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import notjoe.pulse.Pulse;
import notjoe.pulse.api.concept.HarmonicEnergyGetter;
import notjoe.pulse.api.concept.HarmonicEnergyType;
import notjoe.pulse.api.crafting.harmonic.HarmonicTransmutationRecipe;
import notjoe.pulse.common.content.block.ModBlockContainer;
import notjoe.pulse.common.content.tile.base.AbstractModTileEntity;

public class TileHarmonicTransmutation extends AbstractModTileEntity {
    public void onBlockActivated(EntityPlayer player) {
        getWorldWrapper().ifServer(() -> {
            Option<TilePedestal> potentialPedestal = getInputPedestal();
            if (!potentialPedestal.isDefined()) {
                return;
            }

            handleRecipe(player, potentialPedestal.get());
        });
    }

    private void handleRecipe(EntityPlayer player, TilePedestal pedestal) {
        ItemStack input = pedestal.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).getStackInSlot(0);
        ItemStack catalyst = player.getHeldItemMainhand();

        Option<HarmonicTransmutationRecipe<ItemStack>> potentialRecipe =
                Pulse.instance.getHarmonicTransmutationRegistry()
                        .findMatchingRecipe(input, catalyst,
                                getAvailableConsonantEnergy(), getAvailableDissonantEnergy());

        if (!potentialRecipe.isDefined()) {
            return;
        }

        HarmonicTransmutationRecipe<ItemStack> recipe = potentialRecipe.get();
        ItemStack output = recipe.getOutput().copy();

        catalyst.shrink(recipe.getCatalystCount());
        player.inventory.markDirty();

        input.shrink(recipe.getInputCount());

        boolean couldGiveToPlayer = player.addItemStackToInventory(output);
        if (!couldGiveToPlayer) {
            getWorldWrapper().tossItemStack(output, pos.up(), true);
        }
    }

    private Option<TilePedestal> getInputPedestal() {
        return Stream.of(EnumFacing.values())
                .find(face -> world.getBlockState(pos.offset(face)).getBlock() == ModBlockContainer.PEDESTAL)
                .map(face -> (TilePedestal) world.getTileEntity(pos.offset(face)));
    }

    private int getAvailableConsonantEnergy() {
        return new HarmonicEnergyGetter(world).getAvailableEnergy(pos, HarmonicEnergyType.CONSONANT);
    }

    private int getAvailableDissonantEnergy() {
        return new HarmonicEnergyGetter(world).getAvailableEnergy(pos, HarmonicEnergyType.DISSONANT);
    }

    @Override
    public void writeCustomDataToNbt(NBTTagCompound compound) {
        // NO-OP, nothing to store.
    }

    @Override
    public void readCustomDataFromNbt(NBTTagCompound compound) {
        // NO-OP, nothing to read.
    }
}
