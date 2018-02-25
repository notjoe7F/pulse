package notjoe.pulse.api.guidebook;

import io.vavr.collection.Vector;
import net.minecraft.item.ItemStack;

public interface GuidebookEntry {
    String getTitleUnlocalized();
    ItemStack getRepresentativeItem();
    GuidebookCategory getCategory();
    int getGuidebookX();
    int getGuidebookY();
    Vector<GuidebookEntry> getChildren();
    Vector<DocumentationElement> getDocumentationElements();
}
