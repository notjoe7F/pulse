package notjoe.pulse.common.util;

import net.minecraftforge.common.config.Config;
import notjoe.pulse.Pulse;

@Config(modid = Pulse.ID)
public final class Configuration {
    private Configuration() { }

    @Config.Comment("Sets the time (in ticks) that the tuning fork should cooldown for.")
    public static int tuningForkCooldownTicks = 15;

    @Config.Comment({"Sets the maximum time (in ticks) that a Note can exist for.",
                     "Setting this too low might make the mod unplayable."})
    public static int maxTuningForkTicks = 30 * 20;

    @Config.Comment({"Sets the maximum number of transfers a Note is allowed to do.",
                     "Setting this too low might make some recipes impossible."})
    public static int maxNoteTransfers = 15;

    @Config.Comment({"Sets the number of particles rendered for a Note per block."})
    public static int noteRenderSteps = 5;

    @Config.Comment({"Sets the number of extra enchantment levels required for the Arcane Catalog to enchant",
                     "an item. For example, if this is set to 1, then it will require 6 stored levels of sharpness",
                     "to give a sword sharpness 5."})
    public static short arcaneCatalogCost = 1;
}
