package net.hhc.tutorial.multiblock;

import net.hhc.tutorial.TutorialMod;
import net.hhc.tutorial.block.entity.ModBlockEntities;
import net.hhc.tutorial.machine.BakedModel;
import net.hhc.tutorial.machine.SuperBlockEntity;
import net.hhc.tutorial.machine.SuperBlockEntityRenderer;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TutorialMod.MOD_ID,bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class ClientModEvents {

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerBlockEntityRenderer(ModMultiBlockEntities.DISPLAY_BLOCK.get(),DisPlayBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.SUPER_BLOCK.get(), SuperBlockEntityRenderer::new);
       DynamicModel TESTMODEL= new DynamicModel("test_block");
        DynamicModel MULTIBLOCK= new DynamicModel("test_block");
        DynamicModel COBALT_BLOCK= new DynamicModel("cobalt_block");
        DynamicModel ROTATEMODEL=new DynamicModel("rotate2");
    }
}
