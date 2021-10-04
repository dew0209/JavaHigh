package thread.ext.aqs.example;

import edu.emory.mathcs.backport.java.util.concurrent.locks.Lock;
import edu.emory.mathcs.backport.java.util.concurrent.locks.ReadWriteLock;
import edu.emory.mathcs.backport.java.util.concurrent.locks.ReentrantReadWriteLock;



public class UseRwLock implements GoodsService{
    private GoodsInfo goodsInfo;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock getLock = lock.readLock();//读锁
    private final Lock setLock = lock.writeLock();//写锁

    public UseRwLock(GoodsInfo goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    @Override
    public GoodsInfo getNum() {
        try{
            getLock.lock();
            try {
                Thread.currentThread().sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }finally {
            getLock.unlock();
        }

        return null;
    }

    @Override
    public void setNum(int number) {

        try {
            setLock.lock();
            Thread.currentThread().sleep(5);
            goodsInfo.changeNumber(number);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            setLock.unlock();
        }


    }
}
