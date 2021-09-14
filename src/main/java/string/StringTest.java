package string;

import org.junit.Test;

/**
 * String 的使用
 */
public class StringTest {
    /*
    String 字符串，使用一对“”引起来表示
    1.string声明为final的，不可被继承
    2.string实现了Serializable接口：表示string可以比较大小
             实现了Comparable接口：表示string可以比较大小
    3.string内部定义了final char[] value 用于存储字符串数据
    4.string：代表不可变的字符序列。简称：不可变性
        体现：1.当对字符串重新赋值时，需要重写指定内存区域赋值，不能使用原有的value进行赋值
             2.当对现有的字符串进行连接操作时，也需要重新指定内存区域赋值，不能使用原有的value进行赋值
             3.当调用string的replace方法修改指定字符或字符串时，也需要重新指定内存区域，不能使用原有的value进行赋值
    5.通过字面量的方式（区别于new）给一个字符串赋值，此时的字符串值声明在字符串常量池中。
    6.字符串常量池中是不会存储相同内容的字符串的
     */
    @Test
    public void test01(){
        String s1 = "abc";//字面量的定义方式
        String s2 = "abc";
        s1 = "hello";
        System.out.println(s1 == s2);//false
        System.out.println(s1);//hello
        System.out.println(s2);//abc
        System.out.println("*********************");
        String s3 = "abc";
        s3 += "def";
        System.out.println(s3);//abcdef
        System.out.println(s2);//abc
        System.out.println("*********************");
        String s4 = "abc";
        String s5 = s4.replace('a', 'm');
        System.out.println(s4);//abc
        System.out.println(s5);//mbc
    }
    /*
    String 的实例化方式：
        方式一：通过字面量的方式
        方式二：通过new + 构造器的方式
        面试题：String s = new String("abc");方式创建对象，在内存中创建了几个对象？
            两个:一个是堆空间中new结构，另一个是char[]对应的常量池中的数据："abc"
     */
    @Test
    public void test02(){
        String s1 = "javaEE";
        String s2 = "javaEE";
        String s3 = new String("javaEE");
        String s4 = new String("javaEE");
        System.out.println(s1 == s2);//true
        System.out.println(s1 == s3);//false
        System.out.println(s1 == s4);//false
        System.out.println(s3 == s4);//false
    }

    /*
    结论：
    1.常量与常量的拼接结果在常量池。且常量池中不会存在相同内容的常量。
    2.只要其中有一个是变量，结果就在堆中。
    3.如果拼接的结果调用intern()方法，返回值就在常量池中
     */
    @Test
    public void test03(){
        String s1 = "javaEE";
        String s2 = "hadoop";

        String s3 = "javaEEhadoop";
        String s4 = "javaEE" + "hadoop";
        String s5 = s1 + "hadoop";
        String s6 = "javaEE" + s2;
        String s7 = s1 + s2;

        System.out.println(s3 == s4);//true
        System.out.println(s3 == s5);//false
        System.out.println(s3 == s6);//false
        System.out.println(s3 == s7);//false
        System.out.println(s5 == s6);//false
        System.out.println(s5 == s7);//false
        System.out.println(s6 == s7);//false

        String s8 = s6.intern();//返回值得到的s8使用的常量值中已经存在的“javaEEhadoop”
        System.out.println(s3 == s8);//true

    }

    @Test
    public void test04(){
        String s1 = "javaEEhadoop";
        String s2 = "javaEE";
        String s3 = s2 + "hadoop";
        System.out.println(s1 == s3);//false

        final String s4 = "javaEE";//s4:常量
        String s5 = s4 + "hadoop";
        System.out.println(s1 == s5);//true
    }

}
