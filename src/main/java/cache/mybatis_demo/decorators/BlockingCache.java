package cache.mybatis_demo.decorators;

import cache.mybatis_demo.Cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingCache implements Cache {

    //阻塞的超时时长
    private long timeout;
    //被装饰的底层对象，一般都是PerpetualCache
    private final Cache delegate;
    //锁对象集，粒度到key值
    private final ConcurrentHashMap<Object, ReentrantLock> locks;
    public BlockingCache(Cache delegate) {
        this.delegate = delegate;
        locks = new ConcurrentHashMap<>();
    }



    @Override
    public String getId() {
        return delegate.getId();
    }

    @Override
    public void putObject(Object key, Object value) {
        try{
            delegate.putObject(key,value);
        }finally {
            releaseLock(key);
        }
    }

    @Override
    public Object getObject(Object key) {
        acquireLock(key);
        Object value = delegate.getObject(key);
        if (value != null){
            releaseLock(key);
        }
        return value;
    }

    @Override
    public Object removeObject(Object key) {
        return null;
    }

    @Override
    public void clear() {
        delegate.clear();
    }

    @Override
    public int getSize() {
        return delegate.getSize();
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return null;
    }

    private void acquireLock(Object key){
        Lock lock = getLockForKey(key);
        if (timeout > 0){
            try {
                boolean acquired = lock.tryLock(timeout, TimeUnit.MILLISECONDS);
                if(!acquired){//超时
                    System.out.println("超时了！！！！");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else {
            lock.lock();
        }
    }
    private ReentrantLock getLockForKey(Object key){
        ReentrantLock lock = new ReentrantLock();//创建锁
        ReentrantLock previous = locks.putIfAbsent(key,lock);
        return previous == null ? lock : previous;//集合中没有锁则返回新锁，有锁就拿集合中的锁对象
    }
    private void releaseLock(Object key){
        ReentrantLock lock = locks.get(key);
        if(lock.isHeldByCurrentThread()){//当前线程持有锁则释放
            lock.unlock();
        }
    }
}
