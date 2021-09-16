package cache.mybatis_demo.impl;

import cache.mybatis_demo.Cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;

public class PerpetualCache implements Cache {

    private final String id;

    private Map<Object,Object> cache = new HashMap();

    public PerpetualCache(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void putObject(Object key, Object value) {
        cache.put(key,value);
    }

    @Override
    public Object getObject(Object key) {
        return cache.get(key);
    }

    @Override
    public Object removeObject(Object key) {
        return cache.remove(key);
    }

    @Override
    public void clear() {
        cache.clear();
    }

    @Override
    public int getSize() {
        return cache.size();
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (getId() == null){
            System.out.println("非法");
        }
        if (this == o)return true;
        if (!(o instanceof Cache))return false;
        Cache otherCache = (Cache)o;
        return getId().equals(otherCache.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
