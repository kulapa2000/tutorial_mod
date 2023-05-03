package net.hhc.tutorial.machine;

import net.hhc.tutorial.block.ModBlocks;
import net.hhc.tutorial.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class SuperBlockEntity extends BlockEntity {



    private BlockPos blockPos;
    public SuperBlockEntity( BlockPos pPos, BlockState pBlockState)
    {
        super(ModBlockEntities.SUPER_BLOCK.get(), pPos, pBlockState);
    }


    private BlockPos upperBlockPos;
    private BlockPos lowerBlockPos;

    public  final List<BlockPos> childPositions = new ArrayList<>();

    @Override
    protected void saveAdditional(CompoundTag nbt)
    {
        if(upperBlockPos!=null)
        {
            nbt.put("upperBlockPos", NbtUtils.writeBlockPos(upperBlockPos));
        }
        if (lowerBlockPos != null) {
            nbt.put("lowerBlockPos", NbtUtils.writeBlockPos(lowerBlockPos));
        }
        super.saveAdditional(nbt);
        setChanged();
    }

    @Override
    public void load(CompoundTag nbt) {

        if (nbt.contains("upperBlockPos")) {
            upperBlockPos = NbtUtils.readBlockPos(nbt.getCompound("upperBlockPos"));
            childPositions.add(upperBlockPos);
        }

        if (nbt.contains("lowerBlockPos")) {
            lowerBlockPos = NbtUtils.readBlockPos(nbt.getCompound("lowerBlockPos"));
            childPositions.add(lowerBlockPos);
        }
        super.load(nbt);
    }

}
