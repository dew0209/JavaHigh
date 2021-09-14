package thread.base.syn;

import org.junit.Test;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 解决线程安全问题的方式三：Lock锁 --jdk5.0新增
 * 1. 面试题：synchronized 与 Lock的异同？
 *     相同：二者都可以解决线程安全问题
 *     不同：synchronized机制在执行完相应的同步代码以后，自动的释放同步监视器
 *          Lock需要手动的启动同步（lock()），同时结束同步也需要手动的实现（unlock()）
 * 2.优先使用顺序：
 *     Lock  同步代码块（已经进入了方法体，分配了相应资源）  同步方法（在方法体之外）
 */
public class LockTest {
    @Test
    public void test01() throws InterruptedException {
        Window w1 = new Window();
        Thread t1 = new Thread(w1);
        Thread t2 = new Thread(w1);
        Thread t3 = new Thread(w1);
        t1.setName("窗口1");
        t2.setName("窗口2");
        t3.setName("窗口3");
        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();
    }
}

class Window implements Runnable{
    private int ticket = 100;
    private ReentrantLock lock = new ReentrantLock();
    @Override
    public void run() {
        while (true){
            try {
                lock.lock();
                if (ticket > 0){
                    Thread.sleep(10);
                    System.out.println(Thread.currentThread().getName() + "：售票，票号为：" + ticket);
                    ticket--;
                }else break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }

    }
}