package net.hhc.tutorial.machine;
import com.google.gson.*;
import com.mojang.logging.LogUtils;
import net.hhc.tutorial.TutorialMod;
import net.hhc.tutorial.block.entity.CobaltBlasterBlockEntity;
import net.hhc.tutorial.block.entity.ModBlockEntities;
import net.hhc.tutorial.item.ModItems;
import net.hhc.tutorial.screen.SuperBlockEntityMenu;
import net.hhc.tutorial.util.BlueprintUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.*;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.system.CallbackI;
import org.slf4j.Logger;
import java.io.InputStreamReader;
import java.util.*;

@Mod.EventBusSubscriber(modid = TutorialMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SuperBlockEntity extends BlockEntity implements MenuProvider {

    static JsonArray jsonArray=new JsonArray();
    static Gson gson=new Gson();
    static InputStreamReader reader = new InputStreamReader(SuperBlockEntity.class.getResourceAsStream("/blueprint/test.json"));
    static JsonParser jsonParser = new JsonParser();
    static JsonElement jsonElement = jsonParser.parse(reader);

    protected final ContainerData data;

    private final ItemStackHandler inventory=new ItemStackHandler(4)
    {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler=LazyOptional.empty();


    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap== CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public Component getDisplayName() {
        return new TextComponent("superblock");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
        return new SuperBlockEntityMenu(pContainerId,pInventory,this,this.data);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler=LazyOptional.of(()->inventory);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }


    public void drops(BlockPos blockPos) {
        SimpleContainer container = new SimpleContainer(this.inventory.getSlots());
        for (int i = 0; i < this.inventory.getSlots(); i++) {
            container.setItem(i, this.inventory.getStackInSlot(i));
        }

        Containers.dropContents(this.level, blockPos, container);
    }

    static class BlockRequirement<T,I> {
        private  T type;
        private I requiredState;
        public BlockRequirement( T type,I requiredState) {

            this.type = type;
            this.requiredState=requiredState;
        }

        public T getType() {
            return type;
        }

        public I getRequiredState() {
            return requiredState;
        }
    }

    public static Map<BlockPos,BlockRequirement<String, Integer>> northMap=new HashMap<>();
    public static Map<BlockPos,BlockRequirement<String,Integer>> westMap=new HashMap<>();
    public static Map<BlockPos,BlockRequirement<String,Integer>> southMap=new HashMap<>();
    public static Map<BlockPos,BlockRequirement<String,Integer>> eastMap=new HashMap<>();



    private  int facing_direction;

    public  void setFacingDirection(int direction) {
        this.facing_direction = direction;
    }

    public  int getFacingDirection() {
        return this.facing_direction;
    }

    public static void loadBlueprint()
    {
        LOGGER.info("loadBlueprint called");
        if (jsonElement.isJsonArray())
        {
            jsonArray = jsonElement.getAsJsonArray();
            LOGGER.info("json array size:  "+jsonArray.size() );
            for (JsonElement element : jsonArray) {
                JsonObject jsonObject = element.getAsJsonObject();

                int x=jsonObject.get("pos").getAsJsonObject().get("x").getAsInt();
                int y=jsonObject.get("pos").getAsJsonObject().get("y").getAsInt();
                int z=jsonObject.get("pos").getAsJsonObject().get("z").getAsInt();

                BlockPos relativeBlockPos = new BlockPos(x,y,z);

                String className = jsonObject.get("type").getAsString();
                LOGGER.info("relative BlockPos:  "+ relativeBlockPos + "class name:  "+className);

                for (int i=1;i<=4;i++)     //1: north, 2:west, 3:south, 4:east
                {
                    BlockPos newPos= BlueprintUtils.rotateCounterClockWise(relativeBlockPos,i-1);
                    int state=jsonObject.get("state").getAsInt();
                    state=(state+1)%4;

                    switch (i)
                    {
                        case 1:northMap.put(newPos,new BlockRequirement<>(className,state));break;
                        case 2:westMap.put(newPos,new BlockRequirement<>(className,state));break;
                        case 3:southMap.put(newPos,new BlockRequirement<>(className,state));break;
                        case 4:eastMap.put(newPos,new BlockRequirement<>(className,state));break;
                    }

                }
            }

            LOGGER.info(" north map size: " +northMap.size() );
            LOGGER.info(" west map size: " +westMap.size() );
            LOGGER.info(" south map size: " +southMap.size() );
            LOGGER.info(" east map size: " +eastMap.size() );
        }

    }




    private static final Logger LOGGER = LogUtils.getLogger();

    public static Map<BlockPos,BlockPos> superBlockPosMap=new HashMap<>();  //key: Partblock, value:  Superblock

    public static void addSuperBlockPosMap(BlockPos partBlockPos,BlockPos superBlockPos)
    {
        superBlockPosMap.put(partBlockPos,superBlockPos);
    }

    public void removeSuperBlockPosMap(BlockPos partBlockPos)
    {
        superBlockPosMap.remove(partBlockPos);
    }

    private int progress=0;
    private int maxProgress=72;
    private int fuelTime=0;
    private int maxFuelTime=100;

    public SuperBlockEntity( BlockPos pPos, BlockState pBlockState)
    {
        super(ModBlockEntities.SUPER_BLOCK.get(), pPos, pBlockState);
        this.facing_direction=0;

        this.data=new ContainerData()
        {
            public int get(int index)
            {
                switch (index)
                {
                    case 0: return SuperBlockEntity.this.progress;
                    case 1: return SuperBlockEntity.this.maxProgress;
                    default: return 0;
                }
            }

            public void set(int index, int value)
            {
                switch(index)
                {
                    case 0: SuperBlockEntity.this.progress = value; break;
                    case 1: SuperBlockEntity.this.maxProgress = value; break;
                }
            }

            public int getCount() {
                return 2;
            }
        };
    }



    @Override
    protected void saveAdditional(CompoundTag nbt)
    {
        LOGGER.info("block entity save additional called");
        ListTag list = new ListTag();
        for (Map.Entry<BlockPos, BlockPos> entry : superBlockPosMap.entrySet())
        {
            BlockPos key = entry.getKey();   //part block pos
            BlockPos value = entry.getValue(); //super block pos
            ListTag elementListTag = new ListTag();


            elementListTag.add(DoubleTag.valueOf(key.asLong()));
            elementListTag.add(DoubleTag.valueOf(value.asLong()));
            list.add(elementListTag);

        }
        nbt.put("superBlockPosMap",list);
        nbt.putInt("facing",facing_direction);

        nbt.putInt("holder",1);
        nbt.put("inventory",inventory.serializeNBT());
        nbt.putInt("superblock.progress",this.progress);
        super.saveAdditional(nbt);

        ListTag checklist= nbt.getList("superBlockPosMap", Tag.TAG_LIST);
        LOGGER.info("nbt save check,size:  "+ checklist.size());

    }

    @Override
    public void load(CompoundTag nbt)
    {
        super.load(nbt);
        ListTag list = nbt.getList("superBlockPosMap", Tag.TAG_LIST);
        for (int i = 0; i < list.size(); i++) {
            ListTag elementListTag = list.getList(i);
            Double keyLong = elementListTag.getDouble(0);
            Double valueLong = elementListTag.getDouble(1);
            superBlockPosMap.put(BlockPos.of(Math.round(keyLong)),BlockPos.of(Math.round(valueLong)));
        }
        this.facing_direction=nbt.getInt("facing");
        inventory.deserializeNBT(nbt.getCompound("inventory"));
        this.progress=nbt.getInt("superblock.progress");

        LOGGER.info("hashmap reload check,size:  "+superBlockPosMap.size());
    }

    public static <K, V> List<K> getAllPartBlock(Map<K, V> map, BlockPos superBlockPos) {
        List<K> keys = new ArrayList<>();
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (Objects.equals(superBlockPos, entry.getValue())) {
                keys.add(entry.getKey());
            }
        }
        return keys;
    }

    @SubscribeEvent
    public static void onWorldLoad(WorldEvent.Load event)
    {
        LOGGER.info("onworldload called");
        SuperBlockEntity.loadBlueprint();
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag compoundTag=saveWithoutMetadata();
        load(compoundTag);

        return compoundTag;
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, SuperBlockEntity superBlockEntity)
    {
        if(hasRecipe(superBlockEntity)&&!reachStackLimit(superBlockEntity))
        {
            superBlockEntity.progress++;
            if(superBlockEntity.progress>=superBlockEntity.maxProgress)
            {
                processItem(superBlockEntity);
                superBlockEntity.progress=0;
            }
        }
        else
        {
            superBlockEntity.progress=0;
        }

    }

    private static void  processItem(SuperBlockEntity superBlockEntity)
    {
        superBlockEntity.inventory.extractItem(0,1,false);
        superBlockEntity.inventory.setStackInSlot(1, new ItemStack(ModItems.COBALT_INGOT.get(),superBlockEntity.inventory.getStackInSlot(1).getCount()+1));
        superBlockEntity.inventory.setStackInSlot(2, new ItemStack(ModItems.COAL_SILVER.get(),superBlockEntity.inventory.getStackInSlot(2).getCount()+1));

    }

    private static boolean hasRecipe(SuperBlockEntity superBlockEntity)
    {
        if(superBlockEntity.inventory.getStackInSlot(0).getItem()== Items.GUNPOWDER)
        {
            return true;
        }
        return false;
    }

    private static boolean reachStackLimit(SuperBlockEntity superBlockEntity)
    {
        if(superBlockEntity.inventory.getStackInSlot(1).getCount()<superBlockEntity.inventory.getStackInSlot(1).getMaxStackSize()&&superBlockEntity.inventory.getStackInSlot(2).getCount()<superBlockEntity.inventory.getStackInSlot(2).getMaxStackSize())
        {
            return false;
        }
        return true;
    }
}
