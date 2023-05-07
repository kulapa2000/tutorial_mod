package net.hhc.tutorial.machine;

import net.hhc.tutorial.TutorialMod;
import net.hhc.tutorial.recipe.CobaltBlasterRecipe;
import net.hhc.tutorial.recipe.ModRecipes;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.logging.Logger;

public class ModBlueprints {

    private static final Logger logger = Logger.getLogger(ModRecipes.class.getName());
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, TutorialMod.MOD_ID);

    public static final RegistryObject<RecipeSerializer<CobaltBlasterRecipe>> COBALT_BLASTER_SERIALIZER =
            SERIALIZERS.register("cobalt_blaster", () -> CobaltBlasterRecipe.Serializer.INSTANCE);


    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
        //Registry.register(Registry.RECIPE_TYPE, CobaltBlasterRecipe.Type.ID, CobaltBlasterRecipe.Type.INSTANCE);
        logger.info("recipe registered");
    }
}
