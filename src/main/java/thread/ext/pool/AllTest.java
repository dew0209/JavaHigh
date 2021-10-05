package thread.ext.pool;

import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 线程池
 *      为什么要用线程池
 *          1.降低资源的消耗，重复利用，降低线程创建和销毁的资源的消耗
 *          2.提高响应速度[不需要创建]
 *          3.提高线程的可管理性[统一管理]
 * 实现一个自己的线程池
 *      1.线程必须在池子里面已经创建好了，并且可以保持住，要有容器保存多个线程
 *      2.线程还要能够接受外部的任务，运行这个任务，容器需要存储来不及运行的任务
 *
 *  线程池的创建：
 *      public ThreadPoolExecutor(int corePoolSize,
 *                               int maximumPoolSize,
 *                               long keepAliveTime,
 *                               TimeUnit unit,
 *                               BlockingQueue<Runnable> workQueue,
 *                               ThreadFactory threadFactory,
 *                               RejectedExecutionHandler handler)
 *                   corePoolSize：核心线程数  < corePoolSize 创建新线程 = corePoolSize 任务会被保存队列中      如果调用prestartAllCoreThreads()方法则会一次性创建corePoolSize个线程
 *                   maximumPoolSize：允许的最大线程数， 队列也满了 但 < maximumPoolSize  就会再次创建新的线程
 *                   keepAliveTime：线程空闲下来以后，存活的时间，这个参数只有在 > corePoolSize的时候才有用
 *                   unit：存活时间的单位值
 *                   workQueue：保存任务的阻塞队列
 *                   threadFactory：创建线程的一个工厂，给新建的线程赋名字
 *                   RejectedExecutionHandler：队列和maximumPoolSize都满了，该怎么办，
 *                          AbortPolicy：直接抛出异常，默认的
 *                          DiscardPolicy：当前任务直接丢弃
 *                          DiscardOldestPolicy：丢弃阻塞队列里面最老的任务，也就是队列里面最靠前的任务
 *                          CallerRunsPolicy：由调用者所在的线程来执行任务
 *                          自定义策略：实现RejectedExecutionHandler接口即可
 *     提交任务：execute(Runnable command) 无返回值 Future<?> submit(Runnable task) 有返回值
 *     关闭线程池：shutdown() showdownNow()
 *              showdownNow()不仅要设置关闭的状态，还会尝试停止正在运行或者暂停任务的线程
 *              shutdown()设置关闭的状态，只会中断还没执行任务的线程
 *      合理配置线程池
 *          根据任务的性质来：计算密集型(CPU密集型)[利用cpu和内存]，IO密集型，混合型
 *              计算密集型：加密，大数分解，正则... 线程数设置为cpu的核心+1好一些  Runtime.getRuntime().availableProcessors() 获得cpu的核心数
 *              IO密集型：读取文件，数据库连接，网络通讯... 线程数适当大一些，推荐Cpu核心数*2
 *              混合型的任务：尽量拆分。IO密集型>>计算密集型 拆分意义不大 IO密集型约等于计算密集型 拆分效果好
 *              队列的选择上，选择有界[任务溢出就存储到数据库，然后再读进来]，无界可能会导致内存溢出
 *  FixedThreadPool：创建固定线程数量的，适用于负载较重的服务器。使用了无界队列
 *  SingleThreadExecutor：创建单个线程，需要保证顺序执行任务，不会有多个线程活动
 *  CachedThreadPool：会根据需要来创建新线程的，执行很多短期异步任务的程序，使用了SynchronousQueue
 *  WorkStealingPool：基于ForkJoinPool
 *  ScheduledThreadPoolExecutor：定期执行周期任务   需要处理异常，最好将整个run包裹在try-catch中【见Schedule类，里面有写的测试】
 *
 *          需要定期执行周期任务，Timer不建议使用了。
 *          newSingleThreadScheduledExecutor：只包含一个线程，只需要单个线程执行周期任务，保证顺序的执行各个任务
 *          newScheduledThreadPool 可以包含多个线程的，线程执行周期任务，适度控制后台线程数量的时候
 *          方法说明：
 *              schedule：只执行一次，任务还可以延时执行
 *              scheduleAtFixedRate：提交固定时间间隔的任务
 *              scheduleWithFixedDelay：提交固定延时间隔执行的任务
 *          scheduleAtFixedRate任务超时：
 *          规定60s执行一次，有任务执行了80S，下个任务马上开始执行
 *          第一个任务 时长 80s，第二个任务20s，第三个任务 50s
 *          第一个任务第0秒开始，第80S结束；
 *          第二个任务第80s开始，在第100秒结束；
 *          第三个任务第120s秒开始，170秒结束
 *          第四个任务从180s开始
 *
 *
 */
public class AllTest {
    public static void main(String[] args) {
        MyThreadPool pool = new MyThreadPool(3,0);
        pool.execute(new MyTask(1,"嘿嘿"));
        pool.execute(new MyTask(2,"拉拉"));
        pool.execute(new MyTask(3,"哈哈"));
        pool.execute(new MyTask(4,"略略"));
        pool.execute(new MyTask(5,"AA"));
        pool.execute(new MyTask(6,"BB"));
        pool.execute(new MyTask(7,"CC"));
        pool.execute(new MyTask(8,"DD"));

    }
    private static class MyTask implements Runnable{

        private int id;
        private String name;

        public MyTask(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("任务：" + name + ", 完成");
        }
    }
}


//一个最基本的线程池
class MyThreadPool{
    private static int WORK_NUM = 5;
    private static int TASK_COUNT = 100;
    private static int id = 1;
    //工作线程组
    private WorkerThread[] workerThreads;


    //任务队列
    private final BlockingQueue<Runnable> taskQueue;
    private final int work_num;
    public MyThreadPool(){
        this(WORK_NUM,TASK_COUNT);
    }
    public MyThreadPool(int work_num,int taskCount) {
        if (work_num <= 0)work_num = WORK_NUM;
        if(taskCount <= 0)taskCount = TASK_COUNT;
        taskQueue = new ArrayBlockingQueue<Runnable>(taskCount);
        this.work_num = work_num;
        workerThreads = new WorkerThread[work_num];
        for(int i = 0;i < work_num;i++){
            workerThreads[i] = new WorkerThread();
            workerThreads[i].setName("线程：" + id++);
            workerThreads[i].start();
        }
    }

    //执行任务，其实就是把任务加入任务队列，什么时候执行由线程池管理器决定
    public void execute(Runnable task){
        try {
            taskQueue.put(task);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void destroy(){
        System.out.println("关闭线程池~~~~");
        for(int i = 0;i < work_num;i++)workerThreads[i].stopWorker();
        taskQueue.clear();
    }

    @Override
    public String toString() {
        return "工作线程组大小：" + work_num + ", 任务队列大小：" + taskQueue.size();
    }

    class WorkerThread extends Thread{
        //执行任务
        @Override
        public void run() {
            Runnable r = null;
            try {
                while (!isInterrupted()){
                    r = taskQueue.take();
                    if (r != null){
                        System.out.println(Thread.currentThread().getName() + " , 准备运行");
                        Thread.sleep(1000);
                        r.run();
                    }
                    r = null;//help GC
                }
            } catch (InterruptedException e) {
                //ignore

            }
        }
        public void stopWorker(){
            interrupt();
        }
    }
}
