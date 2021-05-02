package top.parak.handler;

import top.parak.FrameData;
import top.parak.util.JsonUtil;
import top.parak.model.Block;
import top.parak.model.MyFrame;
import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import javax.swing.*;
import java.util.List;
import java.util.UUID;

/**
 * 类名：MyHandler
 * 作用：
 * 作者：陈铭
 * 日期：2021.02.23
 */

/**
 * @author KHighness
 * @since 2021-05-01
 * @apiNote 解析服务器端发来的数据
 */
public class MyHandler extends ChannelInboundHandlerAdapter {
    private  String userName=UUID.randomUUID().toString();
    private  String taegetName="";
    private volatile ChannelHandlerContext ctx=null;
    private MyFrame myFrame=null;

    public MyHandler(MyFrame myFrame) {
        this.myFrame=myFrame;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(userName);
        this.ctx=ctx;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        String message = buf.toString(CharsetUtil.UTF_8);
        if (message.contains("找到玩家：")){
            taegetName=message.substring(4);
            FrameData.isAlone=false;
            FrameData.findUser=true;
            int num = JOptionPane.showConfirmDialog(null, "是否准备...", "找到玩家", JOptionPane.YES_NO_OPTION);
            if (num==0){
               //准备
                for (int i = 0; i < 50; i++) {

                    Thread.sleep(20);
                    ctx.channel().writeAndFlush(Unpooled.copiedBuffer(userName + "：已准备", CharsetUtil.UTF_8));
                }

                FrameData.userReady=true;
                if (FrameData.targetReady){
                    FrameData.isStart=true;
                    myFrame.getMyJPanel().getBlock().randomBlock();
                }
            }else {
                ctx.channel().writeAndFlush(Unpooled.copiedBuffer(userName +"：取消准备", CharsetUtil.UTF_8));
            }
        }else if (message.contains("已准备")){

            FrameData.targetReady=true;

            if (FrameData.userReady){
                FrameData.isStart=true;
                myFrame.getMyJPanel().getBlock().randomBlock();
            }

        }else if (message.contains("取消准备")){
            //对方不准备，只能重新搜索玩家
            String userName0 = message.split("：")[0];
            if (userName0.equals(userName)){
                //自己取消的,直接进行单机游戏。但仍然可以再次进行搜索玩家，连接还在

                FrameData.isAlone=true;
                FrameData.findUser=false;
                FrameData.targetReady=false;
                FrameData.userReady=false;

                FrameData.isStart=true;
                myFrame.getMyJPanel().getBlock().randomBlock();

            }else {
                //对方取消的
                int num = JOptionPane.showConfirmDialog(null, "对方取消准备,进行单机游戏...", "取消准备", JOptionPane.CLOSED_OPTION);
                FrameData.isAlone=true;
                FrameData.canMulty=false;
                FrameData.findUser=false;
                FrameData.targetReady=false;
                FrameData.userReady=false;
                FrameData.isStart=true;
                myFrame.getMyJPanel().getBlock().randomBlock();
            }
        }else if (message.contains("当前情况")){

            if (!message.substring(message.length()-1).equals("@")){
                return;
            }


            //获取对方地图
            String mapString = message.split("：")[2];
            String direction = message.split("：")[3];
            String blockClass = message.split("：")[4];
            FrameData.targetBlockX = Integer.parseInt(message.split("：")[5]);
            FrameData.targetBlockY = Integer.parseInt(message.split("：")[6]);


            List<String> strings = JSON.parseArray(mapString, String.class);
            int m=0;
            int n=0;
            int[][] ints = new int[FrameData.boxRow][FrameData.boxCol];
            for (String string : strings) {
                for (String s : string.split(",")) {
                    ints[m][n]=Integer.parseInt(s);
                    n++;
                }
                n=0;
                m++;
            }


            for (int i = 0; i <FrameData.boxRow ; i++) {
                for (int j = 0; j < FrameData.boxCol ; j++) {
                    FrameData.targetMap[i][j]=0;
                    if(ints[i][j]==1) {
                        FrameData.targetMap[i][j]=1;
                    }
                }
            }
            //获取对方方块
            FrameData.targetBlockDirection=Integer.parseInt(direction);
            FrameData.targetBlockClass=Integer.parseInt(blockClass);

            myFrame.repaint();

        }else if (message.contains("发起攻击")){
            synchronized (FrameData.onlineLock) {
                FrameData.onlineDifficulty -= 40;
                FrameData.targetScore++;
            }
        }else if (message.contains("已经触顶")){
            int num = JOptionPane.showConfirmDialog(null, "对方已经失败，您还继续吗？（是：进行单机游戏，否：离开游戏）", "对局结束", JOptionPane.YES_NO_OPTION);
            if (num==0){
                //单机
                FrameData.isAlone=true;
                FrameData.findUser=false;
                FrameData.targetReady=false;
                FrameData.userReady=false;
                FrameData.canMulty=false;
                FrameData.isStart=true;
                FrameData.score=0;
                FrameData.targetScore=0;
                myFrame.getMyJPanel().getBlock().randomBlock();
                for (int i = 0; i < FrameData.boxRow; i++) {
                    for (int j = 0; j < FrameData.boxCol; j++) {
                        myFrame.getMyJPanel().getMap().getMap()[i][j]=0;
                    }
                }
            }else if (num==1){
                //离开
                System.exit(0);
            }

        }else if (message.contains("离开游戏")){
            int num = JOptionPane.showConfirmDialog(null, "对方已经失败，您还继续吗？（是：进行单机游戏，否：离开游戏）", "对局结束", JOptionPane.YES_NO_OPTION);
            if (num==0){
                //单机
                FrameData.isAlone=true;
                FrameData.findUser=false;
                FrameData.targetReady=false;
                FrameData.userReady=false;
                FrameData.canMulty=false;
                FrameData.isStart=true;
                FrameData.score=0;
                FrameData.targetScore=0;
                myFrame.getMyJPanel().getBlock().randomBlock();
                for (int i = 0; i < FrameData.boxRow; i++) {
                    for (int j = 0; j < FrameData.boxCol; j++) {
                        myFrame.getMyJPanel().getMap().getMap()[i][j]=0;
                    }
                }
            }else if (num==1){
                //离开
                System.exit(0);
            }
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().writeAndFlush(Unpooled.copiedBuffer(userName +"：离开游戏", CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.channel().writeAndFlush(Unpooled.copiedBuffer(userName +"：离开游戏", CharsetUtil.UTF_8));
    }

    public String getUserName() {
        return userName;
    }

    public String getTaegetName() {
        return taegetName;
    }

    public ChannelHandlerContext getCtx() {
        return ctx;
    }


    /**
     * 作用：供DropThread线程类根据业务发送数据
     *
     *  下同
     */
    public void sendFindUser(){
        ctx.channel().writeAndFlush(Unpooled.copiedBuffer(userName + "：搜索玩家", CharsetUtil.UTF_8));
        ctx.channel().flush();
    }
    public void sendMapAndBlock(int[][] map,Block block){
        String map1 = JsonUtil.getMap(map);
        ctx.channel().writeAndFlush(Unpooled.copiedBuffer(userName + "：当前情况："+map1+"："+FrameData.direction+"："+FrameData.blockClass+"："+block.getX()+"："+block.getY()+"：@", CharsetUtil.UTF_8));
        ctx.channel().flush();
    }
    public void sendDefeat(){
        ctx.channel().writeAndFlush(Unpooled.copiedBuffer(userName + "：已经触顶", CharsetUtil.UTF_8));
        ctx.channel().flush();
    }
    public void sendAttack(){
        ctx.channel().writeAndFlush(Unpooled.copiedBuffer(userName + "：发起攻击", CharsetUtil.UTF_8));
        ctx.channel().flush();
    }
    public void sendAgain(){
        ctx.channel().writeAndFlush(Unpooled.copiedBuffer(userName + "：再来一局", CharsetUtil.UTF_8));
        ctx.channel().flush();
    }
}
