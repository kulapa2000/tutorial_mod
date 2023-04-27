package net.hhc.tutorial.util;

import net.hhc.tutorial.TutorialMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;

import static net.hhc.tutorial.util.ModTags.Blocks.tag;


public class ModTags {



    public static class Blocks
    {
        public static final TagKey<Block> DOWSING_ROD_VALUABLES= tag("dowsing_rod_valuables");
        public static TagKey<Block> tag(String name)
        {
            return BlockTags.create(new ResourceLocation(TutorialMod.MOD_ID,name));
        }

        public static  TagKey<Block> forgeTag(String name)
        {
            return BlockTags.create(new ResourceLocation("forge",name));
        }
    }

    public static class Items
    {
        public static  TagKey<Item> tag(String name)
        {
            return ItemTags.create(new ResourceLocation(TutorialMod.MOD_ID,name));
        }

        public static  TagKey<Item> forgeTag(String name)
        {
            return ItemTags.create(new ResourceLocation("forge",name));
        }
    }
}
