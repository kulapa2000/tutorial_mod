package net.hhc.tutorial.machine;

import com.mojang.logging.LogUtils;
import net.hhc.tutorial.TutorialMod;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

@Mod.EventBusSubscriber(modid=TutorialMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SuperBlock extends Block  {

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


    @SubscribeEvent
    public static void onPlayerInteract(PlayerInteractEvent event)
    {

        BlockPos blockPos=event.getPos();
        BlockState blockState1=event.getWorld().getBlockState((blockPos.above(1)));
        BlockState blockState2=event.getWorld().getBlockState((blockPos.above(2)));
        if(!event.getWorld().isClientSide()&&event.getPlayer().getMainHandItem().getItem().equals(Items.STICK)&&event.getWorld().getBlockState(blockPos).getBlock() instanceof SuperBlock)
        {
            if(event.getWorld().getBlockState(blockPos.above(1)).getBlock() instanceof PartBlock&&(event.getWorld().getBlockState(blockPos.above(2)).getBlock() instanceof PartBlock))
            {
                LOGGER.info("should assemble");
                event.getWorld().setBlock(blockPos.above(1),blockState1.setValue(IS_ASSEMBLED,true),3);
                event.getWorld().setBlock(blockPos.above(2),blockState1.setValue(IS_ASSEMBLED,true),3);
                event.getWorld().setBlock(blockPos,blockState1.setValue(IS_ASSEMBLED,true),3);
            }
            else
            {
                event.getWorld().setBlock(blockPos,event.getWorld().getBlockState(blockPos).setValue(IS_ASSEMBLED,false),3);
                LOGGER.info("no assemble");
            }

        }
    }

    /*
    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if(!pLevel.isClientSide()&&pPlayer.getMainHandItem().getItem().equals(Items.STICK))
        {
            if(pLevel.getBlockState(pPos.above(1)).getBlock() instanceof PartBlock&&(pLevel.getBlockState(pPos.above(2)).getBlock() instanceof PartBlock))
            {
                LOGGER.info("clicked");
                CheckEvent event = new CheckEvent(this, pPos,pPos.above(1),pPos.above(2) ,pLevel);
                CheckEventHandler.getInstance().dispatchCheckEvent(event);
                pLevel.setBlock(pPos,pState.setValue(IS_ASSEMBLED,true),3);
            }
            else
            {
                pLevel.setBlock(pPos,pState.setValue(IS_ASSEMBLED,false),3);
            }

            PacketHandler.INSTANCE.sendToServer(new ServerboundCoreBlockUpdatePacket(pPos));
            PacketHandler.INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with
                    ( ()->pLevel.getChunkAt(pPos)),new ClientboundCoreBlockUpdatepacket(pPos));

        }
        return InteractionResult.SUCCESS;
    }
*/
    @Deprecated
    public RenderShape getRenderShape(BlockState pState)
    {
        if(pState.getValue(IS_ASSEMBLED)==true)
        {
            return RenderShape.INVISIBLE;
        }

        return RenderShape.MODEL;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        return InteractionResult.SUCCESS;
    }
}
