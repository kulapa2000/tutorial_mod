package net.hhc.tutorial.multiblock;

import net.hhc.tutorial.TutorialMod;
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

public class ModMultiBlocks {
    public static final DeferredRegister<Block> MULTIBLOCK_BLOCKS=DeferredRegister.create(ForgeRegistries.BLOCKS, TutorialMod.MOD_ID);

    public static void register(IEventBus eventBus)
    {
        MULTIBLOCK_BLOCKS.register(eventBus);
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab)
    {
        return ModItems.ITEMS.register(name,()->new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab)
    {
        RegistryObject<T> toReturn=MULTIBLOCK_BLOCKS.register(name,block);
        registerBlockItem(name,toReturn,tab);
        return toReturn;
    }

    public static final RegistryObject<Block>CORE_BLOCK=registerBlock("core_block",
            ()->new CoreBlock(BlockBehaviour.Properties.of(Material.METAL).strength(5f)), ModCreativeModeTab.CREATIVE_MODE_TAB);


}
