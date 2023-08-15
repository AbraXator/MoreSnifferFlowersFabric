package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.Moresnifferflowers;
import net.abraxator.moresnifferflowers.blocks.blockentities.AmbushBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModBlockEntities {
    public static BlockEntityType<AmbushBlockEntity> AMBUSH = Registry.register(Registries.BLOCK_ENTITY_TYPE, Moresnifferflowers.loc("ambush"),
            FabricBlockEntityTypeBuilder.create(AmbushBlockEntity::new, ModBlocks.AMBUSH).build(null));

    public static void init() {}
}
