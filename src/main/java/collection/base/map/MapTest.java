package collection.base.map;

import org.junit.Test;

import java.util.*;

/**
 * 一：Map的实现类的结构
 *  |---Map:双列数据，存储key-value对的数据 ---类似于高中的函数：y = f(x)
 *      |---HashMap:作为Map的主要实现类；线程不安全的，效率高；存储null的key和value
 *          |---LinkedHashMap:保证在遍历map元素时，可以按照添加的顺序实现遍历。
 *              原因：在原有的HashMap底层结构基础上，添加了一对指针，指向前一个和后一个元素。
 *              对于频繁的遍历操作，此类执行效率高于HashMap。
 *      |---TreeMap:保证按照添加的key-value对进行排序，实现排序遍历。此时考虑key的自然排序或定制排序
 *          底层使用红黑树
 *      |---Hashtable:作为古老的实现类；线程安全的，效率低；不能存储null的key和value
 *          |----Properties:常用来处理配置文件。key和value都是String类型
 * 二、Map结构的理解：
 *  Map中的key:无序的、不可重复的，使用Set存储所有的key  ---> key所在的类要重写equals()和hashCode() （以HashMap为例）
 *  Map中的value:无序的、可重复的，使用Collection存储所有的value --->value所在的类要重写equals()
 *  一个键值对：key-value构成了一个Entry对象。
 *  Map中的entry:无序的、不可重复的，使用Set存储所有的entry
 *
 */
/*
    添加、删除、修改操作：
     Object put(Object key,Object value)：将指定key-value添加到(或修改)当前map对象中
     void putAll(Map m):将m中的所有key-value对存放到当前map中
     Object remove(Object key)：移除指定key的key-value对，并返回value
     void clear()：清空当前map中的所有数据
     元素查询的操作：
     Object get(Object key)：获取指定key对应的value
     boolean containsKey(Object key)：是否包含指定的key
     boolean containsValue(Object value)：是否包含指定的value
     int size()：返回map中key-value对的个数
     boolean isEmpty()：判断当前map是否为空
     boolean equals(Object obj)：判断当前map和参数对象obj是否相等
     元视图操作的方法：
     Set keySet()：返回所有key构成的Set集合
     Collection values()：返回所有value构成的Collection集合
     Set entrySet()：返回所有key-value对构成的Set集合
     总结：常用方法：
        添加：put(Object key,Object value)
        删除：remove(Object key)
        修改：put(Object key,Object value)
        查询：get(Object key)
        长度：size()
        遍历：keySet() / values() / entrySet()
 */
public class MapTest {
    @Test
    public void test01(){
        Map map = new HashMap();
        map.put("AA",123);
        map.put(45,123);
        map.put("BB",56);
        //遍历：keySet()
        Set set = map.keySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
        System.out.println();
        //遍历：values()
        Collection values = map.values();
        for (Object value : values) {
            System.out.println(value);
        }
        System.out.println();
        //遍历：key-value
        //方式一：entrySet()
        Set entrySet = map.entrySet();
        Iterator iterator1 = entrySet.iterator();
        while (iterator1.hasNext()){
            Map.Entry next = (Map.Entry)iterator1.next();
            System.out.println(next.getKey() + "---->" + next.getValue());
        }
        System.out.println();
        //方式二：
        Set set1 = map.keySet();
        Iterator iterator2 = set1.iterator();
        while (iterator2.hasNext()){
            Object next = iterator2.next();
            System.out.println(next + "--->" + map.get(next));
        }
    }
    /*
    元素查询的操作：
         Object get(Object key)：获取指定key对应的value
         boolean containsKey(Object key)：是否包含指定的key
         boolean containsValue(Object value)：是否包含指定的value
         int size()：返回map中key-value对的个数
         boolean isEmpty()：判断当前map是否为空
         boolean equals(Object obj)：判断当前map和参数对象obj是否相等
     */
    @Test
    public void test02(){
        Map map = new HashMap();
        map.put("AA",123);
        map.put(45,123);
        map.put("BB",56);
        System.out.println(map.get(45));
        System.out.println(map.containsKey("BB"));
        System.out.println(map.containsValue(123));
        map.clear();
        System.out.println(map.isEmpty());
    }
    /*
    添加、删除、修改操作：
         Object put(Object key,Object value)：将指定key-value添加到(或修改)当前map对象中
         void putAll(Map m):将m中的所有key-value对存放到当前map中
         Object remove(Object key)：移除指定key的key-value对，并返回value
         void clear()：清空当前map中的所有数据
     */
    @Test
    public void test03(){
        Map map = new HashMap();
        //添加
        map.put("AA",123);
        map.put(45,123);
        map.put("BB",56);
        //修改
        map.put("AA",87);
        System.out.println(map);
        Map map1 = new HashMap();
        map1.put("CC",123);
        map1.put("DD",123);
        map.putAll(map1);
        Object cc = map.remove("CC");
        System.out.println("--->" + cc);
        System.out.println(map);
        map.clear();
        System.out.println(map.size());
        System.out.println(map);
    }
    @Test
    public void test04(){
        Map map = new HashMap();
        //map = new Hashtable();//异常，不能存储null
        map.put(null,123);
    }
}
