package net.hhc.tutorial.block;

import net.hhc.tutorial.TutorialMod;
import net.hhc.tutorial.block.custom.CobaltBlasterBlock;
import net.hhc.tutorial.block.custom.CobaltLampBlock;
import net.hhc.tutorial.block.custom.SpeedyBlock;
import net.hhc.tutorial.block.custom.TurnipCropBlock;
import net.hhc.tutorial.item.ModCreativeModeTab;
import net.hhc.tutorial.item.ModItems;
import net.hhc.tutorial.machine.PartBlock;
import net.hhc.tutorial.machine.SuperBlock;
import net.hhc.tutorial.sound.ModSounds;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.List;
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



    private static <T extends Block>RegistryObject<Item>  registerBlockItem(String name,RegistryObject<T> block,CreativeModeTab tab,String tooltipkey)
    {
        return ModItems.ITEMS.register(name,()->new BlockItem(block.get(), new Item.Properties().tab(tab)){
            @Override
            public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
                pTooltip.add(new TranslatableComponent(tooltipkey));
            }
        });
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block,CreativeModeTab tab,String tooltipkey)
    {
        RegistryObject<T> toReturn=BLOCKS.register(name,block);
        registerBlockItem(name,toReturn,tab,tooltipkey);
        return toReturn;
    }



    public static final RegistryObject<Block>COBALT_BLOCK=registerBlock("cobalt_block",
            ()->new Block(BlockBehaviour.Properties.of(Material.METAL).strength(5f).requiresCorrectToolForDrops()), ModCreativeModeTab.CREATIVE_MODE_TAB);

    public static final RegistryObject<Block>COBALT_ORE=registerBlock("cobalt_ore",
            ()->new Block(BlockBehaviour.Properties.of(Material.STONE).strength(4f).requiresCorrectToolForDrops()),ModCreativeModeTab.CREATIVE_MODE_TAB);

    public static final RegistryObject<Block>SPEEDY_BLOCK=registerBlock("speedy_block",
            ()->new SpeedyBlock(BlockBehaviour.Properties.of(Material.STONE).strength(4f).requiresCorrectToolForDrops()),ModCreativeModeTab.CREATIVE_MODE_TAB,"tooltip.block.speedy_block");

    public static final RegistryObject<Block>COBALT_STAIRS=registerBlock("cobalt_stairs",
            ()->new StairBlock(()->ModBlocks.COBALT_BLOCK.get().defaultBlockState(),BlockBehaviour.Properties.of(Material.METAL).strength(4f).requiresCorrectToolForDrops()),ModCreativeModeTab.CREATIVE_MODE_TAB);


    public static final RegistryObject<Block>COBALT_SLAB=registerBlock("cobalt_slab",
            ()->new SlabBlock(BlockBehaviour.Properties.of(Material.METAL).strength(4f).requiresCorrectToolForDrops()),ModCreativeModeTab.CREATIVE_MODE_TAB);

    public static final RegistryObject<Block>COBALT_BUTTON=registerBlock("cobalt_button",
            ()->new StoneButtonBlock(BlockBehaviour.Properties.of(Material.METAL).strength(4f).noCollission().requiresCorrectToolForDrops()),ModCreativeModeTab.CREATIVE_MODE_TAB);

    public static final RegistryObject<Block>COBALT_PRESSURE_PLATE=registerBlock("cobalt_pressure_plate",
            ()->new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING,BlockBehaviour.Properties.of(Material.METAL).strength(4f).requiresCorrectToolForDrops()),ModCreativeModeTab.CREATIVE_MODE_TAB);

    public static final RegistryObject<Block>CHERRY_BLOSSOM_DOOR=registerBlock("cherry_blossom_door",
            ()->new DoorBlock(BlockBehaviour.Properties.of(Material.WOOD).noOcclusion().strength(4f).requiresCorrectToolForDrops()),ModCreativeModeTab.CREATIVE_MODE_TAB);

    public static final RegistryObject<Block>CHERRY_BLOSSOM_TRAPDOOR=registerBlock("cherry_blossom_trapdoor",
            ()->new TrapDoorBlock(BlockBehaviour.Properties.of(Material.WOOD).noOcclusion().strength(4f).requiresCorrectToolForDrops()),ModCreativeModeTab.CREATIVE_MODE_TAB);

    public static final RegistryObject<Block>COBALT_LAMP=registerBlock("cobalt_lamp",
            ()->new CobaltLampBlock(BlockBehaviour.Properties.of(Material.METAL).sound(ModSounds.COBALT_LAMP_SOUNDS).lightLevel((state)->state.getValue(CobaltLampBlock.CLICKED)? 15:0).strength(4f).requiresCorrectToolForDrops()),ModCreativeModeTab.CREATIVE_MODE_TAB);

    public static final RegistryObject<Block>TURNIP_CROP=BLOCKS.register("turnip_crop",
            ()->new TurnipCropBlock(BlockBehaviour.Properties.copy(Blocks.BEETROOTS).noOcclusion().noCollission()));

    public static final RegistryObject<Block>COBALT_BLASTER=registerBlock("cobalt_blaster",
            ()->new CobaltBlasterBlock(BlockBehaviour.Properties.of(Material.METAL).noOcclusion().strength(5f).requiresCorrectToolForDrops()), ModCreativeModeTab.CREATIVE_MODE_TAB);

    public static final RegistryObject<Block>PART_BLOCK=registerBlock("part_block",
            ()->new PartBlock(BlockBehaviour.Properties.of(Material.METAL).noOcclusion().strength(5f)), ModCreativeModeTab.CREATIVE_MODE_TAB);

    public static final RegistryObject<Block>SUPER_BLOCK=registerBlock("super_block",
            ()->new SuperBlock(BlockBehaviour.Properties.of(Material.METAL).noOcclusion().strength(5f)), ModCreativeModeTab.CREATIVE_MODE_TAB);


}
