package notjoe.pulse.common.content.tile;

import io.vavr.collection.Stream;
import io.vavr.collection.Vector;
import io.vavr.control.Option;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import notjoe.pulse.Pulse;
import notjoe.pulse.api.crafting.modal.ModalInfusionRecipe;
import notjoe.pulse.api.musictheory.Note;
import notjoe.pulse.client.particle.PulseParticles;
import notjoe.pulse.common.capability.ModalRune;
import notjoe.pulse.common.capability.NoteHandler;
import notjoe.pulse.common.capability.PulseCapabilities;
import notjoe.pulse.common.content.block.ModBlockContainer;
import notjoe.pulse.common.content.tile.base.AbstractModTileEntity;
import notjoe.pulse.common.util.Configuration;
import notjoe.pulse.common.util.Geometry;

import javax.annotation.Nullable;
import java.awt.*;

// I generally put a :( next to things that make me sad (like having to return null), but I feel like this entire class
// deserves one big :(.
public class TileModalInfusion extends AbstractModTileEntity implements ITickable {
    public static final Block TIER_1_PILLAR_BLOCK = Blocks.QUARTZ_BLOCK;
    public static final Block TIER_2_PILLAR_BLOCK = Blocks.DIAMOND_BLOCK;
    public static final int TIER_1_DISTANCE = 2;
    public static final int TIER_2_DISTANCE = 4;
    public static final int TIER_1_HEIGHT = 3;
    public static final int TIER_2_HEIGHT = 6;

    private ModalInfusionWorker worker = new ModalInfusionWorker(this);
    private Note lastNote = Note.C_LOW;

    private NoteHandler noteHandler = (note, facing) -> getWorldWrapper().ifServer(() -> {
        if (worker.isCurrentlyCrafting()) {
            return;
        }

        Option<ModalInfusionRecipe<ItemStack>> potentialRecipe = Pulse.instance.getModalInfusionRegistry()
                .findMatchingRecipe(note.getNote(), getPedestalStacks(), getAeolianEssence(), getIonianEssence(),
                        getSetupTier());

        if (potentialRecipe.isDefined()) {
            lastNote = note.getNote();
            worker.startCrafting(potentialRecipe.get());
        }
    });

    public Vector<BlockPos> getPedestalPositions() {
        int range = Configuration.modalInfusionPedestalRange;
        Vector<BlockPos> pedestalPositions = Vector.empty();

        // This COULD be done by flatMapping a bunch of streams, but although it requires updating a variable, this
        // nested-loop approach seems much more readable.

        for (int i = -range; i <= range; i++) {
            for (int j = -range; j <= range; j++) {
                for (int k = -range; k <= range; k++) {
                    BlockPos checkPos = pos.add(i, j, k);
                    TileEntity checkTile = world.getTileEntity(checkPos);
                    if (checkTile instanceof TilePedestal && !checkPos.equals(pos.up())) {
                        pedestalPositions = pedestalPositions.append(checkPos);
                    }
                }
            }
        }

        return pedestalPositions;
    }

