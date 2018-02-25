package notjoe.pulse.client.gui.guidebook;

import io.vavr.collection.Vector;
import io.vavr.control.Option;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import notjoe.pulse.Pulse;
import notjoe.pulse.api.guidebook.Guidebook;
import notjoe.pulse.api.guidebook.GuidebookCategory;
import notjoe.pulse.api.guidebook.GuidebookEntry;
import org.lwjgl.opengl.GL11;

import java.io.IOException;

public class GuiGuidebook extends GuiScreen {
    public static final int MARGIN = 20;
    public static final ResourceLocation TEXTURES = new ResourceLocation(Pulse.ID, "textures/gui/paper.png");

    private final Vector<GuidebookEntryButton> entryButtons;

    private GuidebookCategory visibleCategory;
    private int offsetX;
    private int offsetY;
    private int lastMouseDragX;
    private int lastMouseDragY;

    public GuiGuidebook(Guidebook guidebook) {
        super();
        entryButtons = guidebook.getEntries().zipWithIndex((entry, index) -> new GuidebookEntryButton(index, entry.getGuidebookX(), entry.getGuidebookY(), entry));
        visibleCategory = GuidebookCategory.DEBUG;
    }

    @Override
    public void initGui() {
        offsetX = getCenterX();
        offsetY = getCenterY();
    }

    private void drawMap(int mouseX, int mouseY, float partialTicks) {
        mc.renderEngine.bindTexture(TEXTURES);
        drawModalRectWithCustomSizedTexture(MARGIN, MARGIN, 0, 0, width - 2 * MARGIN, height - 2 * MARGIN, 128, 128);

        entryButtons.filter(entry -> entry.getEntry().getCategory() == visibleCategory).forEach(entryButton -> {
            entryButton.drawButton(mc, mouseX, mouseY, offsetX, offsetY);
        });

        drawLineNetwork();

        getHoveredButton(mouseX, mouseY).forEach(guidebookEntryButton -> {
            drawHoveringText(guidebookEntryButton.getEntry().getTitleUnlocalized(), mouseX, mouseY);
        });
    }

    private int getCenterX() {
        return width / 2;
    }

    private int getCenterY() {
        return height / 2;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        drawMap(mouseX, mouseY, partialTicks);
    }

    private Option<GuidebookEntryButton> getHoveredButton(int mouseX, int mouseY) {
        return entryButtons.find(entryButton -> entryButton.isMouseOver(mouseX, mouseY, offsetX, offsetY));
    }

    private void drawLineNetwork() {
        entryButtons.filter(entry -> entry.getEntry().getCategory() == visibleCategory).forEach(entryButton -> {
            GuidebookEntry entry = entryButton.getEntry();

            int realEntryX = (entry.getGuidebookX() * GuidebookEntryButton.ENTRY_BUTTON_SIZE) + offsetX;
            int realEntryY = (entry.getGuidebookY() * GuidebookEntryButton.ENTRY_BUTTON_SIZE) + offsetY;

            entry.getChildren()
                    .filter(childEntry -> childEntry.getCategory() == visibleCategory)
                    .forEach(childEntry -> {
                        int childX = (childEntry.getGuidebookX() * GuidebookEntryButton.ENTRY_BUTTON_SIZE) + offsetX;
                        int childY = (childEntry.getGuidebookY() * GuidebookEntryButton.ENTRY_BUTTON_SIZE) + offsetY;
                        drawLine(realEntryX, realEntryY, childX, childY);
                    });
        });
    }

    private void drawLine(int x1, int y1, int x2, int y2) {
        GL11.glPushMatrix();
        GL11.glColor3f(0f, 0f, 0f);
        GL11.glLineWidth(8.0f);
        GL11.glBegin(GL11.GL_LINE_STRIP);
        GL11.glVertex2i(x1, y1);
        GL11.glVertex2i(x2, y2);
        GL11.glEnd();
        GL11.glPopMatrix();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (mouseButton == 0) {
            Option<GuidebookEntryButton> clickedEntry = getHoveredButton(mouseX, mouseY);
        } else if (mouseButton == 1) {
            lastMouseDragX = mouseX;
            lastMouseDragY = mouseY;
        }
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        if (clickedMouseButton != 1) {
            return;
        }
        int dx = mouseX - lastMouseDragX;
        int dy = mouseY - lastMouseDragY;
        offsetX += dx;
        offsetY += dy;
        lastMouseDragX = mouseX;
        lastMouseDragY = mouseY;
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
