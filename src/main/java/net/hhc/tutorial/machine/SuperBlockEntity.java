package net.hhc.tutorial.machine;

import net.hhc.tutorial.block.ModBlocks;
import net.hhc.tutorial.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class SuperBlockEntity extends BlockEntity {
    public SuperBlockEntity( BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.SUPER_BLOCK.get(), pPos, pBlockState);
    }


}