    public Vector<ItemStack> getPedestalStacks() {
        return getPedestalPositions()
                .map(pedestalPos -> world.getTileEntity(pedestalPos)
                        .getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)
                        .getStackInSlot(0));
    }

    public int getAeolianEssence() {
        return 0;
    }

    public int getIonianEssence() {
        return 0;
    }

    public int getSetupTier() {
        if (hasPedestalAbove()) {
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

    public boolean hasPedestalAbove() {
        TileEntity tileAbove = world.getTileEntity(pos.up());
        return tileAbove != null && tileAbove instanceof TilePedestal;
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

    public Vector<BlockPos> getRunePositions() {
        int tier = getSetupTier();
        Vector<BlockPos> runes = Vector.empty();

        if (tier >= 1) {
            runes = runes.appendAll(getOffsetCorners(TIER_1_DISTANCE)
                    .map(pillarPos -> pillarPos.up(TIER_1_HEIGHT))
                    .filter(runePos -> world.getTileEntity(runePos).hasCapability(PulseCapabilities.MODAL_RUNE, null)));
        }

        if (tier >= 2) {
            runes = runes.appendAll(getOffsetCorners(TIER_2_DISTANCE)
                    .map(pillarPos -> pillarPos.up(TIER_2_HEIGHT))
                    .filter(runePos -> world.getTileEntity(runePos).hasCapability(PulseCapabilities.MODAL_RUNE, null)));
        }

        return runes;
    }

    public Vector<ModalRune> getRunes() {
        return getRunePositions()
                .map(checkPos -> world.getTileEntity(checkPos).getCapability(PulseCapabilities.MODAL_RUNE, null))
                .filter(rune -> rune.canFunction(world, pos));
    }

    public int getEffectiveTime(Vector<ModalRune> runes) {
        return runes.foldLeft(Configuration.baseInfusionSpeed, (previous, rune) -> previous * rune.getSpeedMultiplier(world, pos)).intValue();
    }

    public double getEffectiveEfficiency(Vector<ModalRune> runes) {
        return 1.0 / runes.foldLeft(Configuration.baseInfusionEfficiency, (previous, rune) -> previous * rune.getEfficiencyMultiplier(world, pos));
    }

    public double getEffectiveInstability(Vector<ModalRune> runes) {
        return Math.tanh(0.15 * (runes.foldLeft(Configuration.baseInfusionInstability, (previous, rune) -> previous * rune.getInstabilityMultiplier(world, pos)) - 1));
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == PulseCapabilities.NOTE_HANDLER;
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (hasCapability(capability, facing)) {
            return (T) noteHandler;
        }

        return super.getCapability(capability, facing);
    }

    @Override
    public void writeCustomDataToNbt(NBTTagCompound compound) {
        compound.setTag("worker", worker.serializeNBT());
        compound.setInteger("last_note", lastNote.ordinal());
    }

    @Override
    public void readCustomDataFromNbt(NBTTagCompound compound) {
        worker.deserializeNBT(compound.getCompoundTag("worker"));
        lastNote = Note.values()[compound.getInteger("last_note")];
    }

    @Override
    public void update() {
        getWorldWrapper().ifServer(worker::tickCrafting);
    }

    public void finishCrafting(ModalInfusionRecipe<ItemStack> recipe) {
        TileEntity pedestalAbove = world.getTileEntity(pos.up());
        if (pedestalAbove instanceof TilePedestal) {
            IItemHandler handler = pedestalAbove.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
            ItemStack leftover = handler.insertItem(0, recipe.getOutput(), false);
            getWorldWrapper().tossItemStack(leftover, pos.up(2), false);
        }
    }

    public void onCraftingRenderTick() {
        drawRuneParticles();
        drawPedestalToRuneParticles();
    }

    public void drawRuneParticles() {
        getRunePositions().forEach(runePos -> {
            world.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE,
                    runePos.offset(EnumFacing.random(RANDOM)).getX() + 0.5,
                    runePos.offset(EnumFacing.random(RANDOM)).getY() + 0.5,
                    runePos.offset(EnumFacing.random(RANDOM)).getZ() + 0.5,
                    RANDOM.nextGaussian() * 0.2,
                    RANDOM.nextGaussian() * 0.2,
                    RANDOM.nextGaussian() * 0.2);
        });
    }

    public void drawPedestalToRuneParticles() {
        BlockPos pedestalPos = pos.up();
        Vec3d pedestalVec = new Vec3d(pedestalPos).addVector(0.5, 0.5, 0.5);
        getRunePositions().forEach(runePos -> {
            Vec3d runeVec = new Vec3d(runePos).addVector(0.5, 0.5, 0.5);
            Geometry.nPointsBetween(pedestalVec, runeVec, Configuration.infusionRenderSteps).forEach(drawPos ->
                    PulseParticles.renderNote(world, drawPos.x, drawPos.y, drawPos.z, 0d, 0d, 0d,
                    lastNote.getColor(), 0.4));
        });
    }

    public void onStackDestroyed(int destroyIndex) {
        BlockPos affectedPedestal = getPedestalPositions().get(destroyIndex);
        world.createExplosion(null, affectedPedestal.getX(), affectedPedestal.getY(), affectedPedestal.getZ(), 0f, false);
        ((TilePedestal) world.getTileEntity(affectedPedestal)).syncToClient();
    }

    public void finishCraftingClient() {
        for (int i = 0; i < Configuration.infusionRenderSteps; i++) {
            world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                    RANDOM.nextGaussian(), Math.abs(RANDOM.nextGaussian()), RANDOM.nextGaussian());
        }
    }
}
