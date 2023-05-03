package net.hhc.tutorial.machine;

import com.mojang.logging.LogUtils;
import net.hhc.tutorial.TutorialMod;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.stream.Stream;

@Mod.EventBusSubscriber(modid = TutorialMod.MOD_ID,bus= Mod.EventBusSubscriber.Bus.FORGE)
public class SuperBlock extends Block implements EntityBlock {

    private SuperBlockEntity superBlockEntity;
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
            else
            {
                LOGGER.info("no assemble");
            }

        }
        return InteractionResult.SUCCESS;
    }

    @Deprecated
    public RenderShape getRenderShape(BlockState pState)
    {

        if(pState.getValue(SuperBlock.IS_ASSEMBLED)==true)
        {
            return RenderShape.INVISIBLE;
        }
        return RenderShape.MODEL;
    }

    public void DisassembleAll(Level level,SuperBlockEntity superBlockEntity)
    {
        level.setBlock(superBlockEntity.childPositions.get(0),level.getBlockState(superBlockEntity.childPositions.get(0)).setValue(PartBlock.IS_ASSEMBLED,false),3);
        level.setBlock(superBlockEntity.childPositions.get(1),level.getBlockState(superBlockEntity.childPositions.get(1)).setValue(PartBlock.IS_ASSEMBLED,false),3);
    }

    @SubscribeEvent
    public static void onBreakEvent(BreakEvent event)
    {
        SuperBlockEntity superBlockEntity = event.getSuperBlockEntity();
        BlockPos superBlockpos=superBlockEntity.getBlockPos();
        event.getLevel().setBlock(superBlockpos.above(1),event.getLevel().getBlockState(superBlockpos.above(1)).setValue(PartBlock.IS_ASSEMBLED,false),3);
        event.getLevel().setBlock(superBlockpos.above(1),event.getLevel().getBlockState(superBlockpos.above(1)).setValue(PartBlock.IS_ASSEMBLED,false),3);
        event.getLevel().setBlock(superBlockpos,superBlockEntity.getBlockState().setValue(SuperBlock.IS_ASSEMBLED,false),3);
        superBlockEntity.childPositions.clear();
        LOGGER.info("breakevent received,superentity pos: "+event.getSuperBlockEntity().getBlockPos());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        superBlockEntity = new SuperBlockEntity(pPos, pState);
        return superBlockEntity;
    }


    //@Override
    //public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {return Shapes.or(Block.box(0, 0, 0, 16, 16, 16), Block.box(0, 0, 0, 16, 32, 16));}


}