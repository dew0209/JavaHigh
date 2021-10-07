package thread.ext.pool;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class CompletionServiceTest {

}

class CompletionServiceCase{
    private final int POOL_SIZE = Runtime.getRuntime().availableProcessors();
    private final int TOTAL_TASK = Runtime.getRuntime().availableProcessors() * 10;

    //方法一：自己写集合来实现获取线程池中任务的返回结果
    public void testByQueue() throws Exception{
        long start = System.currentTimeMillis();
        //休眠的总时长
        AtomicInteger count = new AtomicInteger(0);
        //创建线程池
        ExecutorService service = Executors.newFixedThreadPool(POOL_SIZE);
        BlockingQueue<Future<Integer>> futures = new LinkedBlockingQueue<>();
        for(int i = 0;i < TOTAL_TASK;i++){
            Future<Integer> futureTask = service.submit(new CompletionService("task_" + i));
            futures.add(futureTask);
        }
        //查看结果
        for(int i = 0;i < TOTAL_TASK;i++){
            int sleptTime = futures.take().get();//只能按照顺序取
            System.out.println("slept " + sleptTime + " ms...");
            count.addAndGet(sleptTime);
        }
        //关闭线程池
        service.shutdown();
        System.out.println("tasks sleep time " + count.get() + " ms, ans spend time " + (System.currentTimeMillis() - start) + " ms");
    }

    //方法二：
    public void testByCompletion() throws Exception{
        long start = System.currentTimeMillis();
        AtomicInteger count = new AtomicInteger(0);
        ExecutorService pool = Executors.newFixedThreadPool(POOL_SIZE);
        //可以使用
        java.util.concurrent.CompletionService<Integer> ret = new ExecutorCompletionService(pool);
        for(int i = 0;i < TOTAL_TASK;i++){
            ret.submit(new CompletionService("task_" + i));
        }
        for(int i = 0;i < TOTAL_TASK;i++){
            int sleptTime = ret.take().get();//只能按照顺序取
            System.out.println("slept " + sleptTime + " ms...");
            count.addAndGet(sleptTime);
        }
        pool.shutdown();

        System.out.println("tasks sleep time " + count.get() + " ms, ans spend time " + (System.currentTimeMillis() - start) + " ms");
    }


    public static void main(String[] args) throws Exception {
        new CompletionServiceCase().testByQueue();
        new CompletionServiceCase().testByCompletion();
    }

}