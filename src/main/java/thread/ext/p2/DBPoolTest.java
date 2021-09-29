package thread.ext.p2;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class DBPoolTest {
    static DBPool pool = new DBPool(10);
    static CountDownLatch end;

    public static void main(String[] args) {
        int threadCount = 50;
        end = new CountDownLatch(threadCount);
        int count = 20;
        AtomicInteger got = new AtomicInteger();//计数器：统计拿到连接的线程
        AtomicInteger notGot = new AtomicInteger();//计数器：统计没有拿到连接的线程
        for(int i = 0;i < threadCount;i++){
            Thread t = new Thread(new Worker(count,got,notGot));
            t.setName("worker_" + i);
            t.start();
        }
        try {
            end.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("总共尝试了: " + threadCount * count);
        System.out.println("拿到连接的： " + got);
        System.out.println("没有拿到连接的： " + notGot);
    }
    static class Worker implements Runnable{
        int count;
        AtomicInteger got;
        AtomicInteger notGot;

        public Worker(int count, AtomicInteger got, AtomicInteger notGot) {
            this.count = count;
            this.got = got;
            this.notGot = notGot;
        }

        @Override
        public void run() {
            while (count > 0){
                try {
                    Connection connection = pool.fetchConnection(1000);
                    if (connection != null){
                        try {
                            connection.createStatement();
                            connection.commit();
                        } finally {
                            pool.releaseConn(connection);
                            got.incrementAndGet();
                        }
                    }else {
                        notGot.incrementAndGet();
                        System.out.println(Thread.currentThread().getName() + " 等待超时！！！");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } finally {
                    count--;
                }
            }
            end.countDown();
        }
    }
}
