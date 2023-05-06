package net.hhc.tutorial.machine;

import net.minecraft.core.BlockPos;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.eventbus.api.Event;


public class BreakEvent extends Event {
    private Level level;
    private BlockPos blockPos;


    public BreakEvent(Level level, BlockPos blockPos) {
        this.level = level;
        this.blockPos = new BlockPos(blockPos);
    }

    public Level getLevel() {
        return level;
    }

    public BlockPos getBlockPos() {
        return blockPos;
    }

}
