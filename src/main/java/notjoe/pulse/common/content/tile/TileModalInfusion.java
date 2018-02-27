package notjoe.pulse.common.content.tile;

import io.vavr.collection.Stream;
import io.vavr.collection.Vector;
import io.vavr.control.Option;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import notjoe.pulse.Pulse;
import notjoe.pulse.api.crafting.modal.ModalInfusionRecipe;
import notjoe.pulse.common.capability.ModalRune;
import notjoe.pulse.common.capability.NoteHandler;
import notjoe.pulse.common.capability.PulseCapabilities;
import notjoe.pulse.common.content.block.ModBlockContainer;
import notjoe.pulse.common.content.tile.base.AbstractModTileEntity;
import notjoe.pulse.common.util.Configuration;

public class TileModalInfusion extends AbstractModTileEntity implements ITickable {
    public static final Block TIER_1_PILLAR_BLOCK = Blocks.QUARTZ_BLOCK;
    public static final Block TIER_2_PILLAR_BLOCK = Blocks.DIAMOND_BLOCK;
    public static final int TIER_1_DISTANCE = 2;
    public static final int TIER_2_DISTANCE = 4;
    public static final int TIER_1_HEIGHT = 3;
    public static final int TIER_2_HEIGHT = 5;

    private final ModalInfusionWorker worker = new ModalInfusionWorker(this);

    private NoteHandler noteHandler = (note, facing) -> {
        if (worker.isCurrentlyCrafting()) {
            return;
        }

        Option<ModalInfusionRecipe<ItemStack>> potentialRecipe = Pulse.instance.getModalInfusionRegistry()
                .findMatchingRecipe(note.getNote(), getPedestalStacks(), getAeolianEssence(), getIonianEssence(),
                        getSetupTier());

        if (potentialRecipe.isDefined()) {
            worker.startCrafting(potentialRecipe.get());
        }
    };

    public Vector<ItemStack> getPedestalStacks() {

    }

    public int getAeolianEssence() {
        return 0;
    }

    public int getIonianEssence() {

    }

    int getSetupTier() {
        if (world.getBlockState(pos.up()).getBlock() == ModBlockContainer.PEDESTAL) {
            if (!getOffsetCorners(TIER_1_DISTANCE)
                    .forAll(cornerPos -> pillarExistsAt(cornerPos, TIER_1_PILLAR_BLOCK, TIER_1_HEIGHT))) {
                return 0;
            }

            if (!getOffsetCorners(TIER_2_DISTANCE)
                    .forAll(cornerPos -> pillarExistsAt(cornerPos, TIER_2_PILLAR_BLOCK, TIER_2_HEIGHT))) {
                return 1;
            }

            return 2;
        }

        return 0;
    }

    private boolean pillarExistsAt(BlockPos basePos, Block pillarBlock, int expectedHeight) {
        boolean pillarExists = Stream.range(0, expectedHeight)
                .forAll(i -> world.getBlockState(basePos.up(i)).getBlock() == pillarBlock);

        boolean runeExists = Option.of(world.getTileEntity(basePos.up(expectedHeight)))
                .map(tileEntity -> tileEntity.hasCapability(PulseCapabilities.MODAL_RUNE, null))
                .getOrElse(false);

        return pillarExists && runeExists;
    }

    private Vector<BlockPos> getOffsetCorners(int offsetAmount) {
        return Vector.of(-offsetAmount, offsetAmount)
                .flatMap(i -> Vector.of(-offsetAmount, offsetAmount)
                        .map(j -> pos.add(i, 0, j)));
    }

    public Vector<ModalRune> getRunes() {
        int tier = getSetupTier();
        Vector<ModalRune> runes = Vector.empty();

        if (tier >= 1) {
            runes = runes.appendAll(getOffsetCorners(TIER_1_HEIGHT)
                    .map(pillarPos -> pillarPos.up(TIER_1_HEIGHT))
                    .map(runePos -> world.getTileEntity(runePos)
                            .getCapability(PulseCapabilities.MODAL_RUNE, null))
                    .filter(rune -> rune.canFunction(world, pos)));
        }

        if (tier >= 2) {
            runes = runes.appendAll(getOffsetCorners(TIER_2_HEIGHT)
                    .map(pillarPos -> pillarPos.up(TIER_2_HEIGHT))
                    .map(runePos -> world.getTileEntity(runePos)
                            .getCapability(PulseCapabilities.MODAL_RUNE, null))
                    .filter(rune -> rune.canFunction(world, pos)));
        }

        return runes;
    }

    public int getEffectiveTime(Vector<ModalRune> runes) {
        return runes.foldLeft(Configuration.baseInfusionSpeed, (previous, rune) -> previous * rune.getSpeedMultiplier(world, pos)).intValue();
    }

    public double getEffectiveEfficiency(Vector<ModalRune> runes) {
        return runes.foldLeft(Configuration.baseInfusionEfficiency, (previous, rune) -> previous * rune.getEfficiencyMultiplier(world, pos));
    }

    public double getEffectiveInstability(Vector<ModalRune> runes) {
        return runes.foldLeft(Configuration.baseInfusionInstability, (previous, rune) -> previous * rune.getInstabilityMultiplier(world, pos));
    }

    public void onBlockActivated() {
        System.out.println(getSetupTier());
    }

    @Override
    public void writeCustomDataToNbt(NBTTagCompound compound) {

    }

    @Override
    public void readCustomDataFromNbt(NBTTagCompound compound) {

    }

    @Override
    public void update() {
        worker.tickCrafting();
    }

    public void finishCrafting(ModalInfusionRecipe<ItemStack> recipe) {

    }
}
