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

import java.util.*;

public class SuperBlockEntity extends BlockEntity {

    private static final Logger LOGGER = LogUtils.getLogger();

    public static Map<BlockPos,BlockPos> superBlockPosMap=new HashMap<>();  //key: Partblock, value:  Superblock

    public void addSuperBlockPosMap(BlockPos partBlockPos,BlockPos superBlockPos)
    {
        superBlockPosMap.put(partBlockPos,superBlockPos);
    }

    public void removeSuperBlockPosMap(BlockPos partBlockPos)
    {
        superBlockPosMap.remove(partBlockPos);
    }


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


            secondBlockPos = NbtUtils.readBlockPos(nbt.getCompound("secondBlockPos"));
            childPositions.add(secondBlockPos);


            //LOGGER.info("\u001B[33msuper entity child list loaded,size :   \u001B[0m"+childPositions.size());
        }

    }

    public static <K, V> List<K> getAllPartBlock(Map<K, V> map, BlockPos superBlockPos) {
        List<K> keys = new ArrayList<>();
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (Objects.equals(superBlockPos, entry.getValue())) {
                keys.add(entry.getKey());
            }
        }
        return keys;
    }

}
