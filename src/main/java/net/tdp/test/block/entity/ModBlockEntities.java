package net.tdp.test.block.entity;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.tdp.test.Test;
import net.tdp.test.block.ModBlocks;
import net.tdp.test.block.entity.custom.GrowthChamberBlockEntity;
import net.tdp.test.block.entity.custom.PedestalBlockEntity;

public class ModBlockEntities {
    public static final BlockEntityType<PedestalBlockEntity> PEDESTAL_BE =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(Test.MOD_ID, "pedestal_be"),
                    BlockEntityType.Builder.create(PedestalBlockEntity::new, ModBlocks.PEDESTAL).build(null));

    public static final BlockEntityType<GrowthChamberBlockEntity> GROWTH_CHAMBER_BE =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(Test.MOD_ID, "growth_chamber_be"),
                    BlockEntityType.Builder.create(GrowthChamberBlockEntity::new, ModBlocks.GROWTH_CHAMBER).build(null));

    public static void registerBlockEntities() {
        Test.LOGGER.info("Registering Block Entities for " + Test.MOD_ID);
    }
}
