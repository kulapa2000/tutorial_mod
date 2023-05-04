package net.hhc.tutorial.machine;

import com.mojang.logging.LogUtils;
import net.hhc.tutorial.block.ModBlocks;
import net.hhc.tutorial.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class SuperBlockEntity extends BlockEntity {

    private static final Logger LOGGER = LogUtils.getLogger();


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
        if(this.getBlockState().getValue(SuperBlock.IS_ASSEMBLED)==true)
        {
            if(upperBlockPos!=null)
            {
                nbt.put("upperBlockPos", NbtUtils.writeBlockPos(upperBlockPos));
            }
            if (lowerBlockPos != null) {
                nbt.put("lowerBlockPos", NbtUtils.writeBlockPos(lowerBlockPos));
            }

        }
        super.saveAdditional(nbt);
    }


    @Override
    public void onChunkUnloaded()
    {
        childPositions.clear();
        super.onChunkUnloaded();
    }


    @Override
    public void load(CompoundTag nbt)
    {
        LOGGER.info("compound tag loadinggggg");
        if (nbt.contains("upperBlockPos")) {
            upperBlockPos = NbtUtils.readBlockPos(nbt.getCompound("upperBlockPos"));
            childPositions.add(upperBlockPos);
            LOGGER.info("add upper blockpos"+childPositions.size());

        }

        if (nbt.contains("lowerBlockPos")) {
            lowerBlockPos = NbtUtils.readBlockPos(nbt.getCompound("lowerBlockPos"));
            childPositions.add(lowerBlockPos);
            LOGGER.info("add lower blockpos"+childPositions.size());
        }

        if(this.childPositions==null)
        {
            LOGGER.info("nbt data load fail");
        }

        if(this.getLevel().getBlockState(childPositions.get(0)).getBlock() instanceof PartBlock partBlock)
        {
            partBlock.setSuperBlockPos(this.getBlockPos());
        }
        if(this.getLevel().getBlockState(childPositions.get(1)).getBlock() instanceof PartBlock partBlock)
        {
            partBlock.setSuperBlockPos(this.getBlockPos());
        }
        super.load(nbt);
    }

}
