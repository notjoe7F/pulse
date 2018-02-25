package notjoe.pulse.client.particle;

import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import notjoe.pulse.Pulse;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = Pulse.ID)
public class TextureStitchEvents {
    private TextureStitchEvents() { }

    @SubscribeEvent
    public static void onPreTextureStitch(TextureStitchEvent.Pre event) {
        event.getMap().registerSprite(ParticlePulseNote.TEXTURE);
    }
}
