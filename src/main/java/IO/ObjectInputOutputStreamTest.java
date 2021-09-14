package IO;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 对象流的使用
 * 1.ObjectInputStream 和 ObjectOutputStream
 * 2.作用：用于存储和读取基本数据类型数据或对象的处理流。它的强大之处就是可以把Java中的对象写入到数据源中，也能把对象从数据源中还原回来。
 *
 * 3.要想一个java对象是可序列化的，需要满足相应的要求。见Person.java
 *
 * 4.序列化机制：
 * 对象序列化机制允许把内存中的Java对象转换成平台无关的二进制流，从而允许把这种
 * 二进制流持久地保存在磁盘上，或通过网络将这种二进制流传输到另一个网络节点。
 * 当其它程序获取了这种二进制流，就可以恢复成原来的Java对象。
 */
public class ObjectInputOutputStreamTest {
    /*
    序列化过程：将内存中的Java对象保存到磁盘或者通过网络传输出去
     */
    @Test
    public void test01()throws Exception{
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("obj.dat"));
        oos.writeObject(new String("我爱北京天安门"));
        oos.flush();
        oos.writeObject(new Person("李四",12));
        oos.flush();
        oos.close();
    }
    /*
    反序列化：将磁盘文件中的对象还原为内存中的一个Java对象
     */
    @Test
    public void test02() throws  Exception{
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("obj.dat"));
        Object o = ois.readObject();
        Object o1 = ois.readObject();
        System.out.println(o);
        System.out.println(o1);
        ois.close();
    }
}
