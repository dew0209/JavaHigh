package thread.base.syn;

import org.junit.Test;

public class WindowTest4 {
    @Test
    public void test01() throws InterruptedException {
        Window4 win = new Window4();
        Thread t1 = new Thread(win);
        Thread t2 = new Thread(win);
        Thread t3 = new Thread(win);
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

class Window4 implements Runnable{

    private int ticket = 100;

    @Override
    public void run() {
        while (true) {

            show();
        }
    }

    private  synchronized void show(){//同步监视器：this
        //synchronized (this){

        if (ticket > 0) {

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() + ":卖票，票号为：" + ticket);

            ticket--;
        }
        //}
    }
}