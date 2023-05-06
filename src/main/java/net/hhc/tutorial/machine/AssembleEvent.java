package net.hhc.tutorial.machine;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.eventbus.api.Event;

public class AssembleEvent extends Event {
    private Level level;
    private BlockPos blockPos;

    private BlockState blockState;

    private SuperBlockEntity superBlockEntity;

    public AssembleEvent(Level level, BlockPos blockPos,BlockState blockState,SuperBlockEntity superBlockEntity) {
        this.level = level;
        this.blockPos = blockPos;
        this.blockState=blockState;
        this.superBlockEntity = superBlockEntity;
    }

    public Level getLevel() {
        return this.level;
    }

    public BlockPos getBlockPos() {
        return this.blockPos;
    }

    public BlockState getBlockState() {
        return this.blockState;
    }

    public SuperBlockEntity getSuperBlockEntity() {return this.superBlockEntity;}
}
