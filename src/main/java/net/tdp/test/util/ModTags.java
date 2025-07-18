package net.tdp.test.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.tdp.test.Test;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> NEED_PINK_GARNET_TOOL = createTag("need_pink_garnet_tool");
        public static final TagKey<Block> INCORRECT_FOR_PINK_GARNET_TOOL = createTag("incorrect_for_pink_garnet_tool");


        private static TagKey<Block> createTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, Identifier.of(Test.MOD_ID, name));
        }

    }
    public static class Items {
        // Create a TagKey for transformable items
        public static final TagKey<Item> TRANSFORMABLE_ITEMS = createTag("transformable_items");

        // Create a TagKey for the given name
        private static TagKey<Item> createTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, Identifier.of(Test.MOD_ID, name));

        }
    }
}
