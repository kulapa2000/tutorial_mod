package net.hhc.tutorial.block;

import net.hhc.tutorial.TutorialMod;
import net.hhc.tutorial.block.custom.SpeedyBlock;
import net.hhc.tutorial.item.ModCreativeModeTab;
import net.hhc.tutorial.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS=DeferredRegister.create(ForgeRegistries.BLOCKS, TutorialMod.MOD_ID);

    public static void register(IEventBus eventBus)
    {
        BLOCKS.register(eventBus);
    }

    private static <T extends Block>RegistryObject<Item>  registerBlockItem(String name,RegistryObject<T> block,CreativeModeTab tab)
    {
        return ModItems.ITEMS.register(name,()->new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block,CreativeModeTab tab)
    {
        RegistryObject<T> toReturn=BLOCKS.register(name,block);
        registerBlockItem(name,toReturn,tab);
        return toReturn;
    }

    public static final RegistryObject<Block>COBALT_BLOCK=registerBlock("cobalt_block",
            ()->new Block(BlockBehaviour.Properties.of(Material.METAL).strength(5f).requiresCorrectToolForDrops()), ModCreativeModeTab.CREATIVE_MODE_TAB);

    public static final RegistryObject<Block>COBALT_ORE=registerBlock("cobalt_ore",
            ()->new Block(BlockBehaviour.Properties.of(Material.STONE).strength(4f).requiresCorrectToolForDrops()),ModCreativeModeTab.CREATIVE_MODE_TAB);

    public static final RegistryObject<Block>SPEEDY_BLOCK=registerBlock("speedy_block",
            ()->new SpeedyBlock(BlockBehaviour.Properties.of(Material.STONE).strength(4f).requiresCorrectToolForDrops()),ModCreativeModeTab.CREATIVE_MODE_TAB);



}
