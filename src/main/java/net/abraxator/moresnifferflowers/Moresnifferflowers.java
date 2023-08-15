package net.abraxator.moresnifferflowers;

import net.abraxator.moresnifferflowers.init.*;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Moresnifferflowers implements ModInitializer {
    public static final String MOD_ID = "moresnifferflowers";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModBlocks.init();
        ModItems.init();
        ModCreativeTabs.init();
        ModBannerPatterns.init();
        ModLootTableModifiers.init();
        ModBlockEntities.init();
    }

    public static Identifier loc(String path) {
        return new Identifier(MOD_ID, path);
    }
}
