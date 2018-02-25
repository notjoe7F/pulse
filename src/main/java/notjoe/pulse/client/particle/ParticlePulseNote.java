package notjoe.pulse.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import notjoe.pulse.Pulse;

import java.awt.*;

public class ParticlePulseNote extends Particle {
    public static final ResourceLocation TEXTURE = new ResourceLocation(Pulse.ID, "particles/note");
    public ParticlePulseNote(World world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, Color color) {
        super(world, x, y, z, velocityX, velocityY, velocityZ);
        particleAlpha = 0.80f;
        motionX = velocityX;
        motionY = velocityY;
        motionZ = velocityZ;

        TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(TEXTURE.toString());
        setParticleTexture(sprite);
        setMaxAge(40);

        setRBGColorF(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f);
    }

    public ParticlePulseNote(World world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, Color color, double initialAgePercent) {
        this(world, x, y, z, velocityX, velocityY, velocityZ, color);
        particleAge = (int) (particleMaxAge * initialAgePercent);
    }

    @Override
    public int getFXLayer() {
        return 1;
    }

    @Override
    public boolean shouldDisableDepth() {
        return false;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        setAlphaF(0.8f * (1 - (particleAge / particleMaxAge)));
    }

    @Override
    public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX,
                               float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
        double minU = particleTexture.getMinU();
        double maxU = particleTexture.getMaxU();
        double minV = particleTexture.getMinV();
        double maxV = particleTexture.getMaxV();
        double scale = 0.1 * particleScale * (1 - ((float) particleAge / particleMaxAge));
        double renderX = (prevPosX + ((posX - prevPosX) * partialTicks)) - interpPosX;
        double renderY = (prevPosY + ((posY - prevPosY) * partialTicks)) - interpPosY;
        double renderZ = (prevPosZ + ((posZ - prevPosZ) * partialTicks)) - interpPosZ;
        int brightness = getBrightnessForRender(partialTicks);
        int skyLighting = (brightness >> 16) & 0xFFFF;
        int blockLighting = brightness & 0xFFFF;

        buffer.pos(renderX - (rotationX * scale) - (rotationXY * scale),
                renderY - (rotationZ * scale),
                renderZ - (rotationYZ * scale) - (rotationXZ * scale))
                .tex(maxU, maxV)
                .color(particleRed, particleGreen, particleBlue, particleAlpha)
                .lightmap(skyLighting, blockLighting)
                .endVertex();
        buffer.pos((renderX - (rotationX * scale)) + (rotationXY * scale),
                renderY + (rotationZ * scale),
                (renderZ - (rotationYZ * scale)) + (rotationXZ * scale))
                .tex(maxU, minV)
                .color(particleRed, particleGreen, particleBlue, particleAlpha)
                .lightmap(skyLighting, blockLighting)
                .endVertex();
        buffer.pos(renderX + (rotationX * scale) + (rotationXY * scale),
                renderY + (rotationZ * scale),
                renderZ + (rotationYZ * scale) + (rotationXZ * scale))
                .tex(minU, minV)
                .color(particleRed, particleGreen, particleBlue, particleAlpha)
                .lightmap(skyLighting, blockLighting)
                .endVertex();
        buffer.pos((renderX + (rotationX * scale)) - (rotationXY * scale),
                renderY - (rotationZ * scale),
                (renderZ + (rotationYZ * scale)) - (rotationXZ * scale))
                .tex(minU, maxV)
                .color(particleRed, particleGreen, particleBlue, particleAlpha)
                .lightmap(skyLighting, blockLighting)
                .endVertex();
    }
}
