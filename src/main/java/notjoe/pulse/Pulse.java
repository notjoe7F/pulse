package notjoe.pulse;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import notjoe.pulse.api.crafting.harmonic.HarmonicTransmutationRegistry;
import notjoe.pulse.api.crafting.melodic.MelodicCraftingRegistry;
import notjoe.pulse.api.crafting.modal.ModalInfusionRegistry;
import notjoe.pulse.common.CommonProxy;
import notjoe.pulse.common.content.GuidebookEntries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings({"WeakerAccess", "unused"})
@Mod(modid = Pulse.ID, version = Pulse.VERSION, name = Pulse.NAME)
public final class Pulse {
    public static final String ID = "pulse";
    public static final String NAME = "Pulse";
    public static final String VERSION = "@VERSION@";

    @SidedProxy(clientSide = "notjoe.pulse.client.ClientProxy", serverSide = "notjoe.pulse.common.CommonProxy")
    public static CommonProxy proxy;

    @Mod.Instance(Pulse.ID)
    public static Pulse instance;

    static {
        FluidRegistry.enableUniversalBucket();
    }

    private Logger logger = LogManager.getLogger();
    private GuidebookEntries guidebookEntries = new GuidebookEntries();
    private MelodicCraftingRegistry melodicCraftingRegistry = new MelodicCraftingRegistry();
    private HarmonicTransmutationRegistry harmonicTransmutationRegistry = new HarmonicTransmutationRegistry();
    private ModalInfusionRegistry modalInfusionRegistry = new ModalInfusionRegistry();
    private SimpleNetworkWrapper networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(ID);

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        proxy.preInit();
    }

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        proxy.init();
    }

    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent event) {
        proxy.postInit();
    }

    public GuidebookEntries getGuidebookEntries() {
        return guidebookEntries;
    }

    public MelodicCraftingRegistry getMelodicCraftingRegistry() {
        return melodicCraftingRegistry;
    }

    public HarmonicTransmutationRegistry getHarmonicTransmutationRegistry() {
        return harmonicTransmutationRegistry;
    }

    public ModalInfusionRegistry getModalInfusionRegistry() {
        return modalInfusionRegistry;
    }

    public SimpleNetworkWrapper getNetworkWrapper() {
        return networkWrapper;
    }
}
