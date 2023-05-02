package net.hhc.tutorial.multiblock;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

import net.minecraft.client.resources.model.BakedModel;

import net.minecraft.core.BlockPos;

import net.minecraft.world.level.block.Blocks;

import net.minecraft.world.level.block.state.BlockState;

import net.minecraftforge.client.model.data.EmptyModelData;
import org.slf4j.Logger;

public class DisPlayBlockEntityRenderer implements BlockEntityRenderer<DisplayBlockEntity> {

    private static final Logger LOGGER = LogUtils.getLogger();
    private final BlockEntityRendererProvider.Context context;


    public static final String NAME = "test_block";
    public static DynamicModel TESTMODEL= new DynamicModel(NAME);



    public DisPlayBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.context = context;
    }

    @Override
    public void render(DisplayBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        BlockRenderDispatcher dispatcher = this.context.getBlockRenderDispatcher();
        final BlockRenderDispatcher blockRenderer = Minecraft.getInstance().getBlockRenderer();
        BakedModel model = TESTMODEL.get();
        BlockPos blockPos=pBlockEntity.getBlockPos();
        BlockState blockState = pBlockEntity.getLevel().getBlockState(blockPos);

        pPoseStack.pushPose();

        pPoseStack.scale(3, 3, 3);

        blockRenderer.getModelRenderer().renderModel(
                pPoseStack.last(),pBufferSource.getBuffer(RenderType.solid()),
                blockState,model,1,1,1,
                pPackedLight,pPackedOverlay,EmptyModelData.INSTANCE);


        pPoseStack.popPose();

        dispatcher.renderSingleBlock(Blocks.GLASS.defaultBlockState(),pPoseStack,pBufferSource,pPackedLight,pPackedOverlay, EmptyModelData.INSTANCE);


        /*
        final ItemRenderer itemRenderer=Minecraft.getInstance().getItemRenderer();
        dispatcher.renderSingleBlock(Blocks.GLASS.defaultBlockState(),pPoseStack,pBufferSource,pPackedLight,pPackedOverlay, EmptyModelData.INSTANCE);
        LocalPlayer player=Minecraft.getInstance().player;
        final ItemStack heldItem=player.getMainHandItem();
        pPoseStack.pushPose();
        pPoseStack.scale(0.75f,0.75f,0.75f);
        pPoseStack.translate(0.5f,0.5f,0.5f);
        itemRenderer.renderStatic(player,heldItem, ItemTransforms.TransformType.FIXED,false,pPoseStack,pBufferSource,Minecraft.getInstance().level, pPackedLight,pPackedOverlay,0);
        pPoseStack.popPose();*/

    }


}

