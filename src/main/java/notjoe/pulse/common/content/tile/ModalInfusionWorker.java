package notjoe.pulse.common.content.tile;

import io.vavr.collection.Vector;
import io.vavr.control.Option;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import notjoe.pulse.Pulse;
import notjoe.pulse.api.crafting.modal.ModalInfusionRecipe;
import notjoe.pulse.common.capability.ModalRune;
import notjoe.pulse.common.network.ModalInfusionRenderPacket;

import java.util.Random;

public class ModalInfusionWorker implements INBTSerializable<NBTTagCompound> {
    private static final Random RANDOM = new Random();

    private Option<ModalInfusionRecipe<ItemStack>> workingRecipe;
    private int craftingTicksRemaining;
    private boolean isCurrentlyCrafting;
    private boolean isWaitingForIngredients;

    private final TileModalInfusion tile;

    public ModalInfusionWorker(TileModalInfusion tile) {
        this.tile = tile;
        workingRecipe = Option.none();
        craftingTicksRemaining = 0;
        isCurrentlyCrafting = false;
        isWaitingForIngredients = false;
    }

    public void startCrafting(ModalInfusionRecipe<ItemStack> recipe) {
        invalidateCraftingState();
        workingRecipe = Option.of(recipe);
        craftingTicksRemaining = tile.getEffectiveTime(tile.getRunes());
        isCurrentlyCrafting = true;

        if (!canContinueCrafting() || !allIngredientsPresent()) {
            System.out.println("Worker can't craft");
            invalidateCraftingState();
        }
    }

    public Option<ResourceLocation> getRecipeName() {
        return workingRecipe.flatMap(Pulse.instance.getModalInfusionRegistry()::getName);
    }

    public void invalidateCraftingState() {
        workingRecipe = Option.none();
        craftingTicksRemaining = 0;
        isCurrentlyCrafting = false;
        isWaitingForIngredients = false;
    }

    public boolean canContinueCrafting() {
        return isCurrentlyCrafting &&
                (craftingTicksRemaining > 0) &&
                tile.hasPedestalAbove() &&
                workingRecipe.isDefined() &&
                (tile.getSetupTier() >= workingRecipe.get().getTierRequired()) &&
                (tile.getAeolianEssence() >= workingRecipe.get().getAeolianEssenceRequired()) &&
                (tile.getIonianEssence() >= workingRecipe.get().getIonianEssenceRequired());
    }

    public boolean allIngredientsPresent() {
        ModalInfusionRecipe<ItemStack> infusionRecipe = workingRecipe.get();
        return workingRecipe.get().allInputsPresent(tile.getPedestalStacks()) &&
                tile.getAeolianEssence() >= infusionRecipe.getAeolianEssenceRequired() &&
                tile.getIonianEssence() >= infusionRecipe.getIonianEssenceRequired();
    }

    public void tickCrafting() {
        if (!isCurrentlyCrafting) {
            return;
        }

        if (!canContinueCrafting()) {
            invalidateCraftingState();
            return;
        }

        if (!allIngredientsPresent()) {
            isWaitingForIngredients = true;
        }

        ModalInfusionRecipe<ItemStack> recipe = workingRecipe.get();
        Vector<ItemStack> pedestalStacks = tile.getPedestalStacks();
        Vector<ModalRune> runes = tile.getRunes();
        BlockPos tilePos = tile.getPos();

        if (RANDOM.nextDouble() < tile.getEffectiveInstability(runes) && craftingTicksRemaining % 10 == 0) {
            int destroyIndex = RANDOM.nextInt(pedestalStacks.length());
            pedestalStacks.get(destroyIndex).shrink(1);
            tile.onStackDestroyed(destroyIndex);
        }

        if (isWaitingForIngredients) {
            return;
        }

        if (craftingTicksRemaining % 5 == 0) {
            Pulse.instance.getNetworkWrapper().sendToAllAround(new ModalInfusionRenderPacket(tilePos,
                            ModalInfusionRenderPacket.Action.CRAFTING_TICK),
                    new NetworkRegistry.TargetPoint(tile.getWorld().provider.getDimension(),
                            tilePos.getX(), tilePos.getY(), tilePos.getZ(), 40));
        }

        craftingTicksRemaining--;

        if (craftingTicksRemaining <= 1) {
            pedestalStacks.forEach(stack -> {
                if (RANDOM.nextDouble() < tile.getEffectiveEfficiency(runes)) {
                    stack.shrink(1);
                }
            });

            tile.finishCrafting(recipe);
            Pulse.instance.getNetworkWrapper().sendToAllAround(new ModalInfusionRenderPacket(tilePos,
                            ModalInfusionRenderPacket.Action.FINISH_EFFECT),
                    new NetworkRegistry.TargetPoint(tile.getWorld().provider.getDimension(),
                            tilePos.getX(), tilePos.getY(), tilePos.getZ(), 40));
            invalidateCraftingState();
        }
    }

    public boolean isCurrentlyCrafting() {
        return isCurrentlyCrafting;
    }

    public Option<ModalInfusionRecipe<ItemStack>> getWorkingRecipe() {
        return workingRecipe;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound compound = new NBTTagCompound();
        compound.setString("recipe", getRecipeName()
                .map(ResourceLocation::toString)
                .getOrElse(""));
        compound.setInteger("ticks_remaining", craftingTicksRemaining);
        compound.setBoolean("waiting_for_ingredients", isWaitingForIngredients);
        compound.setBoolean("is_currently_crafting", isCurrentlyCrafting);
        return compound;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        String recipeLocation = nbt.getString("recipe");
        workingRecipe = Pulse.instance.getModalInfusionRegistry().getByName(new ResourceLocation(recipeLocation));
        craftingTicksRemaining = nbt.getInteger("ticks_remaining");
        isWaitingForIngredients = nbt.getBoolean("waiting_for_ingredients");
        isCurrentlyCrafting = nbt.getBoolean("is_currently_crafting");
    }
}
