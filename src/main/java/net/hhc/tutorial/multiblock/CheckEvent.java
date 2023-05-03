package net.hhc.tutorial.multiblock;


import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.util.EventObject;


public class CheckEvent extends EventObject {

    private final BlockPos superBlockPos;
    private final BlockPos partBlockPos1;
    private final BlockPos partBlockPos2;


    private final Level level;

    public CheckEvent(Object source, BlockPos superBlockPos,BlockPos partBlockPos1,BlockPos partBlockPos2, Level level)
    {
        super(source);
        this.superBlockPos = superBlockPos;
        this.partBlockPos1=partBlockPos1;
        this.partBlockPos2=partBlockPos2;
        this.level=level;
    }

    public Level getLevel() {
        return level;
    }

    public BlockPos getSuperBlockPos() {
        return superBlockPos;
    }

    public BlockPos getPartBlockPos1() {
        return partBlockPos1;
    }

    public BlockPos getPartBlockPos2() {
        return partBlockPos2;
    }


}
