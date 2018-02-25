package notjoe.pulse.common.content.item;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import notjoe.pulse.Pulse;

/**
 * Contains all items within this mod. ItemBlocks should be retrieved with Item#getItemFromBlock.
 */
@GameRegistry.ObjectHolder(Pulse.ID)
public final class ModItemContainer {
    private ModItemContainer() { }

    public static final Item TUNING_FORK = null;
    public static final Item GUIDEBOOK = null;
}
