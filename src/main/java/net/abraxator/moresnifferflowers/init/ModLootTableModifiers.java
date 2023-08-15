package net.abraxator.moresnifferflowers.init;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.util.Identifier;

public class ModLootTableModifiers {
    private static final Identifier SNIFFER_LOOT =
            new Identifier("gameplay/sniffer_digging");

    public static void init() {
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if(SNIFFER_LOOT.equals(id)) {
                LootPool poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.DAWNBERRY_VINE_SEEDS))
                        .with(ItemEntry.builder(ModItems.AMBUSH_SEEDS))
                        .build();
                tableBuilder.pool(poolBuilder);
            }
        });
    }
}
