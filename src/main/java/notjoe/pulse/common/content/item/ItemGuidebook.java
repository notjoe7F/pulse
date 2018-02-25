package notjoe.pulse.common.content.item;

import io.vavr.collection.Vector;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import notjoe.pulse.Pulse;
import notjoe.pulse.api.guidebook.DocumentationElement;
import notjoe.pulse.api.guidebook.GuidebookCategory;
import notjoe.pulse.api.guidebook.GuidebookEntry;
import notjoe.pulse.client.gui.guidebook.GuiGuidebook;
import notjoe.pulse.common.content.item.base.AbstractModItem;

public class ItemGuidebook extends AbstractModItem implements GuidebookEntry {
    public ItemGuidebook() {
        super("guidebook");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if (worldIn.isRemote) {
            Minecraft.getMinecraft().displayGuiScreen(new GuiGuidebook(Pulse.instance.getGuidebookEntries().getGuidebook()));
        }

        return ActionResult.newResult(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }

    @Override
    public String getTitleUnlocalized() {
        return getUnlocalizedName();
    }

    @Override
    public ItemStack getRepresentativeItem() {
        return new ItemStack(Blocks.NOTEBLOCK);
    }

    @Override
    public GuidebookCategory getCategory() {
        return GuidebookCategory.DEBUG;
    }

    @Override
    public int getGuidebookX() {
        return 0;
    }

    @Override
    public int getGuidebookY() {
        return 0;
    }

    @Override
    public Vector<GuidebookEntry> getChildren() {
        return Vector.of((GuidebookEntry) ModItemContainer.TUNING_FORK);
    }

    @Override
    public Vector<DocumentationElement> getDocumentationElements() {
        return Vector.empty();
    }
}
