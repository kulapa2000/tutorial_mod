package net.hhc.tutorial.multiblock;

import net.hhc.tutorial.TutorialMod;
import net.hhc.tutorial.network.PacketHandler;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = TutorialMod.MOD_ID,bus= Mod.EventBusSubscriber.Bus.MOD)
public class CommonModEvents {

    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent event)
    {
        event.enqueueWork(PacketHandler::init);
    }
}
