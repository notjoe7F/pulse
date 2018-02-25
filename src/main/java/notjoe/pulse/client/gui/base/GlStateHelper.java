package notjoe.pulse.client.gui.base;

import net.minecraft.client.renderer.GlStateManager;

public class GlStateHelper {
    public static void withPushedState(Runnable action) {
        GlStateManager.pushMatrix();
        GlStateManager.pushAttrib();
        action.run();
        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }
}
