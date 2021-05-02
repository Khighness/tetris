package top.parak.model;

import top.parak.FrameData;

import javax.swing.*;
import java.awt.*;

/**
 * @author KHighness
 * @since 2021-05-01
 * @apiNote 下一个方块小面板
 */
public class MySmallPanel extends JPanel {

    private Block block;
    private JLabel jLabel=new JLabel("下");
    private JLabel jLabe2=new JLabel("个");
    private JLabel jLabe3=new JLabel("方");
    private JLabel jLabe4=new JLabel("块");

    public MySmallPanel(Block block) {
        this.setLayout(null);
        this.block=block;

        int width = FrameData.smallBoxWidth * 3 / 4;


        jLabel.setBounds(0,0,width,width);
        this.add(jLabel);
        jLabe2.setBounds(0,width*1,width,width);
        this.add(jLabe2);
        jLabe3.setBounds(0,width*2,width,width);
        this.add(jLabe3);
        jLabe4.setBounds(0,width*3,width,width);
        this.add(jLabe4);


    }

    @Override
    protected void paintComponent(Graphics g) {
        int[][] ints = new int[3][3];
        switch (FrameData.nextBlock){
            case 0:
                ints = FrameData.L1_blocks[0];
                break;
            case 1:
                ints = FrameData.L2_blocks[0];
                break;
            case 2:
                ints = FrameData.T_blocks[0];
                break;
            case 3:
                ints = FrameData.TIAN_blocks[0];
                break;
            case 4:
                ints = FrameData.I_blocks[0];
                break;
        }
        for (int i = 0; i <3; i++) {
            for (int j = 0; j < 3; j++) {
                if (ints[i][j]==1) {
                    g.setColor(Color.pink);
                    g.fillRect((j+1)* FrameData.smallBoxWidth,i* FrameData.smallBoxWidth, FrameData.smallBoxWidth, FrameData.smallBoxWidth);
                    g.setColor(Color.black);
                }
            }
        }
        for (int i = 0; i <3; i++) {
            for (int j = 0; j < 3; j++) {
                g.drawRect((i+1)* FrameData.smallBoxWidth,j* FrameData.smallBoxWidth, FrameData.smallBoxWidth, FrameData.smallBoxWidth);
            }
        }

    }
}
