package notjoe.pulse.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

import java.awt.*;

public class PulseParticles {
    public static void renderNote(World world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, Color color, double initialAgePercent) {
        ParticlePulseNote particlePulseNote = new ParticlePulseNote(world, x, y, z, velocityX, velocityY, velocityZ, color, initialAgePercent);
        Minecraft.getMinecraft().effectRenderer.addEffect(particlePulseNote);
    }
}
