package notjoe.pulse.common;

import io.vavr.collection.HashMap;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import notjoe.pulse.Pulse;
import notjoe.pulse.api.crafting.CraftingIngredient;
import notjoe.pulse.api.crafting.harmonic.HarmonicTransmutationRecipe;
import notjoe.pulse.api.crafting.melodic.WildcardMelodicCraftingRecipe;
import notjoe.pulse.api.crafting.modal.ModalInfusionRecipe;
import notjoe.pulse.api.guidebook.GuidebookEntry;
import notjoe.pulse.api.musictheory.Note;
import notjoe.pulse.common.content.ModContent;
import notjoe.pulse.common.content.tile.*;

public class CommonProxy {
    public void preInit() {
        GameRegistry.registerTileEntity(TilePitcher.class, "tile_pitcher");
        GameRegistry.registerTileEntity(TileMelodicCrafting.class, "tile_melodic_crafting");
        GameRegistry.registerTileEntity(TilePedestal.class, "tile_pedestal");
        GameRegistry.registerTileEntity(TileHarmonicTransmutation.class, "tile_harmonic_transmutation");
        GameRegistry.registerTileEntity(TileModalRune.class, "tile_modal_rune");
        GameRegistry.registerTileEntity(TileModalInfusion.class, "tile_modal_infusion");
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

        Pulse.instance.getHarmonicTransmutationRegistry().add(new HarmonicTransmutationRecipe<>(
                new ItemStack(Items.EMERALD, 6),
                CraftingIngredient.ofOreDictEntry("ingotIron"), 5,
                CraftingIngredient.ofOreDictEntry("nuggetGold"), 3,
                0, 0
        ));

        Pulse.instance.getModalInfusionRegistry().add(new ModalInfusionRecipe<>(
                new ItemStack(Items.DIAMOND_HELMET),
                Note.C_LOW,
                HashMap.of(
                        CraftingIngredient.ofOreDictEntry("oreIron"), 2,
                        CraftingIngredient.ofItemStack(new ItemStack(Blocks.COAL_BLOCK)), 1
                ),
                0, 0
        ));
    }
}
