package top.parak.model;

import top.parak.FrameData;

import javax.swing.*;
import java.awt.*;

/**
 * @author KHighness
 * @since 2021-05-01
 * @apiNote 对手地图和下落方块显示的面板
 */
public class TargetPanel extends JPanel {

    private JLabel jLabel=new JLabel("对手情况");


    public TargetPanel() {
        this.setLayout(null);
        jLabel.setBounds(0,0, FrameData.boxWidth*4,FrameData.boxWidth);
        this.add(jLabel);
    }

    @Override
    protected void paintComponent(Graphics g) {


        g.setColor(Color.pink);
        //画对手的方块
        if (FrameData.targetBlockDirection!=-1 && FrameData.targetBlockClass!=-1 &&
                FrameData.targetBlockX!=-1 && FrameData.targetBlockY!=-1) {

            int[][] targetBlock = null;
            switch (FrameData.targetBlockClass){
                case 0:
                    targetBlock = FrameData.L1_blocks[FrameData.targetBlockDirection];
                    break;
                case 1:
                    targetBlock = FrameData.L2_blocks[FrameData.targetBlockDirection];
                    break;
                case 2:
                    targetBlock = FrameData.T_blocks[FrameData.targetBlockDirection];
                    break;
                case 3:
                    targetBlock = FrameData.TIAN_blocks[FrameData.targetBlockDirection];
                    break;
                case 4:
                    targetBlock = FrameData.I_blocks[FrameData.targetBlockDirection];
                    break;
            }
            int x = FrameData.targetBlockX;
            int y = FrameData.targetBlockY;

            for (int i = x,ii=0; i <x+3 ; i++,ii++) {
                for (int j = y,jj=0; j < y+3; j++,jj++) {
                    if (targetBlock[jj][ii]==1){
                        g.fillRect(i * FrameData.targetBoxWidth, FrameData.boxWidth+j * FrameData.targetBoxWidth, FrameData.targetBoxWidth, FrameData.targetBoxWidth);
                    }
                }
            }
        }
        //画对方的地图
        for (int i = 0; i < FrameData.targetMap.length; i++) {
            for (int j = 0; j < FrameData.targetMap[i].length; j++) {
                if (FrameData.targetMap[i][j] == 1) {

                    g.fillRect(j * FrameData.targetBoxWidth, FrameData.boxWidth+i * FrameData.targetBoxWidth, FrameData.targetBoxWidth, FrameData.targetBoxWidth);
                }
            }
        }
        g.setColor(Color.black);


        for (int i = 0; i < FrameData.boxRow; i++) {
            for (int j = 0; j < FrameData.boxCol; j++) {
                //画线框
                g.drawRect(j * FrameData.targetBoxWidth, FrameData.boxWidth+i * FrameData.targetBoxWidth, FrameData.targetBoxWidth, FrameData.targetBoxWidth);
            }
        }

    }
}
