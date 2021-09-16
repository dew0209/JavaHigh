package cache.mybatis_demo;

import java.io.Serializable;

public class NullCacheKey extends CacheKey{
    public NullCacheKey(){
        super();
    }

    @Override
    public void update(Object obj) {
        System.out.println("不被允许的操作");
    }

    @Override
    public void updateAll(Object[] objects) {
        System.out.println("不被允许");
    }
}
