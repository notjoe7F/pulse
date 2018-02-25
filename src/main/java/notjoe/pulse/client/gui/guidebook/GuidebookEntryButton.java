package notjoe.pulse.client.gui.guidebook;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.util.ResourceLocation;
import notjoe.pulse.Pulse;
import notjoe.pulse.api.guidebook.GuidebookEntry;
import notjoe.pulse.client.gui.base.GlStateHelper;

public class GuidebookEntryButton extends GuiButton {
    public static final int ENTRY_BUTTON_SIZE = 20;
    public static final ResourceLocation TEXTURES = new ResourceLocation(Pulse.ID, "textures/gui/guidebook.png");

    private final GuidebookEntry entry;

    public GuidebookEntryButton(int buttonId, int x, int y, GuidebookEntry entry) {
        super(buttonId, x * ENTRY_BUTTON_SIZE, y * ENTRY_BUTTON_SIZE, ENTRY_BUTTON_SIZE, ENTRY_BUTTON_SIZE, "");
        this.entry = entry;
    }

    public GuidebookEntry getEntry() {
        return entry;
    }

    private void drawButtonBackground(Minecraft mc, int mouseX, int mouseY, int offsetX, int offsetY) {
        int realX = x + offsetX;
        int realY = y + offsetY;

        mc.getTextureManager().bindTexture(TEXTURES);
        drawTexturedModalRect(realX - (ENTRY_BUTTON_SIZE / 2), realY - (ENTRY_BUTTON_SIZE / 2), 0, 0, ENTRY_BUTTON_SIZE, ENTRY_BUTTON_SIZE);
    }

    public void drawButton(Minecraft mc, int mouseX, int mouseY, int offsetX, int offsetY) {
        GlStateHelper.withPushedState(() -> {
            drawButtonBackground(mc, mouseX, mouseY, offsetX, offsetY);
            RenderItem itemRenderer = mc.getRenderItem();
            RenderHelper.enableGUIStandardItemLighting();
            itemRenderer.renderItemIntoGUI(entry.getRepresentativeItem(), (x + offsetX) - 8, (y + offsetY) - 8);
            RenderHelper.disableStandardItemLighting();
        });
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        drawButton(mc, mouseX, mouseY, 0, 0);
    }

    public boolean isMouseOver(int mouseX, int mouseY, int offsetX, int offsetY) {
        int realX = x + offsetX;
        int realY = y + offsetY;
        int minX = realX - (ENTRY_BUTTON_SIZE / 2);
        int maxX = realX + (ENTRY_BUTTON_SIZE / 2);
        int minY = realY - (ENTRY_BUTTON_SIZE / 2);
        int maxY = realY + (ENTRY_BUTTON_SIZE / 2);
        return ((mouseX >= minX) && (mouseX <= maxX)) && ((mouseY >= minY) && (mouseY <= maxY));
    }
}
