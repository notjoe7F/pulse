package notjoe.pulse.common.content;

import io.vavr.collection.Vector;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import notjoe.pulse.Pulse;
import notjoe.pulse.common.content.block.*;
import notjoe.pulse.common.content.block.base.SimpleModalRune;
import notjoe.pulse.common.content.item.ItemGuidebook;
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
            new BlockModalInfusion()
    );

    public static final Vector<Item> ALL_ITEMS = Vector.of(
            new ItemTuningFork(),
            new ItemGuidebook()
    );

    public static final SoundEvent TUNING_FORK_SOUND = new SoundEvent(new ResourceLocation(Pulse.ID, "tuning_fork"));
}
