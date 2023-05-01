package net.hhc.tutorial.multiblock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.AbstractGlassBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Properties;

public class DisplayBlock extends Block implements EntityBlock {
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new DisplayBlockEntity(pPos,pState);
    }

    public DisplayBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public RenderShape getRenderShape(BlockState blockState)
    {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }
}
