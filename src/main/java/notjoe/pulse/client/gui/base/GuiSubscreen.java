package notjoe.pulse.client.gui.base;

public interface GuiSubscreen {
    void draw(int mouseX, int mouseY, float partialTicks);
    void switchIn();
    void switchOut();

    default void onMouseClicked(int mouseX, int mouseY, int mouseButton) { }
    default void onMouseDrag(int mouseX, int mouseY, int heldMouseButton, long timeSinceLastClick) { }
    default void onMouseReleased(int mouseX, int mouseY, int state) { }
}
