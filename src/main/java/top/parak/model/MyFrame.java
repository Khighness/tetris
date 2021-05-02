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
    private MySmallPanel mySmallPanel = new MySmallPanel(myJPanel.getBlock());
    private TargetPanel targetPanel = new TargetPanel();
    private ControlPanel controlPanel = new ControlPanel();
    private ScorePanel scorePanel = new ScorePanel();
    private MyKeyBoardListener myKeyBoardListener = new MyKeyBoardListener(this);
    private MyActionListener myActionListener = new MyActionListener(this);
    private MyMouseListener myMouseListener = new MyMouseListener();


    private JMenuBar menuBar = new JMenuBar();
    private JMenu menu1 = new JMenu("游戏");
    private JMenu menu2 = new JMenu("设置");
    private JMenu menu3 = new JMenu("关于");

    private JMenuItem jmenuItem1 = new JMenuItem("开始游戏");
    private JMenu jmenuItem2 = new JMenu("难度选择");
    private ButtonGroup difficulty =new ButtonGroup();
    private JRadioButton jRadioButton1 = new JRadioButton("初级");
    private JRadioButton jRadioButton2 = new JRadioButton("中级");
    private JRadioButton jRadioButton3 = new JRadioButton("高级");
    private JMenuItem jmenuItem3 = new JMenuItem("结束游戏");
    private JMenuItem jmenuItem4 = new JMenuItem("重置游戏");
    private JMenuItem jmenuItem5 = new JMenuItem("自定义");
    private JMenuItem jmenuItem6 = new JMenuItem("帮助");
    private JMenuItem jmenuItem7 = new JMenuItem("关于作者");
    private JMenuItem jmenuItem8 = new JMenuItem("音乐：关");

    public MyFrame() {

        this.setTitle("俄罗斯方块（对战版）");

        this.setSize((int) (FrameData.boxCol* FrameData.boxWidth*1.5), (int) (FrameData.boxRow* FrameData.boxWidth*1.2));

        this.setLocationRelativeTo(null);
        //设置不可改变大小
        this.setResizable(false);

//        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        //调用定义菜单栏的函数
        this.initMenu();

        //设置窗口可见
        this.setVisible(true);

        this.setLayout(null);
        myJPanel.setBounds(10, 10, FrameData.boxCol* FrameData.boxWidth+10, FrameData.boxRow* FrameData.boxWidth+10);
        this.add(myJPanel);

        mySmallPanel.setBounds(FrameData.boxCol* FrameData.boxWidth+30,10,4* FrameData.smallBoxWidth+10,4* FrameData.smallBoxWidth+10);
        this.add(mySmallPanel);

        targetPanel.setBounds(FrameData.boxCol* FrameData.boxWidth+30,4* FrameData.boxWidth+10,FrameData.boxCol* FrameData.targetBoxWidth+FrameData.boxWidth+10,FrameData.boxRow* FrameData.targetBoxWidth+FrameData.boxWidth+10);
        this.add(targetPanel);

        controlPanel.setBounds(FrameData.boxCol* FrameData.boxWidth+30,13* FrameData.boxWidth+10,FrameData.boxWidth*4,FrameData.boxWidth*6);
        this.add(controlPanel);

        scorePanel.setBounds(FrameData.boxCol* FrameData.boxWidth+30,20* FrameData.boxWidth+10,FrameData.boxWidth*6,FrameData.boxWidth*2);
        this.add(scorePanel);

        this.addKeyListener(myKeyBoardListener);
    }

    /**
     *
     * 方法名：initMenu
     * 作用：初始化主窗口的菜单栏
     */
    private void initMenu() {
        //添加监听
        this.jRadioButton1.setActionCommand("初级");
        this.jRadioButton1.addActionListener(myActionListener);
        this.jRadioButton2.setActionCommand("中级");
        this.jRadioButton2.addActionListener(myActionListener);
        this.jRadioButton3.setActionCommand("高级");
        this.jRadioButton3.addActionListener(myActionListener);
        //加入按钮组
        difficulty.add(this.jRadioButton1);
        difficulty.add(this.jRadioButton2);
        difficulty.add(this.jRadioButton3);

        jRadioButton1.addMouseListener(myMouseListener);
        jRadioButton2.addMouseListener(myMouseListener);
        jRadioButton3.addMouseListener(myMouseListener);


        this.jmenuItem2.add(this.jRadioButton1);
        this.jmenuItem2.add(this.jRadioButton2);
        this.jmenuItem2.add(this.jRadioButton3);

        //开始游戏监听
        jmenuItem1.setActionCommand("开始游戏");
        jmenuItem1.addActionListener(myActionListener);

        //结束游戏监听
        jmenuItem3.setActionCommand("结束游戏");
        jmenuItem3.addActionListener(myActionListener);

        //重置游戏监听
        jmenuItem4.setActionCommand("重置游戏");
        jmenuItem4.addActionListener(myActionListener);

        jmenuItem5.setActionCommand("自定义");
        jmenuItem5.addActionListener(myActionListener);

        jmenuItem6.setActionCommand("帮助");
        jmenuItem6.addActionListener(myActionListener);

        jmenuItem7.setActionCommand("关于作者");
        jmenuItem7.addActionListener(myActionListener);

        jmenuItem8.setActionCommand("音乐开/关");
        jmenuItem8.addActionListener(myActionListener);

        this.menu1.add(this.jmenuItem1);
        this.menu1.add(this.jmenuItem2);
        this.menu1.add(this.jmenuItem4);
        this.menu1.add(this.jmenuItem8);
        this.menu1.add(this.jmenuItem3);
        this.menu2.add(this.jmenuItem5);
        this.menu3.add(this.jmenuItem6);
        this.menu3.add(this.jmenuItem7);
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

    public JRadioButton getjRadioButton1() {
        return jRadioButton1;
    }

    public JRadioButton getjRadioButton2() {
        return jRadioButton2;
    }

    public JRadioButton getjRadioButton3() {
        return jRadioButton3;
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

    public JMenuItem getJmenuItem8() {
        return jmenuItem8;
    }
}
