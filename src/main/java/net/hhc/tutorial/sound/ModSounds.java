package net.hhc.tutorial.sound;

import net.hhc.tutorial.TutorialMod;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.logging.Logger;

public class ModSounds {
    private static final Logger logger = Logger.getLogger(ModSounds.class.getName());
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS= DeferredRegister.create(ForgeRegistries.SOUND_EVENTS,TutorialMod.MOD_ID);

    public static void register(IEventBus eventBus)
    {
        SOUND_EVENTS.register(eventBus);
    }

    public static RegistryObject<SoundEvent> DOWSING_ROD_FOUND_ORE=registerSoundEvents("dowsing_rod_found_ore");

    private static RegistryObject<SoundEvent> registerSoundEvents(String name)
    {
        logger.info("sound registered");
        ResourceLocation id=new ResourceLocation(TutorialMod.MOD_ID,name);
        return SOUND_EVENTS.register(name,()->new SoundEvent(id));
    }
}
