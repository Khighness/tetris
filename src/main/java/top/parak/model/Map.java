package top.parak.model;

import top.parak.FrameData;

import java.awt.*;

/**
 * @author KHighness
 * @since 2021-05-01
 * @apiNote 地图类
 */
public class Map {
    private volatile int[][] map;

    public Map() {
        this.map = new int[FrameData.boxRow][FrameData.boxCol];
    }


    public void drawMap(Graphics g) {
        g.setColor(Color.pink);
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j]==1){
                    g.fillRect(j* FrameData.boxWidth,i* FrameData.boxWidth, FrameData.boxWidth, FrameData.boxWidth);
                }
            }
        }
        g.setColor(Color.black);

        for (int i = 0; i < FrameData.boxRow; i++) {
            for (int j = 0; j < FrameData.boxCol ; j++) {
                //画线框
                g.drawRect(j* FrameData.boxWidth,i* FrameData.boxWidth, FrameData.boxWidth, FrameData.boxWidth);
            }
        }
    }


    public int[][] getMap() {
        return map;
    }

    public void setMap(int[][] map) {
        this.map = map;
    }
}
