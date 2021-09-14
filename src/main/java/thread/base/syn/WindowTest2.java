package thread.base.syn;

import org.junit.Test;
import thread.base.example.Window;


/**
 * 使用同步代码块解决继承Thread类的方式的线程安全问题
 */
public class WindowTest2 {
    @Test
    public void test01() throws InterruptedException {
        Window2 w1 = new Window2();
        Window2 w2 = new Window2();
        Window2 w3 = new Window2();
        w1.setName("窗口1");
        w2.setName("窗口2");
        w3.setName("窗口3");
        w1.start();
        w2.start();
        w3.start();
        w2.join();
        w1.join();
        w3.join();
    }
}

class Window2 extends Thread{
    private static int ticket = 100;
    @Override
    public void run() {
        while (true){
            synchronized (Window2.class){
                try {
                    sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (ticket > 0){
                    System.out.println(Thread.currentThread().getName() + "--->" + ticket);
                    ticket--;
                }else break;
            }
        }
    }
}