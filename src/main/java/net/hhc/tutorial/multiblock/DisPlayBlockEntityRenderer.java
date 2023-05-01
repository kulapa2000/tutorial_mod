package net.hhc.tutorial.multiblock;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;

import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;

import net.minecraftforge.client.model.data.EmptyModelData;


public class DisPlayBlockEntityRenderer implements BlockEntityRenderer<DisplayBlockEntity> {

    private final BlockEntityRendererProvider.Context context;
    public DisPlayBlockEntityRenderer(BlockEntityRendererProvider.Context context)
    {
        this.context=context;
    }
    @Override
    public void render(DisplayBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay)
    {
        BlockRenderDispatcher dispatcher= this.context.getBlockRenderDispatcher();
        final ItemRenderer itemRenderer=Minecraft.getInstance().getItemRenderer();
        dispatcher.renderSingleBlock(Blocks.GLASS.defaultBlockState(),pPoseStack,pBufferSource,pPackedLight,pPackedOverlay, EmptyModelData.INSTANCE);

        LocalPlayer player=Minecraft.getInstance().player;
        final ItemStack heldItem=player.getMainHandItem();
        pPoseStack.pushPose();
        pPoseStack.scale(0.75f,0.75f,0.75f);
        pPoseStack.translate(0.5f,0.5f,0.5f);
        itemRenderer.renderStatic(player,heldItem, ItemTransforms.TransformType.FIXED,false,pPoseStack,pBufferSource,Minecraft.getInstance().level, pPackedLight,pPackedOverlay,0);
        pPoseStack.popPose();
    }




}
