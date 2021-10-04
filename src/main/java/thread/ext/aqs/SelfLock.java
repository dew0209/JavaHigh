package thread.ext.aqs;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 类似ReentrantLock的一个锁【不支持重入】
 */
public class SelfLock implements Lock {

    //state表示获取到state = 1 state = 0 这个线程没有获得到锁
    private static class Sync extends AbstractQueuedSynchronizer{
        //当前锁是否已经被占用了
        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }

        @Override
        protected boolean tryAcquire(int arg) {
            if (compareAndSetState(0,1)){
                //这个线程拿到了这个锁
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
            if(getState() == 0){
                throw new UnsupportedOperationException();
            }
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }
        private Condition newCondition(){
            return new ConditionObject();
        }
    }
    private final Sync sync = new Sync();
    @Override
    public void lock() {
        sync.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {

        return sync.tryAcquireNanos(1,unit.toNanos(time));
    }

    @Override
    public void unlock() {
        sync.release(1);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }


}
