package top.parak.util;

import top.parak.FrameData;
import top.parak.model.Block;
import com.alibaba.fastjson.JSON;

import java.util.ArrayList;

/**
 * @author KHighness
 * @since 2021-05-01
 * @apiNote 序列化地图和方块类的工具类，便于Netty传输
 */
public class JsonUtil {

    /**
     * 方法名：getMap
     * 作用：将地图类转成json
     * @param map 地图类
     * @return
     */
    public static String getMap(int[][] map){
        ArrayList<String> strings = new ArrayList<>();
        String s ="";
        for (int[] ints : map) {
            for (int anInt : ints) {
                s+= String.valueOf(anInt);
                s+=",";
            }
            strings.add(s.substring(0,s.length()-1));
            s="";
        }
        return JSON.toJSONString(strings);
    }


    /**
     * 方法名：getBlock
     * 作用：将方块类转成json
     * @param block 方块类
     * @return
     */
    public static String getBlock(Block block){
        int[][] block1 = block.getBlock()[FrameData.direction];
        ArrayList<String> strings = new ArrayList<>();
        String s ="";

        for (int[] ints : block1) {
            for (int anInt : ints) {
                s+= String.valueOf(anInt);
                s+=",";
            }
            strings.add(s.substring(0,s.length()-1));
            s="";
        }
        return JSON.toJSONString(strings);
    }
}
