package reflection.base;

import org.junit.Test;

import java.lang.annotation.ElementType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionTest {
    //反射之前，对于Person的操作
    @Test
    public void test01(){
        Person p1 = new Person("Tom", 12);
        p1.age = 10;
        System.out.println(p1);
        p1.show();
        //在Person类外部，不可以通过Person类的对象调用其内部私有结构。
        //比如：name、showNation()以及私有的构造器
    }
    //反射之后
    @Test
    public void test02() throws Exception {
        Class<Person> clazz = Person.class;
        //通过反射，创建Person类的对象
        Constructor<Person> constructor = clazz.getConstructor(String.class, int.class);
        Person tom = constructor.newInstance("Tom", 12);
        System.out.println(tom);
        //通过反射，调用对象指定的属性，方法
        //调用属性
        Field age = clazz.getDeclaredField("age");
        age.set(tom,33);
        System.out.println(tom);
        //调用方法
        Method show = clazz.getDeclaredMethod("show");
        show.invoke(tom);
        System.out.println("*******************************");
        //通过反射，可以调用Person类的私有结构的
        //调用私有的构造器
        Constructor<Person> constructor1 = clazz.getDeclaredConstructor(String.class);
        constructor1.setAccessible(true);
        Person jerry = constructor1.newInstance("Jerry");
        System.out.println(jerry);
        //调用私有的属性
        Field name = clazz.getDeclaredField("name");
        name.setAccessible(true);
        name.set(jerry,"jerry11");
        System.out.println(jerry);
        //调用私有的方法
        Method showNation = clazz.getDeclaredMethod("showNation", String.class);
        showNation.setAccessible(true);
        Object res = showNation.invoke(jerry, "中国");
        System.out.println(res);
    }
    //疑问1：通过直接new的方式或反射的方式都可以调用公共的结构，开发中到底用那个？
    //建议：直接new的方式。
    //什么时候会使用：反射的方式。 反射的特征：动态性
    //疑问2：反射机制与面向对象中的封装性是不是矛盾的？如何看待两个技术？
    //不矛盾。

    /*
    关于java.lang.Class类的理解
    1.类的加载过程：
    程序经过javac.exe命令以后，会生成一个或多个字节码文件(.class结尾)。
    接着我们使用java.exe命令对某个字节码文件进行解释运行。相当于将某个字节码文件
    加载到内存中。此过程就称为类的加载。加载到内存中的类，我们就称为运行时类，此
    运行时类，就作为Class的一个实例。

    2.换句话说，Class的实例就对应着一个运行时类。
    3.加载到内存中的运行时类，会缓存一定的时间。在此时间之内，我们可以通过不同的方式
    来获取此运行时类。
     */
    //获取Class的实例的方式（前三种方式需要掌握）
    @Test
    public void test03() throws ClassNotFoundException {
        //方式一：调用运行时类的属性：.class
        Class<Person> clazz = Person.class;
        System.out.println(clazz);
        //方式二：通过运行时类的对象，调用getClass()
        Person p1 = new Person();
        Class clazz1 = p1.getClass();
        System.out.println(clazz1);
        //方式三：调用class的静态方法
        Class aClass = Class.forName("reflection.base.Person");
        System.out.println(aClass);
        //方式四：使用类的加载器：
        ClassLoader classLoader = ReflectionTest.class.getClassLoader();
        Class aClass1 = classLoader.loadClass("reflection.base.Person");
        System.out.println(aClass1);
    }
    //Class实例可以是哪些结构的说明：
    @Test
    public void test04(){
        Class c1 = Object.class;
        Class c2 = Comparable.class;
        Class c3 = String[].class;
        Class c4 = int[][].class;
        Class c5 = ElementType.class;
        Class c6 = Override.class;
        Class c7 = int.class;
        Class c8 = void.class;
        Class c9 = Class.class;

        int[] a = new int[10];
        int[] b = new int[100];
        Class c10 = a.getClass();
        Class c11 = b.getClass();
        // 只要数组的元素类型与维度一样，就是同一个Class
        System.out.println(c10 == c11);

    }
}
