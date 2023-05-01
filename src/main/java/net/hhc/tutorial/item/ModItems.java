package net.hhc.tutorial.item;

import net.hhc.tutorial.TutorialMod;
import net.hhc.tutorial.block.ModBlocks;
import net.hhc.tutorial.fluid.ModFluids;
import net.hhc.tutorial.item.custom.CoalSilverItem;
import net.hhc.tutorial.item.custom.DataTabletItem;
import net.hhc.tutorial.item.custom.DowsingRodItem;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS=DeferredRegister.create(ForgeRegistries.ITEMS, TutorialMod.MOD_ID);

    public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }

    public static final RegistryObject<Item> COBALT_INGOT= ITEMS.register("cobalt_ingot",
            ()->new Item(new Item.Properties().tab(ModCreativeModeTab.CREATIVE_MODE_TAB)));

    public static final RegistryObject<Item> COBALT_NUGGET= ITEMS.register("cobalt_nugget",
            ()->new Item(new Item.Properties().tab(ModCreativeModeTab.CREATIVE_MODE_TAB)));

    public static final RegistryObject<Item> RAW_COBALT = ITEMS.register("raw_cobalt",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.CREATIVE_MODE_TAB)));

    public static final RegistryObject<Item> DOWSING_ROD= ITEMS.register("dowsing_rod",
            ()->new DowsingRodItem(new Item.Properties().tab(ModCreativeModeTab.CREATIVE_MODE_TAB)));

    public static final RegistryObject<Item> COAL_SILVER= ITEMS.register("coal_silver",
            ()->new CoalSilverItem(new Item.Properties().tab(ModCreativeModeTab.CREATIVE_MODE_TAB)));

    public static final RegistryObject<Item> TURNIP= ITEMS.register("turnip",
            ()->new Item(new Item.Properties().tab(ModCreativeModeTab.CREATIVE_MODE_TAB).food(ModFoods.TURNIP)));

    public static final RegistryObject<Item> DATA_TABLET= ITEMS.register("data_tablet",
            ()->new DataTabletItem(new Item.Properties().tab(ModCreativeModeTab.CREATIVE_MODE_TAB)));

    public static final RegistryObject<Item> TURNIP_SEEDS= ITEMS.register("turnip_seeds",
            ()->new ItemNameBlockItem(ModBlocks.TURNIP_CROP.get(),new Item.Properties().tab(ModCreativeModeTab.CREATIVE_MODE_TAB)));

    public static final RegistryObject<Item> HONEY_BUCKET= ITEMS.register("honey_bucket",
            ()->new BucketItem(ModFluids.HONEY_FLUID,new Item.Properties().durability(500).tab(ModCreativeModeTab.CREATIVE_MODE_TAB)));

    public static final RegistryObject<Item> SBZDY= ITEMS.register("sbzdy",
            ()->new Item(new Item.Properties().tab(ModCreativeModeTab.CREATIVE_MODE_TAB)));



}
