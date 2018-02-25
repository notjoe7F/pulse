package notjoe.pulse.common.capability;

import notjoe.pulse.api.concept.HarmonicEnergyType;

import java.util.concurrent.Callable;

public class HarmonicEnergyProviderFactory implements Callable<HarmonicEnergyProvider> {
    @Override
    public HarmonicEnergyProvider call() throws Exception {
        return new HarmonicEnergyProvider() {
            @Override
            public int getAvailableEnergy() {
                return 0;
            }

            @Override
            public HarmonicEnergyType getEnergyType() {
                return null;
            }
        };
    }
}
