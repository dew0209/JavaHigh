package thread.base.example;

import org.junit.Test;

public class WindwosTest1 {
    @Test
    public void test01(){
        Window1 t1 = new Window1();
        Thread h1 = new Thread(t1);
        h1.setName("窗口1");
        Thread h2 = new Thread(t1);
        h2.setName("窗口2");
        Thread h3 = new Thread(t1);
        h3.setName("窗口3");
        h1.start();
        h2.start();
        h3.start();
    }
}

class Window1 implements Runnable{
    private int ticket = 100;
    @Override
    public void run() {
        while (true){
            if (ticket > 0){
                System.out.println(Thread.currentThread().getName() + "--->" + ticket);
                ticket--;
            }else break;
        }
    }
}