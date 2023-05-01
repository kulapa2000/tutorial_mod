package net.hhc.tutorial.network;

import com.mojang.logging.LogUtils;
import net.hhc.tutorial.multiblock.CoreBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.slf4j.Logger;

@SuppressWarnings("resource")
public class ClientAccess {

    private static final Logger LOGGER = LogUtils.getLogger();

    public static boolean updateCoreBlock(BlockPos blockPos)
    {
        final BlockEntity blockEntity= Minecraft.getInstance().level.getBlockEntity(blockPos);   //当需要调用Minecraft类的时候，只能在clientside运行 !
        if(blockEntity instanceof CoreBlockEntity coreBlockEntity)
        {
            LOGGER.info("Server to Client Packet Success");
            return true;
        }
        return false;
    }
}
