package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.Moresnifferflowers;
import net.abraxator.moresnifferflowers.blocks.AmbushBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModBlocks {
    public static final Block DAWNBERRY_VINE = registerBlockNoItem("dawnberry_vine", new Block(AbstractBlock.Settings.create()));
    public static final Block AMBUSH = registerBlockNoItem("ambush", new AmbushBlock(AbstractBlock.Settings.create()));
    public static final Block AMBER = registerBlock("amber", new Block(AbstractBlock.Settings.create()));

    private static Block registerBlockNoItem(String name, Block block) {
        return Registry.register(Registries.BLOCK, Moresnifferflowers.loc(name), block);
    }

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Moresnifferflowers.loc(name), block);
    }

    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, Moresnifferflowers.loc(name),
                new BlockItem(block, new FabricItemSettings()));
    }

    public static void init() {}
}
