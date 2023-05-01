package net.hhc.tutorial.multiblock;

import net.hhc.tutorial.TutorialMod;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMultiBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> MULTIBLOCK_ENTITIES= DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, TutorialMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<CoreBlockEntity>> CORE_BLOCK= MULTIBLOCK_ENTITIES.register("core_block",
            ()->BlockEntityType.Builder.of(CoreBlockEntity::new, ModMultiBlocks.CORE_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<DisplayBlockEntity>> DISPLAY_BLOCK= MULTIBLOCK_ENTITIES.register("display_block",
            ()->BlockEntityType.Builder.of(DisplayBlockEntity::new, ModMultiBlocks.DISPLAY_BLOCK.get()).build(null));
    public static void register(IEventBus eventBus)
    {
        MULTIBLOCK_ENTITIES.register(eventBus);
    }

}
