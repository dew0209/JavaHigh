package generic;

import org.junit.Test;

import java.util.*;

/**
 * 泛型的使用
 *  jdk5.0新增的特性
 * 在集合中使用泛型
 *  总结：
 *      集合接口或集合类在jdk5.0时都修改为带泛型的结构。
 *      在实例化集合类时，可以指明具体的泛型类型
 *      指明完以后，在集合类或接口中凡是定义类或接口时，内部结构（比如：方法、构造器、属性等）使用到类的泛型的位置，都指定为实例化的泛型类型。
 *      注意点：泛型的类型必须是类，不能是基本数据类型。需要用到基本数据类型的位置，拿包装类替换
 *      如果实例化时，没有指明泛型的类型。默认类型为java.lang.Object类型。
 * 如何自定义泛型结构：泛型类、泛型接口；泛型方法。
 */
public class GenericTest {
    @Test
    public void test01(){
        ArrayList<Integer> list = new ArrayList<>();
        list.add(123);
        list.add(456);
        //list.add("tom");//编译失败,编译时会进行类型检查，保证数据的安全
        //遍历
        //方式一：
        for (Integer integer : list) {
            System.out.println(integer);
        }
        //方式二：
        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }
    @Test
    public void test02(){
        HashMap<String, Integer> map = new HashMap<>();
        map.put("Tom",123);
        map.put("Jerry",456);
        map.put("Jack",789);
        //map.put(123,"Harry");//编译失败
        Set<Map.Entry<String, Integer>> entrySet = map.entrySet();
        Iterator<Map.Entry<String, Integer>> iterator = entrySet.iterator();
        while (iterator.hasNext()){
            Map.Entry<String, Integer> next = iterator.next();
            System.out.println(next.getKey() + " ---> " + next.getValue());
        }
    }
    /**
     * 泛型在继承方面的体现
     */
    /*
    1. 泛型在继承方面的体现

      虽然类A是类B的父类，但是G<A> 和G<B>二者不具备子父类关系，二者是并列关系。

       补充：类A是类B的父类，A<G> 是 B<G> 的父类

     */
    @Test
    public void test03(){
        Object obj = null;
        String str = null;
        obj = str;

        Object[] arr1 = null;
        String[] arr2 = null;
        arr1 = arr2;
        //编译不通过
//        Date date = new Date();
//        str = date;
        List<Object> list1 = null;
        List<String> list2 = new ArrayList<String>();
        //此时的list1和list2的类型不具有子父类关系
        //编译不通过
//        list1 = list2;
        /*
        反证法：
        假设list1 = list2;
           list1.add(123);导致混入非String的数据。出错。

         */

        show(list1);
        show1(list2);

    }
    public void show1(List<String> list){

    }

    public void show(List<Object> list){

    }

    @Test
    public void tets04(){
        AbstractList<String> list1 = null;
        List<String> list2 = null;
        ArrayList<String> list3 = null;

        list1 = list3;
        list2 = list3;

        List<String> list4 = new ArrayList<>();
    }
     /*
    2. 通配符的使用
       通配符：?

       类A是类B的父类，G<A>和G<B>是没有关系的，二者共同的父类是：G<?>
     */
    @Test
    public void test05(){
        List<Object> list1 = null;
        List<String> list2 = null;
        List<?> list = null;
        list = list2;
        print(list);
        list = list1;
        print(list);
        List<String> list3 = new ArrayList<>();
        list3.add("AA");
        list3.add("BB");
        list3.add("CC");
        list = list3;
        //添加（写入）：对于List<?>就不能向其内部添加数据
        //除了添加null之外
        /*list.add("DD");
        list.add('?');*/
        list.add(null);
        //获取（读取）：允许读取数据，读取的数据类型为Object
        Object o = list.get(0);
        System.out.println(o);
    }
    public void print(List<?> list){}
    /**
     * 3.有限制条件的通配符的使用
     *           ? extends A:
     *              G<? extends A>可以作为G<A>和G<B>的父类，其中B是A的子类
     *           ? super A:
     *              G<? super A> 可以作为G<A>和G<B>的父类，其中B是A的父类
     */
    @Test
    public void test06(){
        List<? extends Person> list1 = null;
        List<? super Person> list2 = null;
        List<Student> list3 = new ArrayList<Student>();
        List<Person> list4 = new ArrayList<Person>();
        List<Object> list5 = new ArrayList<Object>();
        list1 = list3;
        list1 = list4;
//        list1 = list5;
//        list2 = list3;
        list2 = list4;
        list2 = list5;
        //读取数据：
        list1 = list3;
        Person p = list1.get(0);
        //编译不通过
        //Student s = list1.get(0);
        list2 = list4;
        Object obj = list2.get(0);
        ////编译不通过
//        Person obj = list2.get(0);
        //写入数据：
        //编译不通过
//        list1.add(new Student());
        //编译通过
        list2.add(new Person());
        list2.add(new Student());
    }
}
