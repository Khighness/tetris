package top.parak.model;

import top.parak.FrameData;
import top.parak.listener.MyActionListener;
import top.parak.listener.MyKeyBoardListener;
import top.parak.listener.MyMouseListener;

import javax.swing.*;

/**
 * @author KHighness
 * @since 2021-05-01
 * @apiNote 主窗口
 */
public class MyFrame extends JFrame{

    private MyJPanel myJPanel = new MyJPanel();
    private final MySmallPanel mySmallPanel = new MySmallPanel(myJPanel.getBlock());
    private final TargetPanel targetPanel = new TargetPanel();
    private final ControlPanel controlPanel = new ControlPanel();
    private final ScorePanel scorePanel = new ScorePanel();
    private final MyKeyBoardListener myKeyBoardListener = new MyKeyBoardListener(this);
    private final MyActionListener myActionListener = new MyActionListener(this);
    private final MyMouseListener myMouseListener = new MyMouseListener();

    private final JMenuBar menuBar = new JMenuBar();
    private final JMenu menu1 = new JMenu("游戏");
    private final JMenu menu2 = new JMenu("设置");
    private final JMenu menu3 = new JMenu("关于");

    private final JMenuItem menuItem1 = new JMenuItem("开始游戏");
    private final JMenu menuItem2 = new JMenu("难度选择");
    private final ButtonGroup difficulty = new ButtonGroup();
    private final JRadioButton radioButton1 = new JRadioButton("初级");
    private final JRadioButton radioButton2 = new JRadioButton("中级");
    private final JRadioButton radioButton3 = new JRadioButton("高级");
    private final JMenuItem menuItem3 = new JMenuItem("结束游戏");
    private final JMenuItem menuItem4 = new JMenuItem("重置游戏");
    private final JMenuItem menuItem5 = new JMenuItem("自定义");
    private final JMenuItem menuItem6 = new JMenuItem("帮助");
    private final JMenuItem menuItem7 = new JMenuItem("关于作者");
    private final JMenuItem menuItem8 = new JMenuItem("音乐：关");

    public MyFrame() {
        this.setTitle("俄罗斯方块（对战版）");
        this.setSize((int) (FrameData.boxCol * FrameData.boxWidth * 1.5), (int) (FrameData.boxRow * FrameData.boxWidth * 1.2));
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.initMenu();
        this.setVisible(true);
        this.setLayout(null);

        myJPanel.setBounds(10, 10, FrameData.boxCol * FrameData.boxWidth + 10, FrameData.boxRow * FrameData.boxWidth + 10);
        this.add(myJPanel);
        mySmallPanel.setBounds(FrameData.boxCol * FrameData.boxWidth+30,10,4 * FrameData.smallBoxWidth+10,4 * FrameData.smallBoxWidth + 10);
        this.add(mySmallPanel);
        targetPanel.setBounds(FrameData.boxCol * FrameData.boxWidth+30,4 * FrameData.boxWidth + 10,FrameData.boxCol * FrameData.targetBoxWidth+FrameData.boxWidth + 10,FrameData.boxRow* FrameData.targetBoxWidth+FrameData.boxWidth+10);
        this.add(targetPanel);
        controlPanel.setBounds(FrameData.boxCol * FrameData.boxWidth+30,13 * FrameData.boxWidth + 10,FrameData.boxWidth * 4,FrameData.boxWidth * 6);
        this.add(controlPanel);
        scorePanel.setBounds(FrameData.boxCol * FrameData.boxWidth+30,20 * FrameData.boxWidth + 10,FrameData.boxWidth * 6,FrameData.boxWidth * 2);
        this.add(scorePanel);
        this.addKeyListener(myKeyBoardListener);
    }

    /**
     * 方法名：initMenu
     * 作用：初始化主窗口的菜单栏
     */
    private void initMenu() {
        //添加监听
        this.radioButton1.setActionCommand("初级");
        this.radioButton1.addActionListener(myActionListener);
        this.radioButton2.setActionCommand("中级");
        this.radioButton2.addActionListener(myActionListener);
        this.radioButton3.setActionCommand("高级");
        this.radioButton3.addActionListener(myActionListener);
        //加入按钮组
        difficulty.add(this.radioButton1);
        difficulty.add(this.radioButton2);
        difficulty.add(this.radioButton3);

        radioButton1.addMouseListener(myMouseListener);
        radioButton2.addMouseListener(myMouseListener);
        radioButton3.addMouseListener(myMouseListener);

        this.menuItem2.add(this.radioButton1);
        this.menuItem2.add(this.radioButton2);
        this.menuItem2.add(this.radioButton3);

        //开始游戏监听
        menuItem1.setActionCommand("开始游戏");
        menuItem1.addActionListener(myActionListener);

        //结束游戏监听
        menuItem3.setActionCommand("结束游戏");
        menuItem3.addActionListener(myActionListener);

        //重置游戏监听
        menuItem4.setActionCommand("重置游戏");
        menuItem4.addActionListener(myActionListener);

        menuItem5.setActionCommand("自定义");
        menuItem5.addActionListener(myActionListener);

        menuItem6.setActionCommand("帮助");
        menuItem6.addActionListener(myActionListener);

        menuItem7.setActionCommand("关于作者");
        menuItem7.addActionListener(myActionListener);

        menuItem8.setActionCommand("音乐开/关");
        menuItem8.addActionListener(myActionListener);

        this.menu1.add(this.menuItem1);
        this.menu1.add(this.menuItem2);
        this.menu1.add(this.menuItem4);
        this.menu1.add(this.menuItem8);
        this.menu1.add(this.menuItem3);
        this.menu2.add(this.menuItem5);
        this.menu3.add(this.menuItem6);
        this.menu3.add(this.menuItem7);
        //菜单栏 添加 一级菜单
        this.menuBar.add(this.menu1);
        this.menuBar.add(this.menu2);
        this.menuBar.add(this.menu3);

        //窗口 --> 设置菜单栏
        this.setJMenuBar(this.menuBar);
    }

    public MyJPanel getMyJPanel() {
        return myJPanel;
    }

    public void setMyJPanel(MyJPanel myJPanel) {
        this.myJPanel = myJPanel;
    }

    public JRadioButton getRadioButton1() {
        return radioButton1;
    }

    public JRadioButton getRadioButton2() {
        return radioButton2;
    }

    public JRadioButton getRadioButton3() {
        return radioButton3;
    }

    public ButtonGroup getDifficulty() {
        return difficulty;
    }

    public JMenuBar getMenubar() {
        return menuBar;
    }

    public JMenu getMenu1() {
        return menu1;
    }

    public JMenu getMenu2() {
        return menu2;
    }

    public JMenu getMenu3() {
        return menu3;
    }

    public JMenuItem getMenuItem8() {
        return menuItem8;
    }
}
