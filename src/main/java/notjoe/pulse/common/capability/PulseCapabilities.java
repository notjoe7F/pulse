package notjoe.pulse.common.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public final class PulseCapabilities {
    private PulseCapabilities() { }

    @CapabilityInject(NoteHandler.class)
    public static final Capability<NoteHandler> NOTE_HANDLER = null;

    @CapabilityInject(HarmonicEnergyProvider.class)
    public static final Capability<HarmonicEnergyProvider> HARMONIC_ENERGY_PROVIDER = null;
}
