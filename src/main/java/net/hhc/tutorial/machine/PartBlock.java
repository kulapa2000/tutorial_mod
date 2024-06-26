package net.hhc.tutorial.machine;

import com.mojang.logging.LogUtils;
import net.hhc.tutorial.TutorialMod;
import net.hhc.tutorial.block.ModBlocks;
import net.minecraft.core.BlockPos;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;
import oshi.annotation.concurrent.Immutable;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = TutorialMod.MOD_ID,bus= Mod.EventBusSubscriber.Bus.FORGE)
public class PartBlock extends Block {

    public static final BooleanProperty IS_ASSEMBLED=BooleanProperty.create("is_assembled");

    private static final Logger LOGGER = LogUtils.getLogger();

    public  BlockPos superBlockPos;

    public PartBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState().setValue(IS_ASSEMBLED,false));
    }



    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(IS_ASSEMBLED);
    }


    @Deprecated
    public RenderShape getRenderShape(BlockState pState)
    {

        if(pState.getValue(PartBlock.IS_ASSEMBLED))
        {
            return RenderShape.INVISIBLE;
        }
        return RenderShape.MODEL;
    }


    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit)
    {
        if(pState.getValue(IS_ASSEMBLED)&&!pLevel.isClientSide())
        {
            LOGGER.info("\u001B[33m part block used, \u001B[0m"+" superblock pos:"+SuperBlockEntity.superBlockPosMap.get(pPos));
            LOGGER.info("hashmap size:   "+SuperBlockEntity.superBlockPosMap.size());

        }
        return InteractionResult.FAIL;
    }



    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {

        LOGGER.info("part block ondestroy called");
        if( !level.isClientSide() &&!level.getBlockState(pos).isAir())
        {
            if(level.getBlockState(pos).getValue(PartBlock.IS_ASSEMBLED))
            {
                MinecraftForge.EVENT_BUS.post(new BreakEvent(level,pos));

                LOGGER.info("breakevent posted");
            }
        }
        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }




}
