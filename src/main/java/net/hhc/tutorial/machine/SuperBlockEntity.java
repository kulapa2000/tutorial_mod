package net.hhc.tutorial.machine;
import com.google.gson.*;
import com.mojang.logging.LogUtils;
import net.hhc.tutorial.TutorialMod;
import net.hhc.tutorial.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.*;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraftforge.fml.common.Mod;
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

    public static Map<BlockPos,String> northMap=new HashMap<>();
    public static Map<BlockPos,String> westMap=new HashMap<>();
    public static Map<BlockPos,String> southMap=new HashMap<>();
    public static Map<BlockPos,String> eastMap=new HashMap<>();

    public int facing_direction=0;

    public  void loadBlueprint()
    {
        LOGGER.info("loadBlueprint called");
        if (jsonElement.isJsonArray())
        {
            jsonArray = jsonElement.getAsJsonArray();
            for (JsonElement element : jsonArray) {
                JsonObject jsonObject = element.getAsJsonObject();
                BlockPos relatieveBlockPos = gson.fromJson(jsonObject.get("pos"), BlockPos.class);
                String className = jsonObject.get("type").getAsString();
                String facing=jsonObject.get("facing").getAsString();

                switch (facing)
                {
                    case"south":southMap.put(relatieveBlockPos,className); break;
                    case"west":westMap.put(relatieveBlockPos,className);   break;
                    case"east":eastMap.put(relatieveBlockPos,className);break;
                    case"north":northMap.put(relatieveBlockPos,className);break;
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
        loadBlueprint();
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

}
