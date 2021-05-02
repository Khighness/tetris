package top.parak.model;

import top.parak.FrameData;
import top.parak.listener.MyActionListener;

import javax.swing.*;

/**
 * @author KHighness
 * @since 2021-05-01
 */
public class CustomPanel extends JPanel {

    private JLabel jLabel=new JLabel("勾选方块");
    private JLabel jLabe2=new JLabel("选择速度");

    private JCheckBox jCheckBox1 = new JCheckBox("左L型");
    private JCheckBox jCheckBox2 = new JCheckBox("右L型");
    private JCheckBox jCheckBox3 = new JCheckBox("T型");
    private JCheckBox jCheckBox4 = new JCheckBox("矩型");
    private JCheckBox jCheckBox5 = new JCheckBox("I型");

    private ButtonGroup difficulty =new ButtonGroup();
    private JRadioButton jRadioButton1 = new JRadioButton("初级");
    private JRadioButton jRadioButton2 = new JRadioButton("中级");
    private JRadioButton jRadioButton3 = new JRadioButton("高级");

    private JCheckBox jCheckBox6 = new JCheckBox("音乐播放");
    private JCheckBox jCheckBox7 = new JCheckBox("方块上涨");

    private JButton jButton1=new JButton("确定");
    private JButton jButton2=new JButton("取消");

    private MyActionListener myActionListener;

    public CustomPanel(MyActionListener myActionListener) {

        this.myActionListener=myActionListener;

        difficulty.add(jRadioButton1);
        difficulty.add(jRadioButton2);
        difficulty.add(jRadioButton3);

        this.setLayout(null);
        this.setSize(460,240);
        jLabel.setBounds(30,0,100,50);
        jCheckBox1.setBounds(100,0,70,50);
        if (FrameData.blocksArray[0]!=-1){
            jCheckBox1.setSelected(true);
        }else {
            jCheckBox1.setSelected(false);
        }
        jCheckBox2.setBounds(170,0,70,50);
        if (FrameData.blocksArray[1]!=-1){
            jCheckBox2.setSelected(true);
        }else {
            jCheckBox2.setSelected(false);
        }
        jCheckBox3.setBounds(230,0,70,50);
        if (FrameData.blocksArray[2]!=-1){
            jCheckBox3.setSelected(true);
        }else {
            jCheckBox3.setSelected(false);
        }
        jCheckBox4.setBounds(300,0,70,50);
        if (FrameData.blocksArray[3]!=-1){
            jCheckBox4.setSelected(true);
        }else {
            jCheckBox4.setSelected(false);
        }
        jCheckBox5.setBounds(370,0,70,50);
        if (FrameData.blocksArray[4]!=-1){
            jCheckBox5.setSelected(true);
        }else {
            jCheckBox5.setSelected(false);
        }



        jLabe2.setBounds(30,50,100,50);
        jRadioButton1.setBounds(100,50,70,50);
        jRadioButton2.setBounds(170,50,70,50);
        jRadioButton3.setBounds(230,50,70,50);
        switch ((int) FrameData.difficulty){
            case 500:
                jRadioButton1.setSelected(true);
                break;
            case 300:
                jRadioButton2.setSelected(true);
                break;
            case 100:
                jRadioButton3.setSelected(true);
                break;
        }


        jCheckBox6.setBounds(105,100,100,50);
        if (FrameData.musicOpen){
            jCheckBox6.setSelected(true);
        }else {
            jCheckBox6.setSelected(false);
        }
        jCheckBox7.setBounds(225,100,100,50);
        if (FrameData.blocksUp){
            jCheckBox7.setSelected(true);
        }else {
            jCheckBox7.setSelected(false);
        }



        jButton1.setBounds(145,150,60,30);
        jButton1.setActionCommand("自定义确定");
        jButton1.addActionListener(myActionListener);
        jButton2.setBounds(225,150,60,30);
        jButton2.setActionCommand("自定义取消");



        this.add(jLabel);
        this.add(jCheckBox1);
        this.add(jCheckBox2);
        this.add(jCheckBox3);
        this.add(jCheckBox4);
        this.add(jCheckBox5);

        this.add(jLabe2);
        this.add(jRadioButton1);
        this.add(jRadioButton2);
        this.add(jRadioButton3);

        this.add(jCheckBox6);
        this.add(jCheckBox7);

        this.add(jButton1);
        this.add(jButton2);
    }

    public JLabel getjLabel() {
        return jLabel;
    }

    public JLabel getjLabe2() {
        return jLabe2;
    }

    public JCheckBox getjCheckBox1() {
        return jCheckBox1;
    }

    public JCheckBox getjCheckBox2() {
        return jCheckBox2;
    }

    public JCheckBox getjCheckBox3() {
        return jCheckBox3;
    }

    public JCheckBox getjCheckBox4() {
        return jCheckBox4;
    }

    public JCheckBox getjCheckBox5() {
        return jCheckBox5;
    }

    public ButtonGroup getDifficulty() {
        return difficulty;
    }

    public JRadioButton getjRadioButton1() {
        return jRadioButton1;
    }

    public JRadioButton getjRadioButton2() {
        return jRadioButton2;
    }

    public JRadioButton getjRadioButton3() {
        return jRadioButton3;
    }

    public JCheckBox getjCheckBox6() {
        return jCheckBox6;
    }

    public JCheckBox getjCheckBox7() {
        return jCheckBox7;
    }

    public JButton getjButton1() {
        return jButton1;
    }

    public JButton getjButton2() {
        return jButton2;
    }
}
