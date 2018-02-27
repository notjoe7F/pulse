package notjoe.pulse.common.content.fluid;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import notjoe.pulse.Pulse;

import java.awt.*;

public class FluidAeolianEssence extends Fluid {
    public static final ResourceLocation STILL_TEXTURE = new ResourceLocation(Pulse.ID, "fluids/essence_still");
    public static final ResourceLocation FLOW_TEXTURE = new ResourceLocation(Pulse.ID, "fluids/essence_flow");

    public static final FluidAeolianEssence FLUID_AEOLIAN_ESSENCE = new FluidAeolianEssence();

    public FluidAeolianEssence() {
        super("aeolian_essence", STILL_TEXTURE, FLOW_TEXTURE, new Color(237, 54, 86));
        setTemperature(1000);
        setDensity(1100);
        setLuminosity(15);
        setViscosity(1000);
        setUnlocalizedName("pulse.aeolian_essence");
        FluidRegistry.registerFluid(this);
        FluidRegistry.addBucketForFluid(this);
    }
}
