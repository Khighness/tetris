package top.parak.model;

import top.parak.FrameData;

import javax.swing.*;

/**
 * @author KHighness
 * @since 2021-05-01
 */
public class ControlPanel extends JPanel {

    private JLabel jLabel=new JLabel("功能键盘");
    private JLabel jLabe2=new JLabel("上：大写W");
    private JLabel jLabe3=new JLabel("下：大写S");
    private JLabel jLabe4=new JLabel("左：大写A");
    private JLabel jLabe5=new JLabel("右：大写D");
    private JLabel jLabe6=new JLabel("暂停/继续：空格");


    public ControlPanel() {
        this.setLayout(null);
        jLabel.setBounds(0,0, FrameData.boxWidth*4,FrameData.boxWidth);
        this.add(jLabel);
        jLabe2.setBounds(0,FrameData.boxWidth*1,FrameData.boxWidth*4,FrameData.boxWidth);
        this.add(jLabe2);
        jLabe3.setBounds(0,FrameData.boxWidth*2,FrameData.boxWidth*4,FrameData.boxWidth);
        this.add(jLabe3);
        jLabe4.setBounds(0,FrameData.boxWidth*3,FrameData.boxWidth*4,FrameData.boxWidth);
        this.add(jLabe4);
        jLabe5.setBounds(0,FrameData.boxWidth*4,FrameData.boxWidth*4,FrameData.boxWidth);
        this.add(jLabe5);
        jLabe6.setBounds(0,FrameData.boxWidth*5,FrameData.boxWidth*4,FrameData.boxWidth);
        this.add(jLabe6);
    }


}
