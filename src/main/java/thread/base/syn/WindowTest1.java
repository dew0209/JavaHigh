package thread.base.syn;

import org.junit.Test;

/**
 * 在Java中，我们通过同步机制，来解决线程的安全问题
 *  方式一：同步代码块
 *      synchronized(同步监视器){
 *          //需要被同步的代码
 *      }
 *      说明：
 *          1.操作共享数据的代码，即为需要被同步的代码
 *          2.共享数据：多个线程共享操作的变量
 *          3.同步监视器，俗称：锁。任何一个类的对象，都可以充当锁
 *              要求：多个线程必须要共用同一把锁
 *          补充：在实现Runnable接口创建多线的方式，我们可以考虑使用this同步监视器
 *  方式二：同步方法
 *      如果操作的共享数据的代码完整的声明在一个方法中，我们不妨将此方法声明为同步的
 *
 *  同步的方式，解决了线程的安全问题
 *      操作同步代码时，只能有一个线程参与，其它线程等待。相当于是一个单线的过程，效率低。
 *
 */
public class WindowTest1 {
    @Test
    public void test01() throws InterruptedException {
        Window1 w1 = new Window1();
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

class Window1 implements Runnable{
    private int ticket = 100;
    @Override
    public void run() {
        while (true){
            synchronized (this){
                if (ticket > 0){
                    try {
                        Thread.currentThread().sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "--->" + ticket);
                    ticket--;
                }else break;
            }
        }
    }
}