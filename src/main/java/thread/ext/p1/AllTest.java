package thread.ext.p1;

import java.util.concurrent.Callable;

/**
 * 基础概念
 *  cpu核心数和线程数之间的概念
 *      核心数：线程数 = 1:1
 *      使用了超线程技术后-->1:2
 *  cpu时间片轮转机制
 *      又称RR调度，会导致上下文切换，上下文切换需要时间，因为需要记录当前线程的信息，然后将另一个线程装配进来
 *  什么是进程和线程
 *      进程：程序运行资源分配的最小单位，，进程内部有多个线程，会共享这个资源
 *      线程：cpu调度的最下单位，必须依赖进程而存在
 *  并行和并发
 *      并行：同一时刻，可以同时处理事情的能力
 *      并发：与单位时间相关，在单位时间内可以处理事情的能力
 *      例如；食堂有8个打饭窗口，一个学生打饭30s，那么并行8，并发16
 *  高并发变成的意义，好处和注意事项
 *      好处：充分利用cpu资源，加快用户响应时间，程序模块化，异步化
 *      问题：
 *          线程共享资源，存在冲突
 *          容易导致死锁
 *          启用太多的线程，就有搞垮机器的可能
 *
 *  怎么样才能让Java里面的线程安全停止工作呢？
 *      线程自然终止，自然执行抛出异常或抛出未处理异常
 *      stop()--线程不会正确释放资源,resume(),suspend()--这两者搭配容易死锁
 *  Java线程是协作式，而非抢占式
 *      调用一个线程的interrupt():中断一个线程，并不是直接中断，而是和这个线程打个招呼告诉你，线程是否中断，由线程本身决定
 *      isInterrupted()：判定当前线程是否处于中断状态。
 *      static方法interrupted()：判定当前线程是否处于中断状态，同时将中断标志位设为false
 *      注意：方法里如果抛出InterruptedException，线程的中断标志位会被复位成false，如果确实需要中断线程，要求我们自己在catch语句块里面再次调用interrupt()
 *  线程的状态
 *      创建->就绪：start()
 *      就绪->运行：join(),获取执行权
 *      运行->就绪：时间片到期，yield()
 *      运行->阻塞：sleep(),wait()
 *      阻塞->就绪：interrupt(),sleep时间到，notify(),notifyAll()
 *      运行->停止：run()结束，stop(),setDaemon()
 *      创建 就绪 运行 阻塞 停止
 *      yield():释放资源，但是下一轮仍有可能占用cpu
 *      sleep():释放资源，休眠，下一轮不会占用cpu
 *
 *  start()和run()
 *      直接run(),就是相当于一个普通的方法，必须要start()才是新起一个线程
 *
 *  线程的优先级
 *      1~10，默认为5。1的优先级最高，高优先级线程的分配的时间片的数量是大于低优先级线程的。
 *      线程变量.setPriority(5);
 *      了解即可，不同os对于这个处理不一样
 *
 *  守护线程
 *      和主线程同时结束
 *
 *
 *  线程的共享
 *      synchronized内置锁
 *          static 关键字是类锁
 *          非static 是对象锁
 *      volatile关键字，最轻量的同步机制[可见性，不能保证原子性，不是锁机制]
 *          1、保证内存可见性：可见性是指线程之间的可见性，一个线程修改的状态对另一个线程是可见的。
 *          2、禁止指令重排：指令重排序是JVM为了优化指令、提高程序运行效率。
 *          全局，唯一，最新
 *          参考知乎：[https://www.zhihu.com/question/23944806]
 *      ThreadLocal的使用
 *          线程变量，每个线程使用自己的,类似map集合的，键是线程，value是值
 *
 */

public class AllTest {

    private volatile int age = 1;

    public void setAge(){
        age = age + 20;
    }

    //对象锁
    public synchronized void m1(){

    }
    public void m2(){
        synchronized (this){

        }
    }
    //类锁
    public static synchronized void m3(){

    }
    public static void m4(){
        synchronized (AllTest.class){

        }
    }
    private static class ThreadEx extends Thread{
        @Override
        public void run() {
            System.out.println("以继承的方式新建线程");
        }
    }

    private static class ThreadRun implements Runnable{
        @Override
        public void run() {
            System.out.println("以接口实现的方式新启线程");
        }
    }

    private static class ThreadCall implements Callable<String>{
        @Override
        public String call() throws Exception {
            String res = "以callabe的方式实现接口，返回结果值";
            return res;
        }
    }
    static ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>(){
        @Override
        protected Integer initialValue() {
            return 1;
        }
    };
    private static class ThreadLocalTh implements Runnable{
        private int id;

        public ThreadLocalTh(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " start ");
            Integer integer = threadLocal.get();
            integer += id;
            threadLocal.set(integer);
            System.out.println(threadLocal.get() + "            debug");
            //threadLocal.remove();//清除这个变量
            System.out.println(Thread.currentThread().getName() + " end ");
        }
    }
    public static void main(String[] args) throws Exception{
        /*//新启线程的三种方式
        new ThreadEx().start();
        new Thread(new ThreadRun()).start();
        FutureTask<String> fu = new FutureTask<>(new ThreadCall());
        new Thread(fu).start();
        System.out.println(fu.get());
        //线程的终止
        ThreadEx t1 = new ThreadEx();
        t1.start();
        //t1.stop();//强制停止
        //t1.resume();//恢复挂起的线程，容易死锁
        //t1.suspend();//挂起这个线程*/
        /*Interrept interrept = new Interrept();
        interrept.start();
        Thread.sleep(20);
        interrept.interrupt();//中断机制，协作式*/
        //异常的影响
//        Interrept3 interrept3 = new Interrept3();
        //在启动之前设置，随着main的结束也结束,finally不会被执行
        /*interrept3.setDaemon(true);
        interrept3.start();
        Thread.sleep(10);
        interrept3.interrupt();*/
        Thread[] ths = new Thread[10];
        for(int i = 0;i < 10;i++){
            ths[i] = new Thread(new ThreadLocalTh(i));
            ths[i].start();
        }
        System.out.println("main -- >" + threadLocal.get());//main -- >1
    }
    /*
    使用中断标志位比自己定义变量好
    自己定义变量：阻塞的时候不会被中断
    使用中断标志位：阻塞的时候会被中断
     */
    public static class Interrept3 extends Thread{
        @Override
        public void run() {
            System.out.println("当前线程：" + getName());
            while (!isInterrupted()){
                try {
                    Thread.sleep(100);
                    System.out.println("中断标志位是: " + isInterrupted() + "   debug");
                } catch (InterruptedException e) {//复位中断标志位
                    System.out.println("中断标志位是: " + isInterrupted());
                    //需要手动interrupt()
                    interrupt();
                    e.printStackTrace();
                }finally {
                    System.out.println("1111111111111");
                }
            }
            System.out.println("中断标志位是: " + isInterrupted() + "    end");
        }
    }
    public static class Interrept extends Thread{
        @Override
        public void run() {
            System.out.println("当前线程：" + getName());
            while (!isInterrupted()){
                System.out.println(getName() + " is run");
            }
            System.out.println("中断标志位是: " + isInterrupted());
        }
    }
    public static class Interrept1 implements Runnable{
        @Override
        public void run() {
            System.out.println("当前线程：" + Thread.currentThread().getName());
            while (!Thread.currentThread().isInterrupted()){
                System.out.println(Thread.currentThread().getName() + " is run");
            }
            System.out.println("中断标志位是: " + Thread.currentThread().isInterrupted());
        }
    }
}
