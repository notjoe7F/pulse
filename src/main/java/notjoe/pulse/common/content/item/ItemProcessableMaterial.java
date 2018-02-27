package notjoe.pulse.common.content.item;

import io.vavr.Tuple;
import io.vavr.collection.Map;
import io.vavr.collection.Stream;
import io.vavr.collection.Vector;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import notjoe.pulse.Pulse;
import notjoe.pulse.common.content.OreDictProvider;
import notjoe.pulse.common.content.item.base.AbstractModItem;
import org.apache.commons.lang3.StringUtils;

public class ItemProcessableMaterial extends AbstractModItem implements OreDictProvider {
    private final Vector<String> variants;
    private final String resourceName;

    public ItemProcessableMaterial(String resourceName, Vector<String> variants) {
        super("resource_" + resourceName);
        this.resourceName = resourceName;
        this.variants = variants;
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (tab == getCreativeTab()) {
            items.addAll(Stream.range(0, variants.length())
                    .map(i -> new ItemStack(this, 1, i))
                    .toJavaList());
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName(stack) + "." + variants.get(stack.getMetadata());
    }

    @Override
    public Map<Integer, ModelResourceLocation> getItemModelLocations() {
        return variants.zipWithIndex().toMap(indexAndName -> indexAndName._2,
                indexAndName -> new ModelResourceLocation(
                        new ResourceLocation(Pulse.ID, getInternalName() + "_" + indexAndName._1), "inventory"));
    }

    @Override
    public Map<String, ItemStack> getOreDictEntries() {
        return variants.zipWithIndex()
                .map(nameAndMeta -> nameAndMeta.map2(meta -> new ItemStack(this, 1, meta)))
                .toMap(nameAndStack ->
                        Tuple.of(nameAndStack._1 + StringUtils.capitalize(resourceName), nameAndStack._2));
    }
}
