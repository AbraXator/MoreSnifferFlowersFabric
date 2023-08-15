package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.Moresnifferflowers;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModBannerPatterns {
    public static final BannerPattern AMBUSH = Registry.register(Registries.BANNER_PATTERN, Moresnifferflowers.loc("ambush"), new BannerPattern("msfa"));

    public static void init() {}
}
