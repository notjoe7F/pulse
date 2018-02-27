package notjoe.pulse.common.content;

import io.vavr.collection.Vector;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fluids.BlockFluidClassic;
import notjoe.pulse.Pulse;
import notjoe.pulse.common.content.block.*;
import notjoe.pulse.common.content.block.base.SimpleModalRune;
import notjoe.pulse.common.content.fluid.BlockFluidAeolianEssence;
import notjoe.pulse.common.content.fluid.BlockFluidIonianEssence;
import notjoe.pulse.common.content.item.ItemGuidebook;
import notjoe.pulse.common.content.item.ItemModalDust;
import notjoe.pulse.common.content.item.ItemProcessableMaterial;
import notjoe.pulse.common.content.item.ItemTuningFork;

public final class ModContent {
    private ModContent() { }

    public static final Vector<Block> ALL_BLOCKS = Vector.of(
            new BlockTest(),
            new BlockPitcherPentatonic(),
            new BlockPitcherMajor(),
            new BlockPitcherMinor(),
            new BlockPitcherChromatic(),
            new BlockMelodicCrafting(),
            new BlockPedestal(),
            new BlockHarmonicTransmutation(),
            new SimpleModalRune("basic", 1, 1, 1),
            new SimpleModalRune("speed", 0.9, 1.1, 0.7),
            new SimpleModalRune("stable", 1.2, 0.8, 0.9),
            new SimpleModalRune("efficient", 1.4, 0.9, 1.4),
            new BlockModalInfusion(),
            new BlockCrystal(),
            new BlockCrystalConsonant(),
            // insert dissonant crystal here
            new BlockFluidAeolianEssence(),
            new BlockFluidIonianEssence()
    );

    public static final Vector<Item> ALL_ITEMS = Vector.of(
            new ItemTuningFork(),
            new ItemGuidebook(),
            new ItemProcessableMaterial("aeolian", Vector.of("nugget", "ingot")),
            new ItemProcessableMaterial("ionian", Vector.of("nugget", "ingot")),
            new ItemModalDust("aeolian", Blocks.RED_NETHER_BRICK, (BlockFluidClassic) ModBlockContainer.FLUID_AEOLIAN_ESSENCE),
            new ItemModalDust("ionian", Blocks.PACKED_ICE, (BlockFluidClassic) ModBlockContainer.FLUID_IONIAN_ESSENCE)
    );

    public static final SoundEvent TUNING_FORK_SOUND = new SoundEvent(new ResourceLocation(Pulse.ID, "tuning_fork"));
}
