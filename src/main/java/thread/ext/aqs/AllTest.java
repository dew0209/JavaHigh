package thread.ext.aqs;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 显示锁
 *      Lock接口和核心方法
 *          lock():获得锁
 *          unlock():释放锁
 *          tryLock():尝试获得锁
 *      使用必须要遵守的范式：
 *          在finally中进行锁的释放
 *      lock和synchronized的比较：
 *          synchronized是一个关键字，Lock是语法层面
 *          synchronized代码简洁，synchronized经过优化，已经比以前提升很多了
 *          获取锁可以被中端，超时获取锁，尝试获取锁使用显示锁
 *      ReentrantLock：可重入锁,synchronized也是支持锁的可重入的
 *          public static void dfs(int u){
 *              lock.lock();
 *              System.out.println(u);
 *              if(u < 0)return;
 *              dfs(u - 1);
 *              lock.unlock();
 *          }
 *      锁的公平和非公平：
 *          如果在时间上，先对锁进行获得的请求，一定先被满足，这个锁就是公平的，不满足，就是非公平的。非公平锁效率稍微高一点[锁的挂起的恢复浪费的时间比较多，非公平可以压榨cpu资源]，挂起指的是从内存中移除，有需要再加载进来。
 *          ReentrantLock(boolean fair)：缺省是非公平锁，true是公平锁
 *      ReadWriteLock接口：读写锁，不是排他锁，同一时刻允许多个读锁访问，但是写线程访问的时候，所有的读和写都被阻塞，适用于读多写少的情况
 *          实现类ReentrantReadWriteLock：其内部类读锁和写锁是实现了Lock接口的
 *      等待通知机制：
 *          Condition接口实现
 *          一个Lock可以有多个Condition，这个机制非常好，可以避免每次都去唤醒所有等待的.
 *
 *
 *
 */
public class AllTest {

    public static void main(String[] args) {
        dfs(10);
    }

    public static void dfs(int u){
        lock.lock();
        System.out.println(u);
        if(u < 0)return;
        dfs(u - 1);
        lock.unlock();
    }






    private static Lock lock = new ReentrantLock();
    private int count;

    public void increament(){
        lock.lock();
        try {
            count++;
        }finally {
            lock.unlock();
        }
    }
    public synchronized void increament2(){
        count++;
    }
}
