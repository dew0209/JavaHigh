package thread.ext.aqs;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestMyLock {
    public static void main(String[] args) {
        final Lock lock = new SelfLock();
        //final Lock lock = new ReentrantLock();
        class Worker extends Thread{
            @Override
            public void run() {
                while (true){
                    lock.lock();
                    try {
                        sleep(1000);
                        System.out.println(Thread.currentThread().getName());
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        lock.unlock();
                    }
                    try {
                        sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        for(int i = 0;i < 10;i++){
            Worker w = new Worker();
            w.setDaemon(true);
            w.start();

        }
        for(int i = 0;i < 10;i++){
            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println();
        }

    }
}
