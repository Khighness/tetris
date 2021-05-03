package top.parak.model;

import top.parak.FrameData;

import java.awt.*;
import java.util.Random;

/**
 * @author KHighness
 * @since 2021-05-01
 * @apiNote 方块类
 */
public class Block  {
    private int x;
    private int y;
    private int[][][] block;
    private static final Random random = new Random();

    public Block(int x, int y) {
        this.x = x;
        this.y = y;
        if (FrameData.isStart) {
            randomBlock();
        }
    }

    public void drawMap(Graphics g) {
        if (FrameData.isStart) {
            g.setColor(Color.pink);
            int[][] drawBlok = block[FrameData.direction];
            for (int i = 0; i < drawBlok.length; i++) {
                for (int j = 0; j < drawBlok[i].length; j++) {
                    if (drawBlok[i][j] == 1) {
                        g.fillRect((j + x) * FrameData.boxWidth, (i + y) * FrameData.boxWidth, FrameData.boxWidth, FrameData.boxWidth);
                    }
                }
            }
            g.setColor(Color.black);
        }
    }

    /**
     * 方法名：randomBlock
     * 作用：随机生成新的方块类型
     */
    public void randomBlock(){
        int index0=-1;
        int index1=-1;

        if (FrameData.nextBlock==-1){
            while (true){
                index0=random.nextInt(5);
                if (FrameData.blocksArray[index0]!=-1){
                    FrameData.nextBlock=FrameData.blocksArray[index0];
                    break;
                }
            }
            while (true){
                index1=random.nextInt(5);
                if (FrameData.blocksArray[index1]!=-1){
                    FrameData.blockClass=FrameData.blocksArray[index1];
                    break;
                }
            }
        }else {
            FrameData.blockClass= FrameData.nextBlock;
            while (true){
                index0=random.nextInt(5);
                if (FrameData.blocksArray[index0]!=-1){
                    FrameData.nextBlock=FrameData.blocksArray[index0];
                    break;
                }
            }
        }
        switch (FrameData.blockClass){
            case 0:
                this.block = FrameData.L1_blocks;
                break;
            case 1:
                this.block = FrameData.L2_blocks;
                break;
            case 2:
                this.block = FrameData.T_blocks;
                break;
            case 3:
                this.block = FrameData.TIAN_blocks;
                break;
            case 4:
                this.block = FrameData.I_blocks;
                break;
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int[][][] getBlock() {
        return block;
    }

    public void setBlock(int[][][] block) {
        this.block = block;
    }
}
