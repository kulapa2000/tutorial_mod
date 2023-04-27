package net.hhc.tutorial.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class DowsingRodItem extends Item {
    public DowsingRodItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if(pContext.getLevel().isClientSide())
        {
            BlockPos positionClicked =pContext.getClickedPos();
            Player player= pContext.getPlayer();
            boolean foundBlock=false;

            for(int i=0;i<=positionClicked.getY()+64;i++)
            {
                Block blockBelow=pContext.getLevel().getBlockState(positionClicked.below(i)).getBlock();

                if(isValuableBlock(blockBelow))
                {
                    outputValuableCoordinate(positionClicked.below(i),player,blockBelow);
                    foundBlock=true;
                    break;
                }
            }

            if(foundBlock==false)
            {
                player.sendMessage(new TranslatableComponent("item.tutorial_mod.dowsing_rod.no_valuables"),player.getUUID());
            }
        }

        return super.useOn(pContext);
    }

    private void outputValuableCoordinate(BlockPos blockPos,Player player,Block blockBelow)
    {
        player.sendMessage(new TextComponent("Found"+blockBelow.asItem().getRegistryName().toString()+"at"+blockPos.getX()+","+blockPos.getY()+","+blockPos.getZ()),player.getUUID());
    }

    private boolean isValuableBlock(Block blockBelow)
    {
        return blockBelow==Blocks.IRON_ORE||blockBelow==Blocks.COPPER_ORE||blockBelow==Blocks.DIAMOND_ORE||blockBelow==Blocks.GOLD_ORE;
    }
}
