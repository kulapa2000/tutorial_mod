package net.hhc.tutorial.multiblock;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.slf4j.Logger;

public class CoreBlock extends Block {
    public CoreBlock(Properties pProperties) {
        super(pProperties);
    }

    private static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if(!pLevel.isClientSide()&&pPlayer.getMainHandItem().getItem().equals(Items.STICK))
        {
            CheckEvent event = new CheckEvent(this, pPos, pLevel);
            CheckEventHandler.getInstance().dispatchCheckEvent(event);
        }

        return InteractionResult.SUCCESS;
    }

}
