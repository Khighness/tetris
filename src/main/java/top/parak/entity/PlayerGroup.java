package top.parak.entity;

import io.netty.channel.Channel;

/**
 * @author KHighness
 * @since 2021-05-01
 * @apiNote 封装对战的一组玩家
 */
public class PlayerGroup {
    private Channel user0;
    private String user0Name;
    private Channel user1;
    private String user1Name;

    public PlayerGroup() {
    }

    public PlayerGroup(Channel user0, String user0Name, Channel user1, String user1Name) {
        this.user0 = user0;
        this.user0Name = user0Name;
        this.user1 = user1;
        this.user1Name = user1Name;
    }

    public Channel getUser0() {
        return user0;
    }

    public void setUser0(Channel user0) {
        this.user0 = user0;
    }

    public String getUser0Name() {
        return user0Name;
    }

    public void setUser0Name(String user0Name) {
        this.user0Name = user0Name;
    }

    public Channel getUser1() {
        return user1;
    }

    public void setUser1(Channel user1) {
        this.user1 = user1;
    }

    public String getUser1Name() {
        return user1Name;
    }

    public void setUser1Name(String user1Name) {
        this.user1Name = user1Name;
    }
}
