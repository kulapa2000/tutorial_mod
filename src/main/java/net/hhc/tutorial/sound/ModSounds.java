package net.hhc.tutorial.sound;

import net.hhc.tutorial.TutorialMod;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.common.util.ForgeSoundType;
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

    public static RegistryObject<SoundEvent> COBALT_LAMP_BREAK = registerSoundEvents("cobalt_lamp_break");
    public static RegistryObject<SoundEvent> COBALT_LAMP_STEP = registerSoundEvents("cobalt_lamp_step");
    public static RegistryObject<SoundEvent> COBALT_LAMP_PLACE = registerSoundEvents("cobalt_lamp_place");
    public static RegistryObject<SoundEvent> COBALT_LAMP_HIT = registerSoundEvents("cobalt_lamp_hit");
    public static RegistryObject<SoundEvent> COBALT_LAMP_FALL = registerSoundEvents("cobalt_lamp_fall");

    public static final ForgeSoundType COBALT_LAMP_SOUNDS= new ForgeSoundType(1f,1f,
            ModSounds.COBALT_LAMP_BREAK,
            ModSounds.COBALT_LAMP_STEP,
            ModSounds.COBALT_LAMP_PLACE,
            ModSounds.COBALT_LAMP_HIT,
            ModSounds.COBALT_LAMP_FALL);
}
