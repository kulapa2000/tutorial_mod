package net.hhc.tutorial.network;

import com.mojang.logging.LogUtils;
import net.hhc.tutorial.multiblock.CoreBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import org.slf4j.Logger;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class ClientboundCoreBlockUpdatepacket {
    private static final Logger LOGGER = LogUtils.getLogger();
    public final BlockPos coreBlockPos;
    public ClientboundCoreBlockUpdatepacket(BlockPos blockPos)
    {
        this.coreBlockPos=blockPos;
    }

    public void encode(FriendlyByteBuf buffer)
    {
        buffer.writeBlockPos(this.coreBlockPos);
    }

    public ClientboundCoreBlockUpdatepacket(FriendlyByteBuf buffer)
    {
        this(buffer.readBlockPos());
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx)
    {
        final var success=new AtomicBoolean(false);
        ctx.get().enqueueWork( ()->{
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT,()->()->success.set(ClientAccess.updateCoreBlock(coreBlockPos)));
        } );

        ctx.get().setPacketHandled(true);
        return success.get();
    }
}
