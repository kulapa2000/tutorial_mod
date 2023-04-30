package net.hhc.tutorial.multiblock;

import com.mojang.logging.LogUtils;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.slf4j.Logger;

public class TestListener implements CheckEventListener {

    private static final Logger LOGGER = LogUtils.getLogger();

    public TestListener(){};

    @Override
    public void onEvent(CheckEvent event)
    {
        BlockPos blockPos=event.getBlockPos();
        BlockState blockState=event.getLevel().getBlockState(blockPos);
        Block block=blockState.getBlock();
        BlockState aboveState=event.getLevel().getBlockState(blockPos.above());
        if(aboveState.getBlock().equals(Blocks.DIAMOND_BLOCK))
        {
            LOGGER.info("Diamond success");
        }
        else
        {
            LOGGER.info("Diamond fail");
        }

    }
}
