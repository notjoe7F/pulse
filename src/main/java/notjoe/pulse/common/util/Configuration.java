package notjoe.pulse.common.util;

import net.minecraftforge.common.config.Config;
import notjoe.pulse.Pulse;

// Is there a way to make these fields final? This is kinda gross
@Config(modid = Pulse.ID)
public final class Configuration {

    private Configuration() { }

    @Config.Comment("Sets the time (in ticks) that the tuning fork should cooldown for.")
    public static int tuningForkCooldownTicks = 15;

    @Config.Comment({"Sets the maximum time (in ticks) that a Note can exist for.",
                     "Setting this too low might make the mod unplayable."})
    public static int maxNoteTicks = 600;

    @Config.Comment({"Sets the maximum number of transfers a Note is allowed to make.",
                     "Setting this too low might make some recipes impossible."})
    public static int maxNoteTransfers = 15;

    @Config.Comment({"Sets the number of particles rendered for a Note per block."})
    public static int noteRenderSteps = 5;

    @Config.Comment({"Sets the number of particles rendered for each event during Modal Infusion."})
    public static int infusionRenderSteps = 18;

    @Config.Comment({"Sets the maximum distance in any direction a pedestal can be away from the center of a",
                     "Modal Infusion setup."})
    public static int modalInfusionPedestalRange = 8;

    @Config.Comment({"Sets the base infusion speed for Modal Infusion recipes in ticks."})
    @Config.RangeDouble(min = 1)
    public static double baseInfusionSpeed = 300;

    @Config.Comment({"Sets the base efficiency for Modal Infusion recipes. Chance of an item being consumed",
                     "is 1/efficiency."})
    @Config.RangeDouble(min = 1)
    public static double baseInfusionEfficiency = 1;

    @Config.Comment({"Sets the base stability for Modal Infusion recipes. Chance of an item being destroyed",
                     "prematurely is tanh(0.15 * (instability - 1))"})
    @Config.RangeDouble(min = 1)
    public static double baseInfusionInstability = 1;
}
