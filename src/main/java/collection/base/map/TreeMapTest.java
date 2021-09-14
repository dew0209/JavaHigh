package collection.base.map;

import org.junit.Test;

import java.util.*;

//向TreeMap中添加key-value，要求key必须是由同一个类创建的对象
//因为要按照key进行排序：自然排序 、定制排序
//自然排序
public class TreeMapTest {
    @Test
    public void test01(){
        TreeMap treeMap = new TreeMap();
        treeMap.put(new User("tom",22),98);
        treeMap.put(new User("jerry",21),60);
        treeMap.put(new User("lison",12),20);
        treeMap.put(new User("john",88),18);
        Set set = treeMap.entrySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            Map.Entry next = (Map.Entry) iterator.next();
            System.out.println(next.getKey() + "--->" + next.getValue());
        }
    }
    @Test
    public void test02(){
        TreeMap treeMap = new TreeMap(new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                if (o1 instanceof User && o2 instanceof User) {
                    User u1 = (User) o1;
                    User u2 = (User) o2;
                    return Integer.compare(u1.getAge(), u2.getAge());
                }
                throw new RuntimeException("输入的类型不匹配！");
            }
        });
        treeMap.put(new User("tom",22),98);
        treeMap.put(new User("jerry",21),60);
        treeMap.put(new User("lison",12),20);
        treeMap.put(new User("john",88),18);
        Set set = treeMap.entrySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            Map.Entry next = (Map.Entry) iterator.next();
            System.out.println(next.getKey() + "--->" + next.getValue());
        }
    }
}
