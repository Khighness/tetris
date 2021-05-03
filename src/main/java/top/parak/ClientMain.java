package top.parak;

import top.parak.control.DropThread;

/**
 * @author KHighness
 * @since 2021-05-01
 * @apiNote 客户端
 */
public class ClientMain {
    public static void main(String[] args) {
        DropThread dropThread = new DropThread();
        dropThread.start();
    }
}

class Rival {
    public static void main(String[] args) {
        DropThread dropThread = new DropThread();
        dropThread.start();
    }
}
