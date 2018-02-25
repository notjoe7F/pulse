package notjoe.pulse.api.guidebook;

import net.minecraft.item.ItemStack;

public class ItemStackElement implements DocumentationElement {
    private final ItemStack itemStack;
    private final String captionTranslateKey;

    public ItemStackElement(ItemStack itemStack, String captionTranslateKey) {
        this.itemStack = itemStack;
        this.captionTranslateKey = captionTranslateKey;
    }

    @Override
    public void draw() {

    }
}
