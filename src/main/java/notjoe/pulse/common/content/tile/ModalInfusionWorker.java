package notjoe.pulse.common.content.tile;

import io.vavr.collection.Vector;
import io.vavr.control.Option;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;
import notjoe.pulse.Pulse;
import notjoe.pulse.api.crafting.modal.ModalInfusionRecipe;
import notjoe.pulse.common.capability.ModalRune;

import java.util.Random;

public class ModalInfusionWorker implements INBTSerializable<NBTTagCompound> {
    private static final Random RANDOM = new Random();

    private Option<ModalInfusionRecipe<ItemStack>> workingRecipe;
    private int craftingTicksRemaining;
    private boolean isCurrentlyCrafting;
    private boolean isWaitingForIngredients;

    private final TileModalInfusion tile;

    public ModalInfusionWorker(TileModalInfusion tile) {
        workingRecipe = Option.none();
        craftingTicksRemaining = 0;
        isCurrentlyCrafting = false;
    }

    public void startCrafting(ModalInfusionRecipe<ItemStack> recipe) {
        invalidateCraftingState();
        workingRecipe = Option.of(recipe);
        craftingTicksRemaining = tile.getEffectiveTime(tile.getRunes());
        isCurrentlyCrafting = true;
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
                workingRecipe.isDefined() &&
                (tile.getSetupTier() > workingRecipe.get().getTierRequired()) &&
                (tile.getAeolianEssence() >= workingRecipe.get().getAeolianEssenceRequired()) &&
                (tile.getIonianEssence() >= workingRecipe.get().getIonianEssenceRequired());
    }

    public boolean allIngredientsPresent() {
        return workingRecipe.get().allInputsPresent(tile.getPedestalStacks());
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

        if (RANDOM.nextDouble() > tile.getEffectiveInstability(runes)) {
            pedestalStacks.get(RANDOM.nextInt(pedestalStacks.length())).shrink(1);
        }

        if ((craftingTicksRemaining > 40) && !isWaitingForIngredients) {
            craftingTicksRemaining--;
        } else if (craftingTicksRemaining <= 1) {
            pedestalStacks.forEach(stack -> {
                if (RANDOM.nextDouble() < tile.getEffectiveEfficiency(runes)) {
                    stack.shrink(1);
                }
            });

            tile.finishCrafting(recipe);
            invalidateCraftingState();
        }
    }

    public boolean isCurrentlyCrafting() {
        return isCurrentlyCrafting;
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
