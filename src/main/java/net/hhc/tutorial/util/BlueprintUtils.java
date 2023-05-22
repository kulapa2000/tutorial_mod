package net.hhc.tutorial.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

public class BlueprintUtils {
    public static BlockPos rotateCounterClockWise(BlockPos blockPos, int times)
    {
        double angle=times*Math.PI/2;
        double cos=Math.cos(angle);
        double sin=Math.sin(angle);
        int newX= (int) (blockPos.getX()*cos+blockPos.getZ()*sin);
        int newZ= (int) (blockPos.getZ()*cos-blockPos.getX()*sin);
        return new BlockPos(newX,blockPos.getY(),newZ);
    }

    public static Direction intToDirection(int i)
    {
        switch (i)
        {
            case 0: return Direction.NORTH;
            case 1: return Direction.WEST;
            case 2: return Direction.SOUTH;
            case 3: return Direction.EAST;
            default: return Direction.NORTH;
        }
    }
}
