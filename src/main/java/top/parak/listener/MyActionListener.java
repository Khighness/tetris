package top.parak.listener;

import top.parak.FrameData;
import top.parak.util.MusicUtil;
import top.parak.model.Block;
import top.parak.model.CustomPanel;
import top.parak.model.MyFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author KHighness
 * @since 2021-05-01
 * @apiNote 监听窗口事件
 */
public class MyActionListener implements ActionListener {
    private MyFrame myFrame;
    private CustomPanel customPanel;
    private JDialog dialog;

    public MyActionListener() {
    }

    public MyActionListener(MyFrame myFrame) {
        this.myFrame = myFrame;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        Block block = this.myFrame.getMyJPanel().getBlock();
        int[][] map = this.myFrame.getMyJPanel().getMap().getMap();

        switch (actionCommand) {

            case "初级":
                if (!FrameData.isAlone) {
                    JOptionPane.showMessageDialog(null, "联机模式下该操作不被允许", "警告", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                FrameData.difficulty = 500;
                if (myFrame.getDifficulty().getSelection().getActionCommand().equals("初级")) {
                    FrameData.getDropThread().resume();
                }
                break;
            case "中级":
                if (!FrameData.isAlone) {
                    JOptionPane.showMessageDialog(null, "联机模式下该操作不被允许", "警告", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                FrameData.difficulty = 300;
                if (myFrame.getDifficulty().getSelection().getActionCommand().equals("初级")) {
                    FrameData.getDropThread().resume();
                }
                break;
            case "高级":
                if (!FrameData.isAlone) {
                    JOptionPane.showMessageDialog(null, "联机模式下该操作不被允许", "警告", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                FrameData.difficulty = 100;
                FrameData.blocksUp = true;
                if (myFrame.getDifficulty().getSelection().getActionCommand().equals("初级")) {
                    FrameData.getDropThread().resume();
                }

                break;
            case "开始游戏":
                if (FrameData.isAlone) {
                    FrameData.isStart = true;
                    block.randomBlock();
                }
                break;
            case "结束游戏":
                if (FrameData.isAlone) {
                    int num = JOptionPane.showConfirmDialog(null, "您要退出吗", "结束游戏", JOptionPane.YES_NO_OPTION);
                    if (num == 0) {
                        System.exit(0);
                    }
                }
                break;
            case "重置游戏":
                if (FrameData.isAlone) {
                    if (!FrameData.isStart) {
                        JOptionPane.showMessageDialog(null, "请先开始游戏", "警告", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    for (int i = 0; i < map.length; i++) {
                        for (int j = 0; j < map[i].length; j++) {
                            map[i][j] = 0;
                        }
                    }
                    block.randomBlock();
                    block.setX((FrameData.boxCol - 3) / 2);
                    block.setY(0);

                    FrameData.score = 0;
                    FrameData.targetScore = 0;

                    this.myFrame.repaint();
                } else {
                    JOptionPane.showMessageDialog(null, "联机模式下该操作不被允许", "警告", JOptionPane.WARNING_MESSAGE);
                }
                break;
            case "自定义":
                if (!FrameData.isAlone) {
                    JOptionPane.showMessageDialog(null, "联机模式下该操作不被允许", "警告", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                customPanel = new CustomPanel(this);

                if (dialog == null) {
                    dialog = new JDialog();
                }
                dialog.setSize(customPanel.getWidth(), customPanel.getHeight());

                dialog.setLocationRelativeTo(null);

                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setTitle("自定义设置");
                dialog.setResizable(false);

                dialog.setVisible(true);

                dialog.add(customPanel);

                break;
            case "帮助":

                String help = "如何操作？\n" +
                        "单机模式/联机模式操作见面板，但是联机模式仅能上下左右操作\n\n" +
                        "如何进入联机模式？\n" +
                        "确认联机-->搜索玩家-->确认准备-->开始游戏\n\n";
                JOptionPane.showMessageDialog(null, help, "帮助说明", JOptionPane.INFORMATION_MESSAGE);

                break;
            case "关于作者":
                String about =
                        "作者：KHighness\n" +
                        "博客：https://www.parak.top\n" +
                        "版本 1.0";

                JOptionPane.showMessageDialog(null, about, "关于作者", JOptionPane.INFORMATION_MESSAGE);

                break;
            case "音乐开/关":
                if (FrameData.musicOpen) {
                    FrameData.musicOpen = false;
                    MusicUtil.player.close();
                    MusicUtil.palying =false;
                    myFrame.getMenuItem8().setText("音乐：开");
                } else {
                    FrameData.musicOpen = true;
                    MusicUtil.musicOpen(FrameData.musicName);
                    MusicUtil.palying =true;
                    myFrame.getMenuItem8().setText("音乐：关");
                }
                break;
            case "自定义确定":
                if (customPanel != null) {
                    if (!customPanel.getjCheckBox1().isSelected()) {
                        FrameData.blocksArray[0] = -1;
                    } else {
                        FrameData.blocksArray[0] = 0;
                    }
                    if (!customPanel.getjCheckBox2().isSelected()) {
                        FrameData.blocksArray[1] = -1;
                    } else {
                        FrameData.blocksArray[1] = 1;
                    }
                    if (!customPanel.getjCheckBox3().isSelected()) {
                        FrameData.blocksArray[2] = -1;
                    } else {
                        FrameData.blocksArray[2] = 2;
                    }
                    if (!customPanel.getjCheckBox4().isSelected()) {
                        FrameData.blocksArray[3] = -1;
                    } else {
                        FrameData.blocksArray[3] = 3;
                    }
                    if (!customPanel.getjCheckBox5().isSelected()) {
                        FrameData.blocksArray[4] = -1;
                    } else {
                        FrameData.blocksArray[4] = 4;
                    }

                    if (!customPanel.getjCheckBox6().isSelected()) {
                        FrameData.musicOpen = false;
                        MusicUtil.player.close();
                        MusicUtil.palying =false;
                    } else {
                        FrameData.musicOpen = true;
                        System.out.println(MusicUtil.palying);
                        if (!MusicUtil.palying){
                            MusicUtil.musicOpen(FrameData.musicName);
                        }
                    }
                    if (!customPanel.getjCheckBox7().isSelected()) {
                        FrameData.blocksUp = false;
                    } else {
                        FrameData.blocksUp = true;
                    }
                }
                dialog.dispose();
                break;
            case "自定义取消":
                dialog.dispose();
                break;
        }
    }
}
