package net.hhc.tutorial.item;

import net.hhc.tutorial.TutorialMod;
import net.hhc.tutorial.item.custom.CoalSilverItem;
import net.hhc.tutorial.item.custom.DataTabletItem;
import net.hhc.tutorial.item.custom.DowsingRodItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
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

    public static final RegistryObject<Item> DOWSING_ROD= ITEMS.register("dowsing_rod",
            ()->new DowsingRodItem(new Item.Properties().tab(ModCreativeModeTab.CREATIVE_MODE_TAB)));

    public static final RegistryObject<Item> COAL_SILVER= ITEMS.register("coal_silver",
            ()->new CoalSilverItem(new Item.Properties().tab(ModCreativeModeTab.CREATIVE_MODE_TAB)));

    public static final RegistryObject<Item> TURNIP= ITEMS.register("turnip",
            ()->new Item(new Item.Properties().tab(ModCreativeModeTab.CREATIVE_MODE_TAB).food(ModFoods.TURNIP)));

    public static final RegistryObject<Item> DATA_TABLET= ITEMS.register("data_tablet",
            ()->new DataTabletItem(new Item.Properties().tab(ModCreativeModeTab.CREATIVE_MODE_TAB)));




}
