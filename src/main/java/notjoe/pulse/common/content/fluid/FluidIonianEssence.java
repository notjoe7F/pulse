package notjoe.pulse.common.content.fluid;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import notjoe.pulse.Pulse;

import java.awt.*;

public class FluidIonianEssence extends Fluid {
    public static final ResourceLocation STILL_TEXTURE = new ResourceLocation(Pulse.ID, "fluids/essence_still");
    public static final ResourceLocation FLOW_TEXTURE = new ResourceLocation(Pulse.ID, "fluids/essence_flow");

    public static final FluidIonianEssence FLUID_IONIAN_ESSENCE = new FluidIonianEssence();

    public FluidIonianEssence() {
        super("ionian_essence", STILL_TEXTURE, FLOW_TEXTURE, new Color(94, 222, 237));
        setTemperature(500);
        setDensity(1000);
        setLuminosity(10);
        setViscosity(800);
        FluidRegistry.registerFluid(this);
        FluidRegistry.addBucketForFluid(this);
    }
}
