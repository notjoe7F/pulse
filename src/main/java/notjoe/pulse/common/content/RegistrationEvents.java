package notjoe.pulse.common.content;

import io.vavr.collection.Vector;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.registries.IForgeRegistry;
import notjoe.pulse.Pulse;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = Pulse.ID)
public final class RegistrationEvents {
    private RegistrationEvents() { }

    private static final Vector<Block> blocksToRegister = ModContent.ALL_BLOCKS;
    private static final Vector<Item> itemsToRegister = ModContent.ALL_ITEMS;

    @SubscribeEvent
    public static void onBlocksRegistered(RegistryEvent.Register<Block> event) {
        IForgeRegistry<Block> blockRegistry = event.getRegistry();
        blocksToRegister.forEach(blockRegistry::register);
    }

    @SubscribeEvent
    public static void onItemsRegistered(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> itemRegistry = event.getRegistry();
        itemsToRegister.forEach(itemRegistry::register);
        blocksToRegister.forEach(block -> itemRegistry.register(
                new ItemBlock(block).setRegistryName(block.getRegistryName())));
    }

    @SubscribeEvent
    public static void onModelsRegistered(ModelRegistryEvent event) {
        itemsToRegister
                .filter(item -> item instanceof CustomItemModelProvider)
                .forEach(item -> {
                    CustomItemModelProvider modelProvider = (CustomItemModelProvider) item;
                    modelProvider.getItemModelLocations().forEach((meta, resourceLocation) -> {
                        ModelLoader.setCustomModelResourceLocation(item, meta, resourceLocation);
                    });
                });

        blocksToRegister
                .filter(block -> block instanceof CustomItemModelProvider)
                .forEach(block -> {
                    CustomItemModelProvider modelProvider = (CustomItemModelProvider) block;
                    modelProvider.getItemModelLocations().forEach((meta, resourceLocation) -> {
                        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), meta, resourceLocation);
                    });
                });
    }
}
