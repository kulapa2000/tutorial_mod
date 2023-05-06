package net.hhc.tutorial.machine;

import com.mojang.logging.LogUtils;
import net.hhc.tutorial.block.ModBlocks;
import net.hhc.tutorial.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;

import net.minecraft.world.level.block.entity.BlockEntity;

import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class SuperBlockEntity extends BlockEntity {

    private static final Logger LOGGER = LogUtils.getLogger();


    private BlockPos blockPos;
    public SuperBlockEntity( BlockPos pPos, BlockState pBlockState)
    {
        super(ModBlockEntities.SUPER_BLOCK.get(), pPos, pBlockState);
        LOGGER.info("\u001B[33msuper block entity constructor called\u001B[0m");
        this.blockPos=new BlockPos(pPos).immutable();
        this.childPositions=new ArrayList<>();
        this.childPositions.add(pPos.above(2));
        this.childPositions.add(pPos.above(1));
    }


    private BlockPos firstBlockPos;
    private BlockPos secondBlockPos;
    public   List<BlockPos> childPositions;


    @Override
    protected void saveAdditional(CompoundTag nbt)
    {

        if(this.getBlockState().getValue(SuperBlock.IS_ASSEMBLED))
        {
            if (firstBlockPos != null) {
                nbt.put("firstBlockPos", NbtUtils.writeBlockPos(firstBlockPos));
            }
            if (secondBlockPos != null) {
                nbt.put("secondBlockPos", NbtUtils.writeBlockPos(secondBlockPos));
            }
        }
        super.saveAdditional(nbt);
    }



    @Override
    public void load(CompoundTag nbt)
    {
        super.load(nbt);
        LOGGER.info("\u001B[33m super entity  load nbt called \u001B[0m"+ this.getBlockPos()+"\u001B[33m super entity child list size: \u001B[0m"+this.childPositions.size()+this.childPositions.get(0));

        if(this.getBlockState().getValue(SuperBlock.IS_ASSEMBLED))
        {
            LOGGER.info("\u001B[33m load get state value true  \u001B[0m");
            firstBlockPos = NbtUtils.readBlockPos(nbt.getCompound("firstBlockPos"));
            childPositions.add(firstBlockPos);
            if(this.getLevel().getBlockState(firstBlockPos).getBlock() instanceof PartBlock partBlock)
            {
                partBlock.setSuperBlockPos(this.getBlockPos());
            }

            secondBlockPos = NbtUtils.readBlockPos(nbt.getCompound("secondBlockPos"));
            childPositions.add(secondBlockPos);
            if(this.getLevel().getBlockState(secondBlockPos).getBlock() instanceof PartBlock partBlock)
            {
                partBlock.setSuperBlockPos(this.getBlockPos());
            }

            //LOGGER.info("\u001B[33msuper entity child list loaded,size :   \u001B[0m"+childPositions.size());
        }

    }



}
