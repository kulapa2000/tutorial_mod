package net.hhc.tutorial.machine;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.core.Vec3i;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class MyBlockPos extends BlockPos implements Cloneable {
    public MyBlockPos(int pX, int pY, int pZ) {
        super(pX, pY, pZ);
    }


    @Override
    protected MyBlockPos clone() throws CloneNotSupportedException {
        try
        {
            return (MyBlockPos) super.clone();
        }
        catch  (CloneNotSupportedException e)
        {
            throw new AssertionError(e);
        }

    }
}
