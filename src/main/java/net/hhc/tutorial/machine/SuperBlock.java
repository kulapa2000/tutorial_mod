package net.hhc.tutorial.machine;

import com.mojang.logging.LogUtils;
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


public class SuperBlock extends Block implements EntityBlock {

    private static final Logger LOGGER = LogUtils.getLogger();
    public SuperBlock(Properties pProperties)
    {
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState().setValue(IS_ASSEMBLED,false));
    }

    public static final BooleanProperty IS_ASSEMBLED=BooleanProperty.create("is_assembled");

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(IS_ASSEMBLED);
    }



    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if(!pLevel.isClientSide()&&pPlayer.getMainHandItem().getItem().equals(Items.STICK))
        {
            BlockState blockState1=pLevel.getBlockState((pPos.above(1)));
            BlockState blockState2=pLevel.getBlockState((pPos.above(2)));

            BlockPos upperPos = pPos.above(1);
            BlockPos lowerPos = pPos.above(2);


            if(pLevel.getBlockState(pPos.above(1)).getBlock() instanceof PartBlock&&(pLevel.getBlockState(pPos.above(2)).getBlock() instanceof PartBlock))
            {
                LOGGER.info("should assemble");
                BlockEntity blockEntity=pLevel.getBlockEntity(pPos);

                if(blockEntity instanceof SuperBlockEntity)
                {
                    ((SuperBlockEntity) blockEntity).childPositions.add(upperPos);
                    ((SuperBlockEntity) blockEntity).childPositions.add(lowerPos);

                }

                pLevel.setBlock(pPos,pState.setValue(SuperBlock.IS_ASSEMBLED,true),3);
                pLevel.setBlock(pPos.above(1),blockState1.setValue(PartBlock.IS_ASSEMBLED,true),3);
                pLevel.setBlock(pPos.above(2),blockState2.setValue(PartBlock.IS_ASSEMBLED,true),3);

                if(blockState1.getBlock() instanceof PartBlock partBlock1)
                {
                    partBlock1.setSuperBlockPos(pPos);
                }

                if(blockState1.getBlock() instanceof PartBlock partBlock2)
                {
                    partBlock2.setSuperBlockPos(pPos);
                }
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Deprecated
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
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid)
    {
        if(state.getValue(SuperBlock.IS_ASSEMBLED)==true)
        {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof SuperBlockEntity superBlockEntity)
            {
                if(superBlockEntity.childPositions.size()>=2)
                {
                    LOGGER.info("child list size:  "+superBlockEntity.childPositions.size());
                    level.setBlock(superBlockEntity.childPositions.get(0), level.getBlockState(superBlockEntity.childPositions.get(0)).setValue(PartBlock.IS_ASSEMBLED, false), 3);
                    level.setBlock(superBlockEntity.childPositions.get(1), level.getBlockState(superBlockEntity.childPositions.get(1)).setValue(PartBlock.IS_ASSEMBLED, false), 3);

                    superBlockEntity.childPositions.clear();
                }
            }
        }
            return  super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }

    @Override
    public void onBlockStateChange(LevelReader level, BlockPos pos, BlockState oldState, BlockState newState) {
        if(level.getBlockEntity(pos) instanceof SuperBlockEntity superBlockEntity)
        {
            superBlockEntity.setChanged();
        }
        super.onBlockStateChange(level, pos, oldState, newState);
    }

}

