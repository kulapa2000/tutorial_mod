package net.hhc.tutorial.machine;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;
import com.mojang.math.Vector3d;
import com.mojang.math.Vector3f;
import net.hhc.tutorial.multiblock.DynamicModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.EmptyModelData;
import org.slf4j.Logger;

public class SuperBlockEntityRenderer implements BlockEntityRenderer<SuperBlockEntity> {


    public static final String NAME = "test_block";
    public static DynamicModel MULTIBLOCK= new DynamicModel(NAME);
    public static DynamicModel COBALT_BLOCK= new DynamicModel("cobalt_block");

    public static DynamicModel ROTATEMODEL= new DynamicModel("rotate2");

    private static final Logger LOGGER = LogUtils.getLogger();




    @Override
    public void render(SuperBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay)
    {

        BakedModel origin_model = COBALT_BLOCK.get();
        final BlockRenderDispatcher blockRenderer = Minecraft.getInstance().getBlockRenderer();

        BakedModel rotate_model=ROTATEMODEL.get();

        BlockPos blockPos=pBlockEntity.getBlockPos();

        BlockState blockState = pBlockEntity.getLevel().getBlockState(blockPos);

        if(blockState.getBlock() instanceof SuperBlock)
        {

            if(blockState.getValue(SuperBlock.IS_ASSEMBLED))
            {

                int facing=pBlockEntity.getFacingDirection();

                pPoseStack.pushPose();
                pPoseStack.translate(0.5,0,0.5);

                switch (facing)
                {
                    case 1:
                        pPoseStack.mulPose(Vector3f.YN.rotationDegrees(0));
                        break;
                    case 2:
                        pPoseStack.mulPose(Vector3f.YN.rotationDegrees(-90));
                        break;
                    case 3:
                        pPoseStack.mulPose(Vector3f.YN.rotationDegrees(-180));
                        break;
                    case 4:
                        pPoseStack.mulPose(Vector3f.YN.rotationDegrees(-270));
                        break;
                }

                pPoseStack.translate(-0.5,0,-0.5);


                blockRenderer.getModelRenderer().renderModel(
                        pPoseStack.last(),pBufferSource.getBuffer(RenderType.solid()),
                        blockState,rotate_model,1,1,1,
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
