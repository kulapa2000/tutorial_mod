package net.hhc.tutorial.machine;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

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
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;


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
                if(ifMatch(pPos,pLevel))
                {
                    LOGGER.info("should assemble");


                        for (Map.Entry<BlockPos, String> entry : SuperBlockEntity.checkPosMap.entrySet())
                        {
                            BlockPos keyPos = entry.getKey();
                            int x = keyPos.getX();
                            int y = keyPos.getY();
                            int z = keyPos.getZ();
                            x += pPos.getX();
                            y += pPos.getY();
                            z += pPos.getZ();
                            BlockPos realPos = new BlockPos(x, y, z);
                            superBlockEntity.addSuperBlockPosMap(realPos,pPos);
                            pLevel.setBlock(realPos,pLevel.getBlockState(realPos).setValue(PartBlock.IS_ASSEMBLED,true),3);
                        }
                        pLevel.setBlock(pPos,superBlockEntity.getBlockState().setValue(SuperBlock.IS_ASSEMBLED,true),3);
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


    public boolean ifMatch(BlockPos superBlockPos,Level level)
    {
        boolean isMatch = true;
        for (Map.Entry<BlockPos, String> entry : SuperBlockEntity.checkPosMap.entrySet())
        {
            BlockPos keyPos = entry.getKey();
            String valueType = entry.getValue();
            int x = keyPos.getX();
            int y = keyPos.getY();
            int z = keyPos.getZ();
            x += superBlockPos.getX();
            y += superBlockPos.getY();
            z += superBlockPos.getZ();
            BlockPos realPos = new BlockPos(x, y, z);
            LOGGER.info(" valueType:" + valueType+"block class name:"+ level.getBlockState(realPos).getBlock().getClass().getName());
            if (!level.getBlockState(realPos).getBlock().getClass().getName().equals(valueType))
            {
                isMatch = false;
                break;
            }
        }
        return isMatch;
    }
}

