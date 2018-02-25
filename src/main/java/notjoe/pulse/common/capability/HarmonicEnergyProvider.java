package notjoe.pulse.common.capability;

import notjoe.pulse.api.concept.HarmonicEnergyType;

public interface HarmonicEnergyProvider {
    int getAvailableEnergy();
    HarmonicEnergyType getEnergyType();
}
