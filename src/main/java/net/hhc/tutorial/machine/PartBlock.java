package net.hhc.tutorial.machine;

import com.mojang.logging.LogUtils;
import net.hhc.tutorial.TutorialMod;
import net.minecraft.core.BlockPos;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

@Mod.EventBusSubscriber(modid = TutorialMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)

public class PartBlock extends Block {

    public static final BooleanProperty IS_ASSEMBLED=BooleanProperty.create("is_assembled");

    private static final Logger LOGGER = LogUtils.getLogger();


    private BlockPos superBlockPos;

    public BlockPos getSuperBlockPos() {
        return superBlockPos;
    }

    public void setSuperBlockPos(BlockPos superBlockPos) {
        this.superBlockPos = superBlockPos;
    }

    public PartBlock(Properties pProperties)
    {
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(IS_ASSEMBLED,false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(IS_ASSEMBLED);
    }


    @Deprecated
    public RenderShape getRenderShape(BlockState pState)
    {

        if(pState.getValue(PartBlock.IS_ASSEMBLED)==true)
        {
            return RenderShape.INVISIBLE;

        }

        return RenderShape.MODEL;
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid)
    {
        Block block= level.getBlockState(pos).getBlock();
        if(block instanceof PartBlock partBlock)
        {
            if(partBlock.superBlockPos!=null && state.getValue(PartBlock.IS_ASSEMBLED)==true)
            {
                BlockEntity blockEntity=level.getBlockEntity( partBlock.getSuperBlockPos());

                if(blockEntity instanceof SuperBlockEntity superBlockEntity)
                {
                    if(superBlockEntity.childPositions.contains(pos))
                    {
                        int index=superBlockEntity.childPositions.indexOf(pos);
                        superBlockEntity.childPositions.remove(index);
                        level.setBlock(superBlockEntity.childPositions.get(0),level.getBlockState(superBlockEntity.childPositions.get(0)).setValue(PartBlock.IS_ASSEMBLED,false),3);
                        level.setBlock(superBlockPos,superBlockEntity.getBlockState().setValue(SuperBlock.IS_ASSEMBLED,false),3);
                        superBlockEntity.childPositions.clear();
                        LOGGER.info("should break"+superBlockEntity.getBlockPos());

                    }
                }
            }

        }
        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }


}
