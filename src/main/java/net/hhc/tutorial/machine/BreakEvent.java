package net.hhc.tutorial.machine;

import net.minecraft.core.BlockPos;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.eventbus.api.Event;


public class BreakEvent extends Event {
    private Level level;
    private BlockPos blockPos;

    private BlockState blockState;

    private SuperBlockEntity superBlockEntity;

    public BreakEvent(Level level, BlockPos blockPos,BlockState blockState,SuperBlockEntity superBlockEntity) {
        this.level = level;
        this.blockPos = blockPos;
        this.blockState=blockState;
        this.superBlockEntity = superBlockEntity;
    }

    public Level getLevel() {
        return level;
    }

    public BlockPos getBlockPos() {
        return blockPos;
    }

    public BlockState getBlockState() {
        return blockState;
    }

    public SuperBlockEntity getSuperBlockEntity() {return superBlockEntity;}
}
