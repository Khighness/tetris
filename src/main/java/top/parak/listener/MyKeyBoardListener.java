package top.parak.listener;

import top.parak.FrameData;
import top.parak.model.Block;
import top.parak.model.Map;
import top.parak.model.MyFrame;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * @author KHighness
 * @since 2021-05-01
 * @apiNote 监听键盘事件
 */
public class MyKeyBoardListener extends KeyAdapter {
    private MyFrame myFrame;

    public MyKeyBoardListener(MyFrame myFrame) {
        this.myFrame = myFrame;
    }

    @Override
    public void keyPressed(KeyEvent e) {

        char keyChar = e.getKeyChar();
        int keyCode = e.getKeyCode();

        if (FrameData.isStart) {
            //暂停(仅允许单机下)
            if (keyCode==32 && FrameData.isAlone){
                if (FrameData.suspend){
                    FrameData.getDropThread().resume();
                    FrameData.suspend=false;
                }else {
                    FrameData.getDropThread().suspend();
                    FrameData.suspend=true;
                }
                return;
            }
            if (FrameData.suspend){return;}

            Block block = myFrame.getMyJPanel().getBlock();
            Map map = myFrame.getMyJPanel().getMap();

            int[][] drawBlock = block.getBlock()[FrameData.direction];

            switch (keyChar) {
                case 'W':
                    //最右边反转
                    if (block.getX() >= FrameData.boxCol - 3) {
                        if ((FrameData.blockClass == 0 || FrameData.blockClass == 1) && (FrameData.direction == 0 || FrameData.direction == 2) && block.getX() == FrameData.boxCol - 2) {
                            if (canSpanInMostRight(map, block)) {
                                block.setX(FrameData.boxCol - 3);
                            } else {
                                return;
                            }
                        } else if (FrameData.blockClass == 2 && (FrameData.direction == 1 || FrameData.direction == 3) && block.getX() == FrameData.boxCol - 2) {
                            if (canSpanInMostRight(map, block)) {
                                block.setX(FrameData.boxCol - 3);
                            } else {
                                return;
                            }
                        } else if (FrameData.blockClass == 4 && (FrameData.direction == 0 || FrameData.direction == 2) && block.getX() == FrameData.boxCol - 1) {
                            if (canSpanInMostRight(map, block)) {
                                block.setX(FrameData.boxCol - 3);
                            } else {
                                return;
                            }
                        } else if (FrameData.blockClass == 4 && (FrameData.direction == 0 || FrameData.direction == 2) && block.getX() == FrameData.boxCol - 2) {
                            if (canSpanInMostRight(map, block)) {
                                block.setX(FrameData.boxCol - 3);
                            } else {
                                return;
                            }
                        }
                    } else {
                        //反转是否触碰右边方块
                        if (!spanTouchRight(map, block)) {
                            return;
                        }
                    }

                    if (FrameData.direction < 3) {
                        FrameData.direction++;
                    } else {
                        FrameData.direction = 0;
                    }
                    break;
                case 'S':
                    if (block.getY() == FrameData.boxRow - 3) {
                        return;
                    }
                    for (int i = block.getY(), ii = 0; i < block.getY() + 3; i++, ii++) {
                        for (int j = block.getX(), jj = 0; j < block.getX() + 3; j++, jj++) {
                            if (drawBlock[ii][jj] == 1 && map.getMap()[i + 1][j] == 1) {
                                return;
                            }
                        }
                    }

                    block.setY(block.getY() + 1);
                    break;
                case 'A':
                    if (block.getX() == 0) {
                        return;
                    }

                    for (int i = block.getY(), ii = 0; i < block.getY() + 3; i++, ii++) {
                        for (int j = block.getX(), jj = 0; j < block.getX() + 3; j++, jj++) {
                            if (drawBlock[ii][jj] == 1 && map.getMap()[i][j - 1] == 1) {
                                return;
                            }
                        }
                    }

                    block.setX(block.getX() - 1);
                    break;
                case 'D':
                    for (int i = 0; i < 3; i++) {
                        if (drawBlock[i][2] == 1 && block.getX() == FrameData.boxCol - 3) {
                            return;
                        }
                    }

                    for (int i = 0; i < 3; i++) {
                        if (drawBlock[i][1] == 1 && block.getX() == FrameData.boxCol - 2) {
                            return;
                        }
                    }

                    for (int i = 0; i < 3; i++) {
                        if (drawBlock[i][0] == 1 && block.getX() == FrameData.boxCol - 1) {
                            return;
                        }
                    }

                    try {
                        for (int i = block.getY(), ii = 0; i < block.getY() + 3; i++, ii++) {
                            for (int j = block.getX(), jj = 0; j < block.getX() + 3; j++, jj++) {
                                if (drawBlock[ii][jj] == 1 && map.getMap()[i][j + 1] == 1) {
                                    return;
                                }
                            }
                        }
                    } catch (Exception e1) {
                    }
                    block.setX(block.getX() + 1);
                    break;
            }
            myFrame.repaint();
            if (!FrameData.isAlone) {
                FrameData.myHandler.sendMapAndBlock(map.getMap(),block);
            }
        }
    }

    /**
     * 方法名：canSpanInMostRight
     * 作用：判断下落方块在旋转时界面上是否有空间
     *
     * @param map 地图类
     * @param block 方块类
     * @return
     */
    private boolean canSpanInMostRight(Map map,Block block){
        for (int i = block.getY(); i < block.getY()+3; i++) {
            if (map.getMap()[i][FrameData.boxCol-3]==1){
                return false;
            }
        }
        return true;
    }

    /**
     * 方法名：spanTouchRight
     * 作用：避免方块旋转时，超出地图边缘
     *
     * @param map 地图类
     * @param block 方块类
     * @return
     */
    private boolean spanTouchRight(Map map,Block block){
        if ((FrameData.blockClass==0 || FrameData.blockClass==1) && (FrameData.direction==0 || FrameData.direction==2)){
            for (int i = block.getY(); i < block.getY()+3; i++) {
                if (map.getMap()[i][block.getX()+2]==1){
                    return false;
                }
            }
            return true;
        }else if (FrameData.blockClass==2 && (FrameData.direction==1 || FrameData.direction==3)){
            for (int i = block.getY(); i < block.getY()+3; i++) {
                if (map.getMap()[i][block.getX()+2]==1){
                    return false;
                }
            }
            return true;
        }else if (FrameData.blockClass==4  && (FrameData.direction==0 || FrameData.direction==2)){
            for (int i = block.getY(); i < block.getY()+3; i++) {
                if (map.getMap()[i][block.getX()+1]==1 || map.getMap()[i][block.getX()+2]==1 ){
                    return false;
                }
            }
            return true;
        }
        return true;
    }
}

