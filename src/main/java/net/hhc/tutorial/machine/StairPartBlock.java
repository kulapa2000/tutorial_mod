package net.hhc.tutorial.machine;

import com.mojang.logging.LogUtils;
import net.hhc.tutorial.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.MinecraftForge;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.function.Supplier;
import java.util.stream.Stream;

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

    private static final VoxelShape SHAPE_N= Stream.of(
            Block.box(13, 0, 0, 16, 3, 3),
            Block.box(13, 0, 13, 16, 3, 16),
            Block.box(0, 0, 13, 3, 3, 16),
            Block.box(0, 0, 0, 3, 3, 3),
            Block.box(3, 2, 4, 13, 6, 5),
            Block.box(2, 0, 1, 14, 2, 14),
            Block.box(3, 0, 15, 13, 2, 16),
            Block.box(3, 2, 5, 13, 14, 14),
            Block.box(3, 0, 14, 13, 7, 15),
            Block.box(4, 13, 7, 12, 15, 13)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();


    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext)
    {
        LOGGER.info("Voxelshape changed");
        if(pState.getValue(StairPartBlock.IS_ASSEMBLED))
        {
            return SHAPE_N;
        }
        else
        {
            return super.getShape(pState,pLevel,pPos,pContext);
        }
    }


}
