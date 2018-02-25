package notjoe.pulse.common.content.tile;

import io.vavr.collection.Vector;
import io.vavr.control.Option;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import notjoe.pulse.Pulse;
import notjoe.pulse.api.crafting.melodic.MelodicCraftingRecipe;
import notjoe.pulse.api.crafting.melodic.MelodicCraftingRegistry;
import notjoe.pulse.common.capability.NoteHandler;
import notjoe.pulse.common.capability.PulseCapabilities;
import notjoe.pulse.common.content.tile.base.AbstractModTileEntity;
import notjoe.pulse.common.content.tile.base.ExtendedItemStackHandler;

import javax.annotation.Nullable;

public class TileMelodicCrafting extends AbstractModTileEntity {
    private int gridSize;
    private ExtendedItemStackHandler stackHandler;
    private NoteHandler noteHandler = (note, facing) -> getWorldWrapper().ifServer(() -> {
        MelodicCraftingRegistry melodicCraftingRegistry = Pulse.instance.getMelodicCraftingRegistry();
        Option<MelodicCraftingRecipe<ItemStack>> recipeMatch =
                melodicCraftingRegistry.findMatchingRecipe(note.getMelodyWithCurrent(), stackHandler.getItemStacks(), gridSize);
        recipeMatch.forEach(this::handleRecipeMatch);
    });

    private void handleRecipeMatch(MelodicCraftingRecipe<ItemStack> recipe) {
        Vector<ItemStack> stacks = stackHandler.getItemStacks();
        int maxBatch = stacks.filter(stack -> !stack.isEmpty()).map(ItemStack::getCount).sorted().head();
        getWorldWrapper().tossMultipleItemStacks(recipe.getOutput(), maxBatch, pos.up(), true);
        stackHandler.decrementAll(maxBatch);
    }

    public TileMelodicCrafting() {
        gridSize = 3;
        stackHandler = new ExtendedItemStackHandler(gridSize * gridSize, this::markDirty);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return Vector.of(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, PulseCapabilities.NOTE_HANDLER).contains(capability);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return (T) stackHandler;
        } else if (capability == PulseCapabilities.NOTE_HANDLER) {
            return (T) noteHandler;
        }

        return null; // :(
    }

    @Override
    public void writeCustomDataToNbt(NBTTagCompound compound) {
        compound.setTag("inventory", stackHandler.serializeNBT());
    }

    @Override
    public void readCustomDataFromNbt(NBTTagCompound compound) {
        gridSize = 3;
        stackHandler.deserializeNBT(compound.getCompoundTag("inventory"));
    }
}
