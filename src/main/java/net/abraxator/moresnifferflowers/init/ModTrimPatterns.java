package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.Moresnifferflowers;
import net.minecraft.item.Item;
import net.minecraft.item.trim.ArmorTrimPattern;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Util;

public class ModTrimPatterns {
    public static final RegistryKey<ArmorTrimPattern> AROMA = RegistryKey.of(RegistryKeys.TRIM_PATTERN, Moresnifferflowers.loc("aroma"));

    public static void bootstrap(Registerable<ArmorTrimPattern> registry) {
        register(registry, ModItems.AROMA_ARMOR_TRIM_SMITHING_TABLE, AROMA);
    }

    private static void register(Registerable<ArmorTrimPattern> registry, Item item, RegistryKey<ArmorTrimPattern> key) {
        ArmorTrimPattern trimPattern = new ArmorTrimPattern(key.getValue(), Registries.ITEM.getEntry(item), Text.translatable(Util.createTranslationKey("trim_pattern", key.getValue())));
        registry.register(key, trimPattern);
    }
}
