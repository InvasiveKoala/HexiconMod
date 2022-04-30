package me.ddtincoming.hexicon.util;

import me.ddtincoming.hexicon.Hexicon;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
public class Tags {

    public static class Blocks
    {
        public static final net.minecraftforge.common.Tags.IOptionalNamedTag<Block> FIRESTONE_CLICKABLE_BLOCKS =
            createTag("firestone_clickable_blocks");

        private static net.minecraftforge.common.Tags.IOptionalNamedTag<Block> createTag(String name)
        {
            return BlockTags.createOptional(new ResourceLocation(Hexicon.MOD_ID, name));
        }
        private static net.minecraftforge.common.Tags.IOptionalNamedTag<Block> createForgeTag(String name)
        {
            return BlockTags.createOptional(new ResourceLocation("forge", name));
        }
    }
    public static class Items
    {
        public static final net.minecraftforge.common.Tags.IOptionalNamedTag<Item> AMETHYST = createForgeTag("gems/amethyst");
        private static net.minecraftforge.common.Tags.IOptionalNamedTag<Item> createTag(String name)
        {
            return ItemTags.createOptional(new ResourceLocation(Hexicon.MOD_ID, name));
        }
        private static net.minecraftforge.common.Tags.IOptionalNamedTag<Item> createForgeTag(String name)
        {
            return ItemTags.createOptional(new ResourceLocation("forge", name));
        }
    }

}
