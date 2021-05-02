package top.parak.model;

import top.parak.FrameData;

import javax.swing.*;
import java.awt.*;

/**
 * @author KHighness
 * @since 2021-05-01
 * @apiNote 分数面板
 */
public class ScorePanel extends JPanel {

    private JLabel jLabe1=new JLabel("您的分数："+String.valueOf(FrameData.score)+"分");
    private JLabel jLabe2=new JLabel("对手的分数："+String.valueOf(FrameData.targetScore)+"分");



    public ScorePanel() {
        this.setLayout(null);

        jLabe1.setBounds(0,0,FrameData.boxWidth*6,FrameData.boxWidth);
        this.add(jLabe1);
        jLabe2.setBounds(0,FrameData.boxWidth*1,FrameData.boxWidth*6,FrameData.boxWidth);
        this.add(jLabe2);
    }

    @Override
    protected void paintComponent(Graphics g) {
        jLabe1.setText("您的分数："+String.valueOf(FrameData.score)+"分");
        jLabe2.setText("对手的分数："+String.valueOf(FrameData.targetScore)+"分");
    }
}
