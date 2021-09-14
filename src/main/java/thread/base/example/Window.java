package thread.base.example;

import org.junit.Test;

/**
 * 创建三个窗口卖票
 *  存在线程安全，下一章解决
 */
class WindowTest extends Thread{
    private static int ticket = 100;
    @Override
    public void run() {
        while (true){
            if(ticket > 0){
                System.out.println(Thread.currentThread().getName() + "--->" + ticket);
                ticket--;
            }else break;
        }
    }
}

public class Window{
    @Test
    public void test01(){
        WindowTest t1 = new WindowTest();
        WindowTest t2 = new WindowTest();
        WindowTest t3 = new WindowTest();
        t1.setName("窗口1");
        t2.setName("窗口2");
        t3.setName("窗口3");
        t1.start();
        t2.start();
        t3.start();
    }
}