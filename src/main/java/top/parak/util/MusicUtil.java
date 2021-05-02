package top.parak.util;

import javazoom.jl.player.Player;

import java.io.*;
import java.net.URL;

/**
 * @author KHighness
 * @since 2021-05-01
 * @apiNote 播放音乐工具类
 */
public class MusicUtil {
    public static Player player = null;
    public volatile static boolean isplayed = false;

    /**
     * 方法名：musicOpen
     * 作用：打开音乐
     *
     * @param fileName 音乐文件的名字
     */
    public static void musicOpen(String fileName) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL music = MusicUtil.class.getClassLoader().getResource("music");

                    File file = new File(music.getFile()+"/"+fileName);
                    FileInputStream fileInputStream = new FileInputStream(file);
                    //创建一个缓冲流
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
                    player = new Player(new FileInputStream(file));
                    player.play();
                    isplayed=true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

}
}
