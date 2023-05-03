package net.hhc.tutorial.machine;

import com.mojang.logging.LogUtils;
import net.hhc.tutorial.TutorialMod;
import net.hhc.tutorial.multiblock.CheckEvent;
import net.hhc.tutorial.multiblock.CheckEventHandler;
import net.hhc.tutorial.multiblock.CheckEventListener;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.Collection;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = TutorialMod.MOD_ID)
public class PartBlock extends Block {

    private static final Logger LOGGER = LogUtils.getLogger();


    public PartBlock(Properties pProperties)
    {
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState().setValue(IS_ASSEMBLED,false));

    }

    public static final BooleanProperty IS_ASSEMBLED=BooleanProperty.create("is_assembled");

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(IS_ASSEMBLED);
    }


    @Deprecated
    public RenderShape getRenderShape(BlockState pState)
    {
        if(pState.getValue(IS_ASSEMBLED)==true)
        {
            return RenderShape.INVISIBLE;
        }

        return RenderShape.MODEL;
    }

}
