package notjoe.pulse.client.gui.base;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public abstract class AbstractModGuiContainer extends GuiContainer {
    private final ResourceLocation guiBackgroundTexture;
    private final String name;

    public AbstractModGuiContainer(Container inventorySlotsIn, ResourceLocation guiBackgroundTexture, String name, int xSize, int ySize) {
        super(inventorySlotsIn);
        this.guiBackgroundTexture = guiBackgroundTexture;
        this.name = name;
        this.xSize = xSize;
        this.ySize = ySize;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(guiBackgroundTexture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        drawCenteredString(fontRenderer, I18n.format("pulse.gui." + name), xSize / 2, (int) (-1.5 * fontRenderer.FONT_HEIGHT), 0xFFFFFFFF);
    }
}
