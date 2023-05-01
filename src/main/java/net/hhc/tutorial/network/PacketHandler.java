package net.hhc.tutorial.network;

import com.mojang.logging.LogUtils;
import net.hhc.tutorial.TutorialMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;


public class PacketHandler {
    private PacketHandler(){}

    private static final String PROTOCOL_VERSION="1";

    public static final SimpleChannel INSTANCE= NetworkRegistry.newSimpleChannel
            (new ResourceLocation(TutorialMod.MOD_ID),()->PROTOCOL_VERSION,PROTOCOL_VERSION::equals,PROTOCOL_VERSION::equals);
    public static void init()
    {
        int index=0;
        INSTANCE.messageBuilder(ServerboundCoreBlockUpdatePacket.class,index++, NetworkDirection.PLAY_TO_SERVER)
                .encoder(ServerboundCoreBlockUpdatePacket::encode)
                .decoder(ServerboundCoreBlockUpdatePacket::new)
                .consumer(ServerboundCoreBlockUpdatePacket::handle)
                .add();
        INSTANCE.messageBuilder(ClientboundCoreBlockUpdatepacket.class,index++,NetworkDirection.PLAY_TO_CLIENT)
                .encoder(ClientboundCoreBlockUpdatepacket::encode)
                .decoder(ClientboundCoreBlockUpdatepacket::new)
                .consumer(ClientboundCoreBlockUpdatepacket::handle)
                .add();
    }


}
