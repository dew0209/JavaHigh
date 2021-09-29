package thread.ext.utils;

import javafx.concurrent.Worker;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class DBPoolTest {
    static DBPool pool = new DBPool();


    public static void main(String[] args) throws InterruptedException {
        int threadCount = 50;
        Thread[] ths = new Thread[50];
        for(int i = 0;i < threadCount;i++){
            ths[i] = new Thread(new Worker());
            ths[i].setName("worker_" + i);
        }
        for(int i = 0; i < 50;i++){
            ths[i].start();

        }
        for(int i = 0;i < 50;i++)
            ths[i].join();
    }
    static class Worker implements Runnable{
        int count = 20;
        @Override
        public void run() {
            while (count > 0){
                try {
                    Connection connection = pool.getConn();
                    if (connection != null){
                        try {
                            connection.createStatement();
                            connection.commit();
                        } finally {
                            pool.releaseConn(connection);
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } finally {
                    count--;
                }
            }
        }
    }
}
