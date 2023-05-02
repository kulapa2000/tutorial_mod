package net.hhc.tutorial.multiblock;


import com.mojang.logging.LogUtils;
import net.hhc.tutorial.TutorialMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.ForgeModelBakery;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.client.model.data.IModelData;
import org.slf4j.Logger;

import java.util.List;
import java.util.Random;

public class DynamicModel
{

    private final ResourceLocation name;

    public DynamicModel(String desc)
    {
        this.name = new ResourceLocation(TutorialMod.MOD_ID, "block/"+desc);
        ForgeModelBakery.addSpecialModel(this.name);

    }

    public BakedModel get()
    {
        final BlockRenderDispatcher blockRenderer = Minecraft.getInstance().getBlockRenderer();
        return blockRenderer.getBlockModelShaper().getModelManager().getModel(name);
    }

    public List<BakedQuad> getNullQuads()
    {
        return getNullQuads(EmptyModelData.INSTANCE);
    }

    public List<BakedQuad> getNullQuads(IModelData data)
    {
        Random rand=new Random();
        return get().getQuads(null, null,rand, data);
    }

    public ResourceLocation getName()
    {
        return name;
    }
}