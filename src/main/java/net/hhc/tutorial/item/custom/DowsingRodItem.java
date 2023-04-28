package net.hhc.tutorial.item.custom;

import net.hhc.tutorial.item.ModItems;
import net.hhc.tutorial.util.InventoryUtil;
import net.hhc.tutorial.util.ModTags;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import net.minecraft.tags.BlockTags;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class DowsingRodItem extends Item {
    public DowsingRodItem(Properties pProperties) {
        super(pProperties);
    }

    private static final Logger logger = Logger.getLogger(DowsingRodItem.class.getName());

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
                BlockState blockBelowState=pContext.getLevel().getBlockState(positionClicked.below(i));

                if(isValuableBlock(blockBelowState))
                {
                    logger.info("does find");
                    outputValuableCoordinate(positionClicked.below(i),player,blockBelow);
                    foundBlock=true;

                    if(InventoryUtil.hasPlayerStackInInventory(player,ModItems.DATA_TABLET.get()))
                    {
                        addNbtToDataTablet(player,positionClicked.below(i),blockBelow);
                    }
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

    private boolean isValuableBlock(BlockState blockBelowState)
    {
        //logger.info("value pending");
        return blockBelowState.is(ModTags.Blocks.DOWSING_ROD_VALUABLES);
        //Registry.BLOCK.getHolderOrThrow(Registry.BLOCK.getResourceKey(blockBelow).get()).is(ModTags.Blocks.DOWSING_ROD_VALUABLES);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced)
    {
        if(Screen.hasShiftDown())
        {
            pTooltipComponents.add(new TranslatableComponent("tooltip.tutorial_mod.dowsing_rod.tooltip.shift"));
        }
        else
        {
            pTooltipComponents.add(new TranslatableComponent("tooltip.tutorial_mod.dowsing_rod.tooltip"));
        }
    }

    private void addNbtToDataTablet(Player player, BlockPos pos, Block blockBelow) {
        ItemStack dataTablet =
                player.getInventory().getItem(InventoryUtil.getFirstInventoryIndex(player, ModItems.DATA_TABLET.get()));

        CompoundTag nbtData = new CompoundTag();
        nbtData.putString("tutorial_mod.last_found_ore", "Found " + blockBelow.asItem().getRegistryName().toString() + " at (" +
                pos.getX() + ", "+ pos.getY() + ", "+ pos.getZ() + ")");

        dataTablet.setTag(nbtData);
    }
}
