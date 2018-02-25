package notjoe.pulse.common.content;

import io.vavr.collection.Map;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;

/**
 * Represents content that has a custom item model that can be set through the ModelLoader.
 */
public interface CustomItemModelProvider {
    /**
     * Get all item models for this object by metadata.
     * @return Map of metadata integer values to ModelResourceLocations representing this object's models.
     */
    Map<Integer, ModelResourceLocation> getItemModelLocations();
}
