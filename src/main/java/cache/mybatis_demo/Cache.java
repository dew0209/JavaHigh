package cache.mybatis_demo;

import java.util.concurrent.locks.ReadWriteLock;

/*
缓存类统一接口
 */
public interface Cache {
    String getId();//缓存实现类的id

    void putObject(Object key,Object value);//往缓存中添加数据，key一般是CacheKey对象

    Object getObject(Object key);//根据指定的key从缓存获取数据

    Object removeObject(Object key);//根据指定的key从缓存中删除数据

    void clear();//清空缓存

    int getSize();//获取缓存的个数

    ReadWriteLock getReadWriteLock();//获取读写锁
}
