package top.parak.control;

import top.parak.FrameData;
import top.parak.util.MusicUtil;
import top.parak.handler.MyHandler;
import top.parak.model.Block;
import top.parak.model.Map;
import top.parak.model.MyFrame;
import top.parak.model.MyJPanel;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import javax.swing.*;
import java.util.Random;

/**
 * @author KHighness
 * @since 2021-05-01
 * @apiNote 控制方块下落
 */
public class DropThread extends Thread {

    @Override
    public void run() {
        super.run();
        //播放音乐
        MusicUtil.musicOpen(FrameData.musicName);
        MusicUtil.palying =true;

        //连接Netty
        MyHandler myHandler = FrameData.myHandler;
        createClient(myHandler);

        if (FrameData.getDropThread() == null) {
            FrameData.setDropThread(this);
        }
        MyFrame myFrame = FrameData.myFrame;
        JMenuBar menubar = myFrame.getMenubar();
        MyJPanel myJPanel = myFrame.getMyJPanel();
        Map map = myJPanel.getMap();
        Block block = myJPanel.getBlock();
        Random random = new Random();
        int col = 0;

        //询问是否联机
        int canMulti = JOptionPane.showConfirmDialog(null, "您是否要进行联机对战？", "是否联机", JOptionPane.YES_NO_OPTION);
        if (canMulti == 0) {
            FrameData.canMulty = true;
        }

        //判断是否能联机
        Channel channel = null;
        String userName = "";
        String targetName = "";
        if (FrameData.canMulty) {
            //是否能连上服务器
            searchUser(myHandler, userName, targetName, channel, block);
        } else {
            FrameData.isAlone = true;
            block.randomBlock();
        }

        while (true) {
            try {
                if (FrameData.isAlone){
                    Thread.sleep(FrameData.difficulty);
                }else {
                    synchronized (FrameData.onlineLock) {
                        Thread.sleep(FrameData.onlineDifficulty);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (FrameData.isStart) {
                if (FrameData.isBottom) {
                    int[][] drawBlock = block.getBlock()[FrameData.direction];
                    for (int i = block.getY(), ii = 0; i < block.getY() + 3; i++, ii++) {
                        for (int j = block.getX(), jj = 0; j < block.getX() + 3; j++, jj++) {
                            if (drawBlock[ii][jj] == 1) {
                                map.getMap()[i][j] = 1;
                            }
                        }
                    }

                    //高级模式方块上涨
                    if (FrameData.blocksUp) {
                        col = random.nextInt(FrameData.boxCol);
                        int[][] map1 = map.getMap();

                        for (int i = 1; i < FrameData.boxRow; i++) {
                            for (int j = 0; j < FrameData.boxCol; j++) {
                                if (map1[i][j] == 1) {
                                    map1[i - 1][j] = 1;
                                    map1[i][j] = 0;
                                }
                            }
                        }

                        for (int j = 0; j < FrameData.boxCol; j++) {
                            if (j != col) {
                                map1[FrameData.boxRow - 1][j] = 1;
                            }
                        }
                    }

                    //是否触顶
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < FrameData.boxCol; j++) {
                            if (map.getMap()[i][j] == 1) {
                                if (!FrameData.isAlone) {
                                    //联机模式下触顶
                                    myHandler.sendDefeat();
                                }
                                //单机模式下触顶
                                int num = JOptionPane.showConfirmDialog(null, "您要重来一局吗", "游戏结束", JOptionPane.YES_NO_OPTION);
                                if (num != 0) {
                                    System.exit(0);
                                } else {
                                    if (!FrameData.isAlone) {
                                        //联机下再来一局
                                        FrameData.isAlone = true;
                                        FrameData.findUser = false;
                                        FrameData.userReady = false;
                                        FrameData.targetReady = false;
                                        FrameData.isStart = false;
                                        FrameData.targetScore = 0;
//                                        searchUser(myHandler, userName, targetName, channel, block);
                                    }
                                    block.randomBlock();
                                    block.setY(0);
                                    block.setX((FrameData.boxCol - 3) / 2);
                                    for (int ii = 0; ii < map.getMap().length; ii++) {
                                        for (int jj = 0; jj < map.getMap()[ii].length; jj++) {
                                            map.getMap()[ii][jj] = 0;
                                        }
                                    }
                                    FrameData.score = 0;
                                    FrameData.targetScore = 0;
                                    myFrame.repaint();
                                }
                            }
                        }
                    }


                    //判断是否消行
                    int sum = 0;
                    for (int i = 0; i < FrameData.boxRow; i++) {
                        for (int j = 0; j < FrameData.boxCol; j++) {
                            sum += map.getMap()[i][j];
                        }

                        if (sum == FrameData.boxCol) {

                            FrameData.score++;

                            int[][] temp = new int[FrameData.boxRow][FrameData.boxCol];
                            for (int ii = 0; ii < i; ii++) {
                                for (int j = 0; j < FrameData.boxCol; j++) {
                                    temp[ii][j] = map.getMap()[ii][j];
                                    map.getMap()[ii][j] = 0;

                                    if (!FrameData.isAlone) {
                                        //联机模式进行攻击
                                        myHandler.sendAttack();
                                    }
                                }
                            }
                            for (int j = 0; j < i; j++) {
                                for (int k = 0; k < FrameData.boxCol; k++) {
                                    map.getMap()[j + 1][k] = temp[j][k];
                                }
                            }
                        }

                        sum = 0;
                    }


                    block.setY(0);
                    block.setX((FrameData.boxCol - 3) / 2);
                    block.randomBlock();

                    FrameData.isBottom = false;

                    if (!FrameData.isAlone) {
                        FrameData.myHandler.sendMapAndBlock(map.getMap(), block);
                    }

                } else {

                    //是否已经触底了
                    if (block.getY() == FrameData.boxRow - 3) {
                        FrameData.isBottom = true;
                        continue;
                    }
                    //判断能否下落
                    int[][] drawBlock = block.getBlock()[FrameData.direction];
                    for (int i = block.getY(), ii = 0; i < block.getY() + 3; i++, ii++) {
                        for (int j = block.getX(), jj = 0; j < block.getX() + 3; j++, jj++) {
                            if (drawBlock[ii][jj] == 1 && map.getMap()[i + 1][j] == 1) {
                                FrameData.isBottom = true;
                                break;
                            }
                        }
                    }
                    if (FrameData.isBottom) {
                        continue;
                    }
                    block.setY(block.getY() + 1);

                }

                myFrame.repaint();
                if (!FrameData.isAlone) {
                    FrameData.myHandler.sendMapAndBlock(map.getMap(), block);
                }
            }
        }
    }


    /**
     * 方法名：searchUser
     * 作用：搜索玩家
     *
     * @param myHandler Netty的客户端handler，封装了一个channel上下文环境
     * @param userName 当前玩家用户名，是自动生成的一个uuid
     * @param targetName 对方玩家的名字
     * @param channel Netty通道
     * @param block 当前下落的方块
     */
    private void searchUser(MyHandler myHandler, String userName, String targetName, Channel channel, Block block) {
        ChannelHandlerContext ctx = myHandler.getCtx();

        int connectionNum = 0;//连接超时2s
        while (ctx == null && connectionNum <= 100) {
            ctx = myHandler.getCtx();
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            connectionNum++;
        }

        if (ctx != null) {
            int num = JOptionPane.showConfirmDialog(null, "连接服务器成功，确定搜索玩家？（超时时间20s）", "搜索玩家", JOptionPane.YES_NO_OPTION);
            if (num != 0) {
                FrameData.isAlone = true;
                block.randomBlock();
                return;
            }

            //联机模式
            for (int i = 0; i < 1000; i++) {
                if (FrameData.findUser) {
                    break;
                }
                try {
                    Thread.sleep(20);
                    myHandler.sendFindUser();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (FrameData.isAlone) {
                int num0 = JOptionPane.showConfirmDialog(null, "未找到有玩家在线，是否单机游玩", "没有玩家在线", JOptionPane.YES_NO_OPTION);
                if (num0 != 0) {
                    System.exit(0);
                }
                FrameData.isStart = true;
                FrameData.isAlone = true;
                block.randomBlock();
            }

        } else {
            JOptionPane.showConfirmDialog(null, "远程服务器未启动，将以单机模式进行", "单机模式", JOptionPane.CLOSED_OPTION);
            FrameData.isStart = true;
            FrameData.isAlone = true;
            block.randomBlock();
        }

    }

    /**
     * 方法名：createClient
     * 作用：尝试连接Netty服务器，并创建Netty的channel上下文环境
     *
     * @param myHandler Netty的客户端handler，封装了一个channel上下文环境
     */
    private void createClient(MyHandler myHandler) {

        //客户端需要一个事件循环组
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            //创建客户端启动对象
            //注意客户端使用的不是 ServerBootstrap 而是 Bootstrap
            Bootstrap bootstrap = new Bootstrap();

            //设置相关参数
            bootstrap.group(group) //设置线程组
                    .channel(NioSocketChannel.class) // 设置客户端通道的实现类(反射)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(myHandler); //加入自己的处理器
                        }
                    });

            //启动客户端去连接服务器端
            //关于 ChannelFuture 要分析，涉及到netty的异步模型
//            ChannelFuture channelFuture = bootstrap.connect("159.75.26.246", 6668).sync();
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 6668).sync();
            //给关闭通道进行监听,没有这句话，channel关闭不会监听到，
            // 且该处不会阻塞，会直接执行到finally，所以finally需要直接置空
//            channelFuture.channel().closeFuture().sync();

        } catch (Exception e) {

        } finally {
//            group.shutdownGracefully();
        }


    }
}
