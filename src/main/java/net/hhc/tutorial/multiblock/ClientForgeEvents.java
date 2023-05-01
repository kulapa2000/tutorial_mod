package net.hhc.tutorial.multiblock;

import net.hhc.tutorial.TutorialMod;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TutorialMod.MOD_ID,bus= Mod.EventBusSubscriber.Bus.FORGE,value = Dist.CLIENT)
public class ClientForgeEvents {
    private ClientForgeEvents(){}

    @SuppressWarnings("resource")
    @SubscribeEvent
    public static void clientTick(TickEvent.ClientTickEvent event)
    {
        final var player= Minecraft.getInstance().player;
    }
}
