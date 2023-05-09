package net.hhc.tutorial.machine;
import com.google.gson.*;
import com.mojang.logging.LogUtils;
import net.hhc.tutorial.TutorialMod;
import net.hhc.tutorial.block.entity.ModBlockEntities;
import net.hhc.tutorial.util.BlueprintUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.*;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.system.CallbackI;
import org.slf4j.Logger;
import java.io.InputStreamReader;
import java.util.*;

@Mod.EventBusSubscriber(modid = TutorialMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SuperBlockEntity extends BlockEntity {

    static JsonArray jsonArray=new JsonArray();
    static Gson gson=new Gson();
    static InputStreamReader reader = new InputStreamReader(SuperBlockEntity.class.getResourceAsStream("/blueprint/test.json"));
    static JsonParser jsonParser = new JsonParser();
    static JsonElement jsonElement = jsonParser.parse(reader);

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
            for (JsonElement element : jsonArray) {
                JsonObject jsonObject = element.getAsJsonObject();
                BlockPos relatieveBlockPos = gson.fromJson(jsonObject.get("pos"), BlockPos.class);
                String className = jsonObject.get("type").getAsString();

                for (int i=1;i<=4;i++)     //1: north, 2:west, 3:south, 4:east
                {
                    BlockPos newPos= BlueprintUtils.rotateCounterClockWise(relatieveBlockPos,i-1);
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

    public SuperBlockEntity( BlockPos pPos, BlockState pBlockState)
    {
        super(ModBlockEntities.SUPER_BLOCK.get(), pPos, pBlockState);
        this.facing_direction=0;
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
}
