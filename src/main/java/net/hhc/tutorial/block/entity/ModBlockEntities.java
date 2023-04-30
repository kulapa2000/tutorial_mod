package net.hhc.tutorial.block.entity;

import net.hhc.tutorial.TutorialMod;
import net.hhc.tutorial.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES= DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, TutorialMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<CobaltBlasterBlockEntity>> COBALT_BLASTER= BLOCK_ENTITIES.register("cobalt_blaster",
            ()->BlockEntityType.Builder.of(CobaltBlasterBlockEntity::new, ModBlocks.COBALT_BLASTER.get()).build(null));
    public static void register(IEventBus eventBus)
    {
        BLOCK_ENTITIES.register(eventBus);
    }
}
