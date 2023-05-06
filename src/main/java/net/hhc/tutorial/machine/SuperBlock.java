package net.hhc.tutorial.machine;

import com.mojang.logging.LogUtils;
import net.hhc.tutorial.TutorialMod;
import net.hhc.tutorial.block.ModBlocks;
import net.hhc.tutorial.block.custom.CobaltBlasterBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;

import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.List;


public class SuperBlock extends Block implements EntityBlock {

    private static final Logger LOGGER = LogUtils.getLogger();
    public SuperBlock(Properties pProperties)
    {
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState().setValue(IS_ASSEMBLED,false));
    }

    public static final BooleanProperty IS_ASSEMBLED=BooleanProperty.create("is_assembled");

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder)
    {
        pBuilder.add(IS_ASSEMBLED);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit)
    {

        if(!pLevel.isClientSide()&&pPlayer.getMainHandItem().getItem().equals(Items.STICK)&&!pState.getValue(SuperBlock.IS_ASSEMBLED))
        {
            LOGGER.info("check1 pass" );
            if(pLevel.getBlockEntity(pPos) instanceof SuperBlockEntity superBlockEntity)
            {
                LOGGER.info("check2 pass");
                BlockPos firstBlockPos = superBlockEntity.childPositions.get(0);
                BlockPos secondBlockPos = superBlockEntity.childPositions.get(1);

                if(pLevel.getBlockState(firstBlockPos).getBlock().getClass() == PartBlock.class && pLevel.getBlockState(secondBlockPos).getBlock().getClass()== PartBlock.class )
                {
                    LOGGER.info("should assemble");
                    if(!pLevel.getBlockState(pPos).getValue(IS_ASSEMBLED))
                    {
                        LOGGER.info("super block setting called");
                        superBlockEntity.addSuperBlockPosMap(firstBlockPos,pPos);
                        superBlockEntity.addSuperBlockPosMap(secondBlockPos,pPos);
                        LOGGER.info("pos added ");

                        pLevel.setBlock(pPos,superBlockEntity.getBlockState().setValue(SuperBlock.IS_ASSEMBLED,true),2);

                        pLevel.setBlock(firstBlockPos,pLevel.getBlockState(firstBlockPos).setValue(PartBlock.IS_ASSEMBLED,true),2);
                        pLevel.setBlock(secondBlockPos,pLevel.getBlockState(secondBlockPos).setValue(PartBlock.IS_ASSEMBLED,true),2);
                    }
                }

            }
            return InteractionResult.SUCCESS;
        }
       return super.use(pState,pLevel,pPos,pPlayer,pHand,pHit);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState)
    {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new  SuperBlockEntity(pPos,pState);
    }


    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {

        if(!level.isClientSide())
        {
            List<BlockPos> allPartBlocks=SuperBlockEntity.getAllPartBlock(SuperBlockEntity.superBlockPosMap,pos);
            level.setBlock(pos,state.setValue(SuperBlock.IS_ASSEMBLED,false),3);
            for (int i=0;i<allPartBlocks.size();i++)
            {
                level.setBlock(allPartBlocks.get(i),level.getBlockState(allPartBlocks.get(i)).setValue(PartBlock.IS_ASSEMBLED,false),3);
                SuperBlockEntity.superBlockPosMap.remove(allPartBlocks.get(i));
            }
        }

        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }



}

