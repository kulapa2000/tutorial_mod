package net.hhc.tutorial.machine;

import com.mojang.logging.LogUtils;
import net.hhc.tutorial.TutorialMod;
import net.hhc.tutorial.network.ClientboundCoreBlockUpdatepacket;
import net.hhc.tutorial.network.PacketHandler;
import net.hhc.tutorial.network.ServerboundCoreBlockUpdatePacket;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.Arrays;

import static net.hhc.tutorial.machine.PartBlock.IS_ASSEMBLED;


@Mod.EventBusSubscriber(modid = TutorialMod.MOD_ID,bus= Mod.EventBusSubscriber.Bus.FORGE)
public class SuperBlock extends Block {

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
                    ((SuperBlockEntity) blockEntity).childPositions.set(0,upperPos);
                    ((SuperBlockEntity) blockEntity).childPositions.set(1,lowerPos);
                }


                pLevel.setBlock(pPos,pState.setValue(SuperBlock.IS_ASSEMBLED,true),3);
                pLevel.setBlock(pPos.above(1),blockState1.setValue(PartBlock.IS_ASSEMBLED,true),3);
                pLevel.setBlock(pPos.above(2),blockState2.setValue(PartBlock.IS_ASSEMBLED,true),3);

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




    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid)
    {
        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }
}
