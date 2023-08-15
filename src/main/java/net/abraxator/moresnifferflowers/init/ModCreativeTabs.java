package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.Moresnifferflowers;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;

public class ModCreativeTabs {
    public static final ItemGroup MORE_SNIFFER_FLOWERS = Registry.register(Registries.ITEM_GROUP, Moresnifferflowers.loc("more_sniffer_flowers"),
            FabricItemGroup.builder()
                    .displayName(Text.translatable("moresnifferflowers_tab"))
                    .icon(() -> new ItemStack(ModItems.DAWNBERRY))
                    .entries((displayContext, entries) -> Registries.ITEM.forEach(item -> {
                        if(Registries.ITEM.getId(item).getNamespace().equals(Moresnifferflowers.MOD_ID)) {
                            entries.add(item);
                        }
                    }))
                    .build());

    public static void init() {}
}