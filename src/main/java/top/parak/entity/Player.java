package top.parak.entity;

import io.netty.channel.Channel;

/**
 * @author KHighness
 * @since 2021-05-01
 * @apiNote 封装玩家对象
 */
public class Player {
    private Channel channel;
    private String userName;

    public Player() {
    }

    public Player(Channel channel, String userName) {
        this.channel = channel;
        this.userName = userName;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
