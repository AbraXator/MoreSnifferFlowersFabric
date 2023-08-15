package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.Moresnifferflowers;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;

public class ModTags {
    public static class ModBannerPatternTags {
        public static final TagKey<BannerPattern> AMBUSH_BANNER_PATTERN = create("ambush_banner_pattern");

        public static TagKey<BannerPattern> create(String name) {
            return TagKey.of(Registries.BANNER_PATTERN.getKey(), Moresnifferflowers.loc(name));
        }
    }
}
