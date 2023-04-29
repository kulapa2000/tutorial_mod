package net.hhc.tutorial.block.custom;

import net.hhc.tutorial.item.ModItems;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.BeetrootBlock;

public class TurnipCropBlock extends BeetrootBlock {
    public TurnipCropBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected ItemLike getBaseSeedId()
    {
        return ModItems.TURNIP_SEEDS.get();
    }


}
