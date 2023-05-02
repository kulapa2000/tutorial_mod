package net.hhc.tutorial.multiblock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class DisplayBlockEntity extends BlockEntity {
    public DisplayBlockEntity( BlockPos pPos, BlockState pBlockState)
    {
        super(ModMultiBlockEntities.DISPLAY_BLOCK.get(), pPos, pBlockState);
    }


}
