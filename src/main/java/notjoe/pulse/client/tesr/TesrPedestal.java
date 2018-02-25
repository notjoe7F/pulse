package notjoe.pulse.client.tesr;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import notjoe.pulse.client.gui.base.GlStateHelper;
import notjoe.pulse.common.content.tile.TilePedestal;

@SideOnly(Side.CLIENT)
public class TesrPedestal extends TileEntitySpecialRenderer<TilePedestal> {
    @Override
    public void render(TilePedestal te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        GlStateHelper.withPushedState(() -> {
            GlStateManager.translate(x, y, z);
            GlStateManager.disableRescaleNormal();
            renderItem(te);
        });
    }

    public void renderItem(TilePedestal tilePedestal) {
        ItemStack stack = tilePedestal.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).getStackInSlot(0);
        if (stack.isEmpty()) {
            return;
        }

        RenderHelper.enableStandardItemLighting();
        GlStateManager.enableLighting();
        GlStateManager.pushMatrix();

        GlStateManager.translate(0.5, 1.4, 0.5);
        GlStateManager.scale(0.5, 0.5, 0.5);

        GlStateManager.translate(0f, 0.05 * Math.sin(0.1 * getWorld().getWorldTime()), 0f);
        GlStateManager.rotate((float) ((0.9 * getWorld().getWorldTime()) % 360), 0f, 1f, 0f);
        Minecraft.getMinecraft().getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.NONE);

        GlStateManager.popMatrix();
    }
}
