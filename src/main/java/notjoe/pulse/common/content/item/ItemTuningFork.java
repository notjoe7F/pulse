package notjoe.pulse.common.content.item;

import io.vavr.collection.Vector;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import notjoe.pulse.api.guidebook.DocumentationElement;
import notjoe.pulse.api.guidebook.GuidebookCategory;
import notjoe.pulse.api.guidebook.GuidebookEntry;
import notjoe.pulse.api.guidebook.ParagraphElement;
import notjoe.pulse.common.content.block.ModBlockContainer;
import notjoe.pulse.common.content.entity.EntityNote;
import notjoe.pulse.common.content.item.base.AbstractModItem;
import notjoe.pulse.common.util.Configuration;

public class ItemTuningFork extends AbstractModItem implements GuidebookEntry {
    public ItemTuningFork() {
        super("tuning_fork");
        setMaxStackSize(1);
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.BOW;
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        if (handIn != EnumHand.MAIN_HAND) {
            return ActionResult.newResult(EnumActionResult.FAIL, itemstack);
        }

        playerIn.setActiveHand(handIn);
        return ActionResult.newResult(EnumActionResult.SUCCESS, itemstack);
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack,World worldIn, EntityLivingBase entityLiving, int timeLeft) {
        if (!(entityLiving instanceof EntityPlayer)) {
            return;
        }

        EntityPlayer player = (EntityPlayer) entityLiving;
        EntityNote note = new EntityNote(worldIn, player);
        worldIn.spawnEntity(note);
        note.playSound();

        player.getCooldownTracker().setCooldown(this, Configuration.tuningForkCooldownTicks);
    }

    @Override
    public String getTitleUnlocalized() {
        return getUnlocalizedName();
    }

    @Override
    public ItemStack getRepresentativeItem() {
        return new ItemStack(this);
    }

    @Override
    public GuidebookCategory getCategory() {
        return GuidebookCategory.DEBUG;
    }

    @Override
    public int getGuidebookX() {
        return 2;
    }

    @Override
    public int getGuidebookY() {
        return 0;
    }

    @Override
    public Vector<GuidebookEntry> getChildren() {
        return Vector.of((GuidebookEntry) ModBlockContainer.PITCHER_PENTATONIC);
    }

    @Override
    public Vector<DocumentationElement> getDocumentationElements() {
        return Vector.of((DocumentationElement) new ParagraphElement("asdfdfgshd"));
    }
}
