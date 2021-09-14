package thread.base.syn;

import org.junit.Test;

/**
 * 使用同步方法继承Thread类的方式中的线程安全问题
 */
public class WindowTest3 {
    @Test
    public void test01() throws InterruptedException {
        Window3 w1 = new Window3();
        Window3 w2 = new Window3();
        Window3 w3 = new Window3();
        w1.setName("窗口1");
        w2.setName("窗口2");
        w3.setName("窗口3");
        w1.start();
        w2.start();
        w3.start();
        w1.join();
        w2.join();
        w3.join();
    }
}

class Window3 extends Thread{
    private static int ticket = 100;

    @Override
    public void run() {
        while (true) {
            show();
        }

    }
    private static synchronized void show(){//同步监视器：Window3.class
        //private synchronized void show(){ //同步监视器：t1,t2,t3。此种解决方式是错误的
        if (ticket > 0) {

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() + "：卖票，票号为：" + ticket);
            ticket--;
        }
    }
}
