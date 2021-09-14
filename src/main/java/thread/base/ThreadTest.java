package thread.base;

import org.junit.Test;

/**
 * 多线程的创建方式：
 *     方式一：
 *         1.创建一个继承于Thread类的子类
 *         2.重写Thread类的run方法，讲此线程执行的操作声明在run方法中
 *         3.创建Thread类的子类的对象
 *         4.通过此对象调用start方法
 */
public class ThreadTest{

    @Test
    public void test01(){
        //创建Thread类的子类的对象
        MyThread m1 = new MyThread();
        m1.start();
        //以下是错误的演示
        //m1.run();
        //m1.start();//已经启动过一次了。IllegalThreadStateException异常
    }

}


//创建一个继承于Thread类的子类
class MyThread extends Thread{
    //重写Thread类的run方法
    @Override
    public void run() {
        for(int i = 0;i < 100;i++){
            if(i % 2 == 0){
                System.out.println(Thread.currentThread().getName() + "--->" + i);
            }
        }
    }
}