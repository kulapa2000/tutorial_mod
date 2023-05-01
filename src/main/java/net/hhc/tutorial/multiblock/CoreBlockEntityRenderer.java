package net.hhc.tutorial.multiblock;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.client.model.data.EmptyModelData;


public class CoreBlockEntityRenderer implements BlockEntityRenderer<CoreBlockEntity>{

    @Override
    public void render(CoreBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay)
    {
       BlockRenderDispatcher dispatcher= this.context.getBlockRenderDispatcher();
       dispatcher.renderSingleBlock(Blocks.GLASS.defaultBlockState(),pPoseStack,pBufferSource,pPackedLight,pPackedOverlay, EmptyModelData.INSTANCE);

    }

    private final BlockEntityRendererProvider.Context context;
    public CoreBlockEntityRenderer(BlockEntityRendererProvider.Context context)
    {
        this.context=context;
    }

}
