package net.hhc.tutorial.machine;

import com.mojang.logging.LogUtils;
import net.hhc.tutorial.block.ModBlocks;
import net.hhc.tutorial.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.*;

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
        LOGGER.info("block entity save additional called");
        ListTag list = new ListTag();
        for (Map.Entry<BlockPos, BlockPos> entry : superBlockPosMap.entrySet()) {
            BlockPos key = entry.getKey();   //part block pos
            BlockPos value = entry.getValue(); //super block pos
            ListTag elementListTag = new ListTag();
            elementListTag.add(DoubleTag.valueOf(key.asLong()));
            elementListTag.add(DoubleTag.valueOf(value.asLong()));
            list.add(elementListTag);
        }
        nbt.put("superBlockPosMap",list);

        LOGGER.info("list tag size:  "+list.size()+" type:  "+list.getType());

        super.saveAdditional(nbt);

        ListTag checklist= nbt.getList("superBlockPosMap", Tag.TAG_LIST);
        LOGGER.info("nbt save check,size:  "+ checklist.size());

    }



    @Override
    public void load(CompoundTag nbt)
    {
        super.load(nbt);
        ListTag list = nbt.getList("superBlockPosMap", Tag.TAG_LIST);
        LOGGER.info("read from list size:  "+list.size());
        for (int i = 0; i < list.size(); i++) {
            ListTag elementListTag = list.getList(i);
            Double keyLong = elementListTag.getDouble(0);
            Double valueLong = elementListTag.getDouble(1);
            superBlockPosMap.put(BlockPos.of(Math.round(keyLong) ), BlockPos.of(Math.round(valueLong)));
        }
        LOGGER.info("hashmap reload check,size:  "+superBlockPosMap.size());

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
