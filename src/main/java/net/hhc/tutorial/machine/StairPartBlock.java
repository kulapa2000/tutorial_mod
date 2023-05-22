package net.hhc.tutorial.machine;

import com.mojang.logging.LogUtils;
import net.hhc.tutorial.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.MinecraftForge;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.function.Supplier;

public class StairPartBlock extends StairBlock {

    private final Block base;
    private final BlockState baseState;
    public static final DirectionProperty FACING =  BlockStateProperties.HORIZONTAL_FACING;
    public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;
    public static final EnumProperty<StairsShape> SHAPE = BlockStateProperties.STAIRS_SHAPE;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private final java.util.function.Supplier<BlockState> stateSupplier;

    public static final BooleanProperty IS_ASSEMBLED=BooleanProperty.create("is_assembled");

    public StairPartBlock(Supplier<BlockState> state, Properties properties)
    {
        super(state,properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(HALF, Half.BOTTOM).setValue(IS_ASSEMBLED,false).setValue(SHAPE, StairsShape.STRAIGHT).setValue(WATERLOGGED, Boolean.valueOf(false)));
        this.base = Blocks.AIR; // These are unused, fields are redirected
        this.baseState = Blocks.AIR.defaultBlockState();
        this.stateSupplier = state;
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, HALF, SHAPE, WATERLOGGED,IS_ASSEMBLED);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState)
    {
        if(pState.getValue(StairPartBlock.IS_ASSEMBLED))
        {
            return RenderShape.INVISIBLE;
        }
        return RenderShape.MODEL;
    }

    private static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {

        LOGGER.info("part block ondestroy called");
        if( !level.isClientSide() &&!level.getBlockState(pos).isAir())
        {
            if(level.getBlockState(pos).getValue(StairPartBlock.IS_ASSEMBLED))
            {
                MinecraftForge.EVENT_BUS.post(new BreakEvent(level,pos));

                LOGGER.info("breakevent posted");
            }
        }
        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }


}
