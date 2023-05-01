package net.hhc.tutorial.network;

import com.mojang.logging.LogUtils;
import net.hhc.tutorial.multiblock.CoreBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import org.slf4j.Logger;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class ServerboundCoreBlockUpdatePacket {
    private static final Logger LOGGER = LogUtils.getLogger();
    public final BlockPos coreBlockPos;
    public ServerboundCoreBlockUpdatePacket(BlockPos blockPos)
    {
        this.coreBlockPos=blockPos;
    }

    public void encode(FriendlyByteBuf buffer)
    {
        buffer.writeBlockPos(this.coreBlockPos);
    }

    public ServerboundCoreBlockUpdatePacket(FriendlyByteBuf buffer)
    {
        this(buffer.readBlockPos());
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx)
    {
        final var success=new AtomicBoolean(false);
        ctx.get().enqueueWork( ()->{
            BlockEntity blockEntity=ctx.get().getSender().level.getBlockEntity(coreBlockPos);
            if(blockEntity instanceof CoreBlockEntity coreBlock)
            {
                LOGGER.info("Client to Server Packet Succeed");
            }
        }  );

        ctx.get().setPacketHandled(true);
        return success.get();
    }
}
