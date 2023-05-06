package net.hhc.tutorial.block.custom;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.lwjgl.openal.LOKIIMAADPCM;
import org.slf4j.Logger;

import java.util.Random;

public class SpeedyBlock extends Block {
    private static final Logger LOGGER = LogUtils.getLogger();

    public SpeedyBlock(Properties pProperties) {

        super(pProperties);
        LOGGER.info("speedy block cons called");
    }

    @Override
    public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {
        if(!pLevel.isClientSide())
        {
            if(pEntity instanceof LivingEntity)
            {
                LivingEntity entity= ((LivingEntity) pEntity);
                entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,200,1));
            }
        }
        super.stepOn(pLevel, pPos, pState, pEntity);
    }

}
