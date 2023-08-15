package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.Moresnifferflowers;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.BannerPatternItem;
import net.minecraft.item.Item;
import net.minecraft.item.SmithingTemplateItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModItems {
    public static final Item DAWNBERRY_VINE_SEEDS = register("dawnberry_vine_seeds", new AliasedBlockItem(ModBlocks.DAWNBERRY_VINE, new Item.Settings()));
    public static final Item DAWNBERRY = register("dawnberry", new Item(new Item.Settings().food(ModFoods.DAWNBERRY)));
    public static final Item AMBUSH_SEEDS = register("ambush_seeds", new AliasedBlockItem(ModBlocks.AMBUSH, new Item.Settings()));
    public static final Item AMBUSH_BANNER_PATTERN = register("ambush_banner_pattern", new BannerPatternItem(ModTags.ModBannerPatternTags.AMBUSH_BANNER_PATTERN, new Item.Settings()));
    public static final Item AMBER_SHARD = register("amber_shard", new Item(new Item.Settings()));
    public static final Item AROMA_ARMOR_TRIM_SMITHING_TABLE = register("aroma_armor_trim_smithing_table", SmithingTemplateItem.of(ModTrimPatterns.AROMA));
    public static final Item DRAGONFLY = register("dragonfly", new Item(new Item.Settings().food(ModFoods.DRAGONFLY)));

    public static Item register(String name, Item item) {
        return Registry.register(Registries.ITEM, Moresnifferflowers.loc(name), item);
    }

    public static void init() {}
}
