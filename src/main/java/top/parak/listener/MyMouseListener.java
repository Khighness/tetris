package top.parak.listener;

import top.parak.FrameData;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author KHighness
 * @since 2021-05-01
 * @apiNote 监听鼠标事件
 */
public class MyMouseListener extends MouseAdapter {

    public MyMouseListener() {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (FrameData.isAlone) {
            FrameData.getDropThread().suspend();
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (FrameData.isAlone) {
            FrameData.getDropThread().resume();
        }
    }
}
