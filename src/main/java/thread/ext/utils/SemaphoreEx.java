package thread.ext.utils;

import java.util.concurrent.Semaphore;

/**
 * 控制同时访问某个特定资源的线程数量
 *  public Semaphore(int permits):许可证的数量
 */
public class SemaphoreEx {
    public static void main(String[] args) {
        new Semaphore(10);
    }
}
