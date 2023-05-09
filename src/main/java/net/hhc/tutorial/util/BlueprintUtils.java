package net.hhc.tutorial.util;

import net.minecraft.core.BlockPos;

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
}
