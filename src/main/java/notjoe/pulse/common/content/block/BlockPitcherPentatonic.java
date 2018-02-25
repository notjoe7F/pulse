package notjoe.pulse.common.content.block;

import io.vavr.collection.Vector;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import notjoe.pulse.api.guidebook.DocumentationElement;
import notjoe.pulse.api.guidebook.GuidebookCategory;
import notjoe.pulse.api.guidebook.GuidebookEntry;
import notjoe.pulse.api.musictheory.Scales;
import notjoe.pulse.common.content.block.base.AbstractModDirectionalBlock;
import notjoe.pulse.common.content.tile.TilePitcher;

import javax.annotation.Nullable;

public class BlockPitcherPentatonic extends AbstractModDirectionalBlock implements GuidebookEntry {
    public BlockPitcherPentatonic() {
        super("pitcher_pentatonic", Material.WOOD);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TilePitcher(Scales.PENTATONIC_MAJOR);
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
        return 4;
    }

    @Override
    public int getGuidebookY() {
        return 2;
    }

    @Override
    public Vector<GuidebookEntry> getChildren() {
        return Vector.empty();
    }

    @Override
    public Vector<DocumentationElement> getDocumentationElements() {
        return Vector.empty();
    }
}
