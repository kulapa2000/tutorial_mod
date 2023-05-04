package net.hhc.tutorial.machine;

import com.mojang.blaze3d.vertex.PoseStack;
import net.hhc.tutorial.TutorialMod;
import net.hhc.tutorial.multiblock.DynamicModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.ForgeModelBakery;
import net.minecraftforge.client.model.data.EmptyModelData;

public class SuperBlockEntityRenderer implements BlockEntityRenderer<SuperBlockEntity> {


    public static final String NAME = "test_block";
    public static BakedModel MULTIBLOCK= new BakedModel(NAME);
    public static BakedModel COBALT_BLOCK= new BakedModel("cobalt_block");


    final BlockRenderDispatcher blockRenderer = Minecraft.getInstance().getBlockRenderer();
    net.minecraft.client.resources.model.BakedModel model = MULTIBLOCK.get();
    net.minecraft.client.resources.model.BakedModel origin_model = COBALT_BLOCK.get();
    @Override
    public void render(SuperBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay)
    {

        BlockPos blockPos=pBlockEntity.getBlockPos();
        BlockState blockState = pBlockEntity.getLevel().getBlockState(blockPos);

        if(blockState.getBlock() instanceof SuperBlock)
        {
            if(blockState.getValue(SuperBlock.IS_ASSEMBLED)==true)
            {
                pPoseStack.pushPose();
                pPoseStack.scale(1, 3.5f, 1);
                blockRenderer.getModelRenderer().renderModel(
                        pPoseStack.last(),pBufferSource.getBuffer(RenderType.solid()),
                        blockState,model,1,1,1,
                        pPackedLight,pPackedOverlay, EmptyModelData.INSTANCE);
                pPoseStack.popPose();

            }
            if(blockState.getValue(SuperBlock.IS_ASSEMBLED)==false)
            {
                blockRenderer.getModelRenderer().renderModel(
                        pPoseStack.last(),pBufferSource.getBuffer(RenderType.solid()),
                        blockState,origin_model,1,1,1,
                        pPackedLight,pPackedOverlay, EmptyModelData.INSTANCE);
            }
        }

    }


    private final BlockEntityRendererProvider.Context context;
    public SuperBlockEntityRenderer(BlockEntityRendererProvider.Context context){this.context=context;}
}
