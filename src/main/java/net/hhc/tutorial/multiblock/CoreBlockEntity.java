package net.hhc.tutorial.multiblock;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class CoreBlockEntity extends BlockEntity  {

    public CoreBlockEntity( BlockPos pPos, BlockState pBlockState)
    {
        super(ModMultiBlockEntities.CORE_BLOCK.get(), pPos, pBlockState);
    }

}
