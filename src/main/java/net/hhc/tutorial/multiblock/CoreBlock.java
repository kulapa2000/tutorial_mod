package net.hhc.tutorial.multiblock;

import com.mojang.logging.LogUtils;
import net.hhc.tutorial.network.ClientboundCoreBlockUpdatepacket;
import net.hhc.tutorial.network.PacketHandler;
import net.hhc.tutorial.network.ServerboundCoreBlockUpdatePacket;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

public class CoreBlock extends Block{

    private static final Logger LOGGER = LogUtils.getLogger();

    public CoreBlock(Properties pProperties) {
        super(pProperties);
    }


    @Override
    public RenderShape getRenderShape(BlockState blockState)
    {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }
}
