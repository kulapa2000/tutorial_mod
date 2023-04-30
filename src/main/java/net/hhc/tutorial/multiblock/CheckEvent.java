package net.hhc.tutorial.multiblock;


import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.util.EventObject;


public class CheckEvent extends EventObject {

    private final BlockPos blockPos;
    private final Level level;

    public CheckEvent(Object source, BlockPos blockPos, Level level) {
        super(source);
        this.blockPos = blockPos;
        this.level=level;
    }

    public Level getLevel() {
        return level;
    }

    public BlockPos getBlockPos() {
        return blockPos;
    }

}
