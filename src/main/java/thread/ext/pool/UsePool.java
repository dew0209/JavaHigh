package thread.ext.pool;

import java.util.Random;
import java.util.concurrent.*;

public class UsePool {
    static class Worker implements Runnable{
        private String taskName;
        private Random r = new Random();

        public Worker(String taskName) {
            this.taskName = taskName;
        }

        public String getName() {
            return taskName;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " " + taskName);
            try {
                Thread.sleep(r.nextInt(100) * 5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class CallWorker implements Callable<String> {
        private String taskName;
        private Random r = new Random();

        public CallWorker(String taskName) {
            this.taskName = taskName;
        }

        public String getName() {
            return taskName;
        }


        @Override
        public String call() throws Exception {
            System.out.println(Thread.currentThread().getName() + " " + taskName);

            return Thread.currentThread().getName() + " " + taskName + " : " + r.nextInt(100) * 5;
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService pool = new ThreadPoolExecutor(2,
                4,3,
                TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(10),
                new ThreadPoolExecutor.AbortPolicy());
        pool = Executors.newCachedThreadPool();
        for(int i = 0;i < 6;i++){
            Worker w = new Worker("worker_" + i);
            pool.execute(w);
        }
        for(int i = 0;i < 6;i++){
            CallWorker w = new CallWorker("worker_" + i);
            Future<String> submit = pool.submit(w);
            System.out.println(submit.get());
        }

        pool.shutdown();


    }
}
