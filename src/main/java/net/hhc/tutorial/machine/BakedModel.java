package net.hhc.tutorial.machine;

import net.hhc.tutorial.TutorialMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.ForgeModelBakery;

public class BakedModel {

    private final ResourceLocation name;

    public BakedModel(String desc)
    {
        this.name = new ResourceLocation(TutorialMod.MOD_ID, "block/"+desc);
        ForgeModelBakery.addSpecialModel(this.name);
    }

    public net.minecraft.client.resources.model.BakedModel get()
    {
        final BlockRenderDispatcher blockRenderer = Minecraft.getInstance().getBlockRenderer();
        return blockRenderer.getBlockModelShaper().getModelManager().getModel(name);
    }
}