package net.hhc.tutorial.machine;

import com.mojang.logging.LogUtils;
import net.hhc.tutorial.TutorialMod;
import net.hhc.tutorial.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;

import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(modid = TutorialMod.MOD_ID,bus= Mod.EventBusSubscriber.Bus.FORGE)
public class SuperBlock extends BaseEntityBlock {

    private static final Logger LOGGER = LogUtils.getLogger();
    public SuperBlock(Properties pProperties)
    {
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState().setValue(IS_ASSEMBLED,false).setValue(FACING, Direction.NORTH));
    }

    public static final BooleanProperty IS_ASSEMBLED=BooleanProperty.create("is_assembled");
    public static final DirectionProperty FACING=DirectionProperty.create("facing");

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder)
    {
        pBuilder.add(IS_ASSEMBLED);
        pBuilder.add(FACING);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {

        return this.defaultBlockState().setValue(FACING,pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit)
    {
        if(!pLevel.isClientSide&&pState.getValue(SuperBlock.IS_ASSEMBLED))
        {
            NetworkHooks.openGui((ServerPlayer) pPlayer, ((SuperBlockEntity) pLevel.getBlockEntity(pPos)),pPos);
        }

        if(!pLevel.isClientSide()&&pPlayer.getMainHandItem().getItem().equals(Items.STICK)&&!pState.getValue(SuperBlock.IS_ASSEMBLED))
        {
            LOGGER.info("check1 pass" );
            if(pLevel.getBlockEntity(pPos) instanceof SuperBlockEntity superBlockEntity)
            {
                LOGGER.info("check2 pass");

                if(ifMatch(pPos,pLevel,SuperBlockEntity.northMap))
                {
                    LOGGER.info("should assemble");
                    assemble(pLevel,pPos,SuperBlockEntity.northMap);
                    superBlockEntity.setFacingDirection(1);

                }
                if(ifMatch(pPos,pLevel,SuperBlockEntity.westMap))
                {
                    LOGGER.info("should assemble");
                    assemble(pLevel,pPos,SuperBlockEntity.westMap);
                    superBlockEntity.setFacingDirection(2);

                }
                if(ifMatch(pPos,pLevel,SuperBlockEntity.southMap))
                {
                    LOGGER.info("should assemble");
                    assemble(pLevel,pPos,SuperBlockEntity.southMap);
                    superBlockEntity.setFacingDirection(3);
                }
                if(ifMatch(pPos,pLevel,SuperBlockEntity.eastMap))
                {
                    LOGGER.info("should assemble");
                    assemble(pLevel,pPos,SuperBlockEntity.eastMap);
                    superBlockEntity.setFacingDirection(4);

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

        if(!level.isClientSide()&&level.getBlockState(pos).getValue(SuperBlock.IS_ASSEMBLED))
        {
            ((SuperBlockEntity) level.getBlockEntity(pos)).drops(pos);
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


    public boolean ifMatch(BlockPos superBlockPos,Level level,Map<BlockPos, SuperBlockEntity.BlockRequirement<String,Integer>> map)
    {
        LOGGER.info("match called");
        boolean isMatch = true;
        for (Map.Entry<BlockPos,  SuperBlockEntity.BlockRequirement<String,Integer>> entry : map.entrySet())
        {
            BlockPos keyPos = entry.getKey();
            String valueType = entry.getValue().getType();
            int state=entry.getValue().getRequiredState();
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

            switch (state)
            {
                case -1: break;
                case 0: if(level.getBlockState(realPos).getValue(FACING)==Direction.NORTH){break;}
                case 1: if(level.getBlockState(realPos).getValue(FACING)==Direction.WEST){break;}
                case 2:if(level.getBlockState(realPos).getValue(FACING)==Direction.SOUTH){break;}
                case 3:if(level.getBlockState(realPos).getValue(FACING)==Direction.EAST){break;}
            }

        }
        return isMatch;
    }


    public void assemble(Level level,BlockPos superBlockPos,Map<BlockPos,SuperBlockEntity.BlockRequirement<String,Integer>> map)
    {
        for (Map.Entry<BlockPos, SuperBlockEntity.BlockRequirement<String,Integer>> entry : map.entrySet())
        {
            BlockPos keyPos = entry.getKey();
            int x = keyPos.getX();
            int y = keyPos.getY();
            int z = keyPos.getZ();
            x += superBlockPos.getX();
            y += superBlockPos.getY();
            z += superBlockPos.getZ();
            BlockPos realPos = new BlockPos(x, y, z);
            SuperBlockEntity.addSuperBlockPosMap(realPos,superBlockPos);
            level.setBlock(realPos,level.getBlockState(realPos).setValue(PartBlock.IS_ASSEMBLED,true),3);
        }
        level.setBlock(superBlockPos,level.getBlockState(superBlockPos).setValue(SuperBlock.IS_ASSEMBLED,true),3);
    }

    @SubscribeEvent
    public static void onBreakEvent(BreakEvent event)
    {
        LOGGER.info("breakevent received");
        BlockPos superBlockPos=SuperBlockEntity.superBlockPosMap.get(event.getBlockPos());
        ((SuperBlockEntity) event.getLevel().getBlockEntity(superBlockPos)).drops(event.getBlockPos());
        event.getLevel().setBlock(superBlockPos,event.getLevel().getBlockState(superBlockPos).setValue(SuperBlock.IS_ASSEMBLED,false),2);
        List<BlockPos> allPartBlocks=SuperBlockEntity.getAllPartBlock(SuperBlockEntity.superBlockPosMap,superBlockPos);
        for (int i=0;i<allPartBlocks.size();i++)
        {
            event.getLevel().setBlock(allPartBlocks.get(i),event.getLevel().getBlockState(allPartBlocks.get(i)).setValue(PartBlock.IS_ASSEMBLED,false),2);
            SuperBlockEntity.superBlockPosMap.remove(allPartBlocks.get(i));
        }
        event.getLevel().setBlock(event.getBlockPos(),event.getLevel().getBlockState(event.getBlockPos()).setValue(PartBlock.IS_ASSEMBLED,false),2);
        SuperBlockEntity.superBlockPosMap.remove(event.getBlockPos());
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, ModBlockEntities.SUPER_BLOCK.get(),SuperBlockEntity::tick);
    }
}

