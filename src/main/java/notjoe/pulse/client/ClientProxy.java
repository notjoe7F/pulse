package notjoe.pulse.client;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import notjoe.pulse.client.tesr.TesrPedestal;
import notjoe.pulse.common.CommonProxy;
import notjoe.pulse.common.content.tile.TilePedestal;

public class ClientProxy extends CommonProxy {
    @Override
    public void preInit() {
        super.preInit();
        ClientRegistry.bindTileEntitySpecialRenderer(TilePedestal.class, new TesrPedestal());
    }
}
