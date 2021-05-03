package top.parak.model;

import top.parak.FrameData;

import javax.swing.*;
import java.awt.*;

/**
 * @author KHighness
 * @since 2021-05-01
 * @apiNote 主面板，显示下落方块和地图
 */
public class MyJPanel extends JPanel {

    private Map map=new Map();

    private Block block=new Block((FrameData.boxCol - 3) / 2,0);

    public MyJPanel() {
        this.setBackground(Color.LIGHT_GRAY);
        this.setLayout(null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        block.drawMap(g);
        map.drawMap(g);
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }
}
