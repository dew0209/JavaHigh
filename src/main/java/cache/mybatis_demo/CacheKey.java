package cache.mybatis_demo;

import reflection.mybatis_demo.ArrayUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 缓存的key值，也是同步监视器
 */
public class CacheKey implements Cloneable, Serializable {
    private static final long serialVersionUID = 1146682552656046210L;

    private static final int DEFAULT_MULTIPLYER = 37;
    private static final int DEFAULT_HASHCODE = 17;

    public static final CacheKey NULL_CACHE_KEY = null;

    private final int multiplie;//参数hash计算的乘数
    private int hashCode;//CacheKey的hash值，在update函数中实时运算出来的
    private long checkSum;//校验和，hash值的和
    private int count;//updateList中元素的个数
    private List<Object> updateList;

    public CacheKey() {
        this.hashCode = DEFAULT_HASHCODE;
        this.multiplie = DEFAULT_MULTIPLYER;
        this.count = 0;
        this.updateList = new ArrayList<>();
    }
    public CacheKey(Object[] objects){
        this();
        updateAll(objects);
    }
    public int getUpdateCount(){return updateList.size();}

    public void update(Object obj){
        //获取object的hash值
        int baseHashCode = obj == null ? 1 : ArrayUtil.hashCode(obj);
        count++;
        checkSum += baseHashCode;
        baseHashCode *= count;
        hashCode = multiplie * hashCode + baseHashCode;
        updateList.add(obj);
    }

    public void updateAll(Object[] objects){
        for(Object o : objects){
            update(o);
        }
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)return true;//是否为同一个对象
        if(!(object instanceof  CacheKey))return false;
        final CacheKey cacheKey = (CacheKey)object;
        if(hashCode != cacheKey.hashCode)return false;
        if(checkSum != cacheKey.checkSum)return false;
        if (count != cacheKey.count)return false;
        //比较具体元素的hash值
        for(int i = 0;i < updateList.size();i++){
            Object thisObject = updateList.get(i);
            Object thatObject = cacheKey.updateList.get(i);
            if (!ArrayUtil.equals(thisObject,thatObject))return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return hashCode;
    }
    @Override
    public String toString() {
        StringBuilder returnValue = new StringBuilder().append(hashCode).append(':').append(checkSum);
        for (Object object : updateList) {
            returnValue.append(':').append(ArrayUtil.toString(object));
        }
        return returnValue.toString();
    }

    @Override
    public CacheKey clone() throws CloneNotSupportedException {
        CacheKey clonedCacheKey = (CacheKey) super.clone();
        clonedCacheKey.updateList = new ArrayList<>(updateList);
        return clonedCacheKey;
    }
}
