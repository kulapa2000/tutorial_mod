package net.hhc.tutorial.machine;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;
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
import org.slf4j.Logger;

public class SuperBlockEntityRenderer implements BlockEntityRenderer<SuperBlockEntity> {


    public static final String NAME = "test_block";
    public static DynamicModel MULTIBLOCK= new DynamicModel(NAME);
    public static DynamicModel COBALT_BLOCK= new DynamicModel("cobalt_block");

    private static final Logger LOGGER = LogUtils.getLogger();




    @Override
    public void render(SuperBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay)
    {

        net.minecraft.client.resources.model.BakedModel model = MULTIBLOCK.get();
        net.minecraft.client.resources.model.BakedModel origin_model = COBALT_BLOCK.get();
        final BlockRenderDispatcher blockRenderer = Minecraft.getInstance().getBlockRenderer();

        BlockPos blockPos=pBlockEntity.getBlockPos();
        BlockState blockState = pBlockEntity.getLevel().getBlockState(blockPos);

        if(blockState.getBlock() instanceof SuperBlock)
        {

            if(blockState.getValue(SuperBlock.IS_ASSEMBLED))
            {
                pPoseStack.pushPose();
                pPoseStack.scale(1, 3.5f, 1);
                blockRenderer.getModelRenderer().renderModel(
                        pPoseStack.last(),pBufferSource.getBuffer(RenderType.solid()),
                        blockState,model,1,1,1,
                        pPackedLight,pPackedOverlay, EmptyModelData.INSTANCE);
                pPoseStack.popPose();

            }
            if(!blockState.getValue(SuperBlock.IS_ASSEMBLED))
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
