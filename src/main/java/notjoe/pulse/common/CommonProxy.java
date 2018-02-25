package notjoe.pulse.common;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import notjoe.pulse.Pulse;
import notjoe.pulse.api.crafting.CraftingIngredient;
import notjoe.pulse.api.crafting.melodic.WildcardMelodicCraftingRecipe;
import notjoe.pulse.api.guidebook.GuidebookEntry;
import notjoe.pulse.common.content.ModContent;
import notjoe.pulse.common.content.tile.TileMelodicCrafting;
import notjoe.pulse.common.content.tile.TilePitcher;

public class CommonProxy {
    public void preInit() {
        GameRegistry.registerTileEntity(TilePitcher.class, "tile_pitcher");
        GameRegistry.registerTileEntity(TileMelodicCrafting.class, "tile_melodic_crafting");
    }

    public void init() {
        NetworkRegistry.INSTANCE.registerGuiHandler(Pulse.instance, new PulseGuiHandler());
    }

    public void postInit() {
        Pulse.instance.getGuidebookEntries().addEntries(
                ModContent.ALL_BLOCKS.filter(block -> block instanceof GuidebookEntry)
                    .map(GuidebookEntry.class::cast)
        );

        Pulse.instance.getGuidebookEntries().addEntries(
                ModContent.ALL_ITEMS.filter(item -> item instanceof GuidebookEntry)
                        .map(GuidebookEntry.class::cast)
        );

        Pulse.instance.getMelodicCraftingRegistry().add(new WildcardMelodicCraftingRecipe<>(
                new ItemStack(Items.APPLE),
                3,
                CraftingIngredient.createIngredientList("ingotIron")
        ));
    }
}
