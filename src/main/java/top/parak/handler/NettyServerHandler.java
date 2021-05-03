package top.parak.handler;

import top.parak.entity.Player;
import top.parak.entity.PlayerGroup;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author KHighness
 * @since 2021-05-01
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    private static List<PlayerGroup> playerGroups = new ArrayList<>();
    private static List<Player> players = new ArrayList<>();


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {


        Channel channel = ctx.channel();

        for (PlayerGroup playerGroup : playerGroups) {
            if (playerGroup.getUser0() == channel) {
                playerGroup.getUser1().writeAndFlush(Unpooled.copiedBuffer("离开游戏", CharsetUtil.UTF_8));
                players.add(new Player(playerGroup.getUser1(), playerGroup.getUser1Name()));
                playerGroups.remove(playerGroup);
                break;
            }
            if (playerGroup.getUser1() == channel) {
                playerGroup.getUser0().writeAndFlush(Unpooled.copiedBuffer("离开游戏", CharsetUtil.UTF_8));
                players.add(new Player(playerGroup.getUser0(), playerGroup.getUser0Name()));
                playerGroups.remove(playerGroup);
                break;
            }
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        System.out.println("待机" + players.size());
        System.out.println("大厅" + playerGroups.size());

        Channel channel = ctx.channel();
        ByteBuf buf = (ByteBuf) msg;
        String message = buf.toString(CharsetUtil.UTF_8);
        if (message.contains("搜索玩家")) {
            String userName = message.split("：")[0];

            //游戏房间和等待大厅有channel不添加进大厅
            int num = 0;
            int num0 = 0;
            for (Player player : players) {
                if (player.getUserName().equals(userName)) {
                    num++;
                    break;
                }
            }
            for (PlayerGroup playerGroup : playerGroups) {
                if (playerGroup.getUser0Name().equals(userName) || playerGroup.getUser1Name().equals(userName)) {
                    num0++;
                    break;
                }
            }

            if (num == 0 && num0 == 0) {
                players.add(new Player(channel, userName));
            }


            //找到玩家
            if (players.size() > 1) {
                if (num == 0) {
                    Player player = players.get(0);
                    PlayerGroup playerGroup = new PlayerGroup(channel, userName, player.getChannel(), player.getUserName());
                    playerGroups.add(playerGroup);
                    players.remove(0);
                    players.remove(players.size() - 1);
                    channel.writeAndFlush(Unpooled.copiedBuffer("找到玩家：" + player.getUserName(), CharsetUtil.UTF_8));
                    player.getChannel().writeAndFlush(Unpooled.copiedBuffer("找到玩家：" + userName, CharsetUtil.UTF_8));
                }
            }
        } else if (message.contains("已准备")) {
            String userName = message.split("：")[0];

            for (PlayerGroup playerGroup : playerGroups) {
                if (playerGroup.getUser0Name().equals(userName)) {
                    playerGroup.getUser1().writeAndFlush(Unpooled.copiedBuffer(message, CharsetUtil.UTF_8));
                    break;
                }
                if (playerGroup.getUser1Name().equals(userName)) {
                    playerGroup.getUser0().writeAndFlush(Unpooled.copiedBuffer(message, CharsetUtil.UTF_8));
                    break;
                }
            }
        } else if (message.contains("取消准备")) {
            String userName = message.split("：")[0];
            for (PlayerGroup playerGroup : playerGroups) {
                if (playerGroup.getUser0Name().equals(userName) || playerGroup.getUser1Name().equals(userName)) {
                    //一方取消准备，游戏房间关闭，等待大厅添加两方
                    players.add(new Player(playerGroup.getUser0(), playerGroup.getUser0Name()));
                    players.add(new Player(playerGroup.getUser1(), playerGroup.getUser1Name()));

                    //通知双方，取消对局
                    playerGroup.getUser0().writeAndFlush(Unpooled.copiedBuffer(message, CharsetUtil.UTF_8));
                    playerGroup.getUser1().writeAndFlush(Unpooled.copiedBuffer(message, CharsetUtil.UTF_8));

                    playerGroups.remove(playerGroup);
                    break;
                }
            }
        } else if (message.contains("当前情况")) {

            String userName = message.split("：")[0];
            for (PlayerGroup playerGroup : playerGroups) {
                if (playerGroup.getUser0Name().equals(userName)) {
                    playerGroup.getUser1().writeAndFlush(Unpooled.copiedBuffer(message, CharsetUtil.UTF_8));
                    System.out.println(playerGroup.getUser1().isActive());
                    break;
                }
                if (playerGroup.getUser1Name().equals(userName)) {
                    playerGroup.getUser0().writeAndFlush(Unpooled.copiedBuffer(message, CharsetUtil.UTF_8));
                    System.out.println(playerGroup.getUser0().isActive());
                    break;
                }
            }
        } else if (message.contains("发起攻击")) {

            String userName = message.split("：")[0];
            for (PlayerGroup playerGroup : playerGroups) {
                if (playerGroup.getUser0Name().equals(userName)) {
                    playerGroup.getUser1().writeAndFlush(Unpooled.copiedBuffer(message, CharsetUtil.UTF_8));
                    break;
                }
                if (playerGroup.getUser1Name().equals(userName)) {
                    playerGroup.getUser0().writeAndFlush(Unpooled.copiedBuffer(message, CharsetUtil.UTF_8));
                    break;
                }
            }

        } else if (message.contains("已经触顶")) {

            String userName = message.split("：")[0];

            for (PlayerGroup playerGroup : playerGroups) {
                if (playerGroup.getUser0Name().equals(userName)) {
                    playerGroup.getUser1().writeAndFlush(Unpooled.copiedBuffer(message, CharsetUtil.UTF_8));

                    players.add(new Player(ctx.channel(), userName));
                    players.add(new Player(playerGroup.getUser1(), playerGroup.getUser1Name()));

                    playerGroups.remove(playerGroup);
                    break;
                }
                if (playerGroup.getUser1Name().equals(userName)) {
                    playerGroup.getUser0().writeAndFlush(Unpooled.copiedBuffer(message, CharsetUtil.UTF_8));

                    players.add(new Player(ctx.channel(), userName));
                    players.add(new Player(playerGroup.getUser0(), playerGroup.getUser0Name()));

                    playerGroups.remove(playerGroup);
                    break;
                }
            }


        } else if (message.contains("再来一局")) {
            String userName = message.split("：")[0];


        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel channel = ctx.channel();
        for (Player player : players) {
            if (player.getChannel() == channel) {
                players.remove(player);
                break;
            }
        }

        for (PlayerGroup playerGroup : playerGroups) {
            if (playerGroup.getUser1() == channel) {
                playerGroup.getUser0().writeAndFlush(Unpooled.copiedBuffer("离开游戏", CharsetUtil.UTF_8));
                players.add(new Player(playerGroup.getUser0(), playerGroup.getUser0Name()));
                playerGroups.remove(playerGroup);
                break;
            }
            if (playerGroup.getUser0() == channel) {
                playerGroup.getUser1().writeAndFlush(Unpooled.copiedBuffer("离开游戏", CharsetUtil.UTF_8));
                players.add(new Player(playerGroup.getUser1(), playerGroup.getUser1Name()));
                playerGroups.remove(playerGroup);
                break;
            }
            break;
        }


        channel.close();
    }
}
