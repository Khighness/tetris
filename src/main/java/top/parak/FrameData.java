package top.parak;

import top.parak.control.DropThread;
import top.parak.handler.MyHandler;
import top.parak.model.MyFrame;

/**
 * @author KHighness
 * @since 2021-05-01
 * @apiNote 封装窗口、面板、数据
 */
public class FrameData {
    public static final int boxWidth=20;
    public static final int targetBoxWidth=boxWidth/3;
    public static final int smallBoxWidth= boxWidth;
    public static final int boxRow=24;
    public static final int boxCol=16;
    public static boolean isStart=false;//是否开始
    public static boolean isBottom=false;//是否触底
    public static long difficulty=500;//困难值
    public static long onlineDifficulty=400;//在线困难值
    public static Object onlineLock=new Object();//在线锁
    public static int score=0;//得分
    public static int targetScore=0;//对手得分
    public static int blockClass=0;//方块类型01234
    public static int nextBlock=-1;//下一个方块
    public static int direction=0;//上右下左0123
    public volatile static int blockUpNum=0;//困难模式下方块上涨次数
    public volatile static int[] blocksArray={0,1,2,3,4};//选择出现的方块种类
    public static boolean musicOpen=true;//是否开启音乐
    public static boolean blocksUp=false;//是否开启方块上涨
    public static boolean suspend=false;//是否暂停
    public static String musicName="安静.mp3";//音乐名


    public static boolean canMulty=false;//是否进行联机
    public static boolean isAlone=false;//是否是单机模式
    public static boolean findUser=false;//是否找到在线玩家
    public static boolean userReady=false;//玩家准备
    public static boolean targetReady=false;//对方准备
    public static int[][] targetMap=new int[boxRow][boxCol];
    public static int targetBlockDirection=-1;
    public static int targetBlockClass=-1;
    public static int targetBlockX=-1;
    public static int targetBlockY=-1;


    public static final int[][][] L1_blocks = {
            {
                    {1, 1, 0},
                    {0, 1, 0},
                    {0, 1, 0}

            },
            {
                    {0, 0, 0},
                    {0, 0, 1},
                    {1, 1, 1}


            },
            {
                    {1, 0, 0},
                    {1, 0, 0},
                    {1, 1, 0}

            },
            {
                    {0, 0, 0},
                    {1, 1, 1},
                    {1, 0, 0}


            }
    };
    public static final int[][][] L2_blocks = {
            {
                    {1, 1, 0},
                    {1, 0, 0},
                    {1, 0, 0}

            },
            {
                    {0, 0, 0},
                    {1, 1, 1},
                    {0, 0, 1}


            },
            {
                    {0, 1, 0},
                    {0, 1, 0},
                    {1, 1, 0}

            },
            {
                    {0, 0, 0},
                    {1, 0, 0},
                    {1, 1, 1}
            }
    };

    public static final int[][][] T_blocks = {
            {
                    {0, 0, 0},
                    {1, 1, 1},
                    {0, 1, 0}

            },
            {
                    {0, 1, 0},
                    {1, 1, 0},
                    {0, 1, 0}


            },
            {
                    {0, 0, 0},
                    {0, 1, 0},
                    {1, 1, 1}

            },
            {
                    {1, 0, 0},
                    {1, 1, 0},
                    {1, 0, 0}


            }
    };
    public static final int[][][] TIAN_blocks = {
            {
                    {0, 0, 0},
                    {1, 1, 0},
                    {1, 1, 0}

            },
            {
                    {0, 0, 0},
                    {1, 1, 0},
                    {1, 1, 0}
            },
            {
                    {0, 0, 0},
                    {1, 1, 0},
                    {1, 1, 0}
            },
            {
                    {0, 0, 0},
                    {1, 1, 0},
                    {1, 1, 0}
            }
    };
    public static final int[][][] I_blocks = {
            {
                    {1, 0, 0},
                    {1, 0, 0},
                    {1, 0, 0}

            },
            {
                    {0, 0, 0},
                    {0, 0, 0},
                    {1, 1, 1}
            },
            {
                    {1, 0, 0},
                    {1, 0, 0},
                    {1, 0, 0}
            },
            {
                    {0, 0, 0},
                    {0, 0, 0},
                    {1, 1, 1}
            }
    };


    public static MyFrame myFrame=new MyFrame();
    public static MyHandler myHandler=new MyHandler(myFrame);//Netty的handler
    public static DropThread staticDropThread=null;

    public static void setDropThread(DropThread dropThread) {
        staticDropThread=dropThread;
    }

    public static DropThread getDropThread() {
        return staticDropThread;
    }
}
