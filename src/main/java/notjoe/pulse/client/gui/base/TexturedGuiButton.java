package notjoe.pulse.client.gui.base;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class TexturedGuiButton extends GuiButton {
    private final ResourceLocation texture;
    private final int textureU, textureV;
    private final int hoverOffsetU, hoverOffsetV;
    private final int disableOffsetU, disableOffsetV;

    public TexturedGuiButton(int buttonId, int x, int y, int textureU, int textureV, int hoverOffsetU, int hoverOffsetV,
                             int disableOffsetU, int disableOffsetV, int width, int height, ResourceLocation texture) {
        super(buttonId, x, y, width, height, "");
        this.texture = texture;
        this.textureU = textureU;
        this.textureV = textureV;
        this.hoverOffsetU = hoverOffsetU;
        this.hoverOffsetV = hoverOffsetV;
        this.disableOffsetU = disableOffsetU;
        this.disableOffsetV = disableOffsetV;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if (visible) {
            mc.getTextureManager().bindTexture(texture);
            GL11.glColor4f(1f, 1f, 1f, 1f);
            int drawU = textureU;
            int drawV = textureV;
            if (!enabled) {
                drawU += disableOffsetU;
                drawV += disableOffsetV;
            } else if ((mouseX >= x) && (mouseX <= (x + width)) && (mouseY >= y) && (mouseY <= (y + height))) {
                drawU += hoverOffsetU;
                drawV += hoverOffsetV;
            }
            drawTexturedModalRect(x, y, drawU, drawV, width, height);
            mouseDragged(mc, mouseX, mouseY);
        }
    }
}
