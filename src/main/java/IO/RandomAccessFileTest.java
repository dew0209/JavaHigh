package IO;

import org.junit.Test;

import java.io.File;
import java.io.RandomAccessFile;

/**
 * RandomAccessFile的使用
 * 1.RandomAccessFile直接继承于java.lang.Object类，实现了DataInput和DataOutput接口
 * 2.RandomAccessFile既可以作为一个输入流，又可以作为一个输出流
 *
 * 3.如果RandomAccessFile作为输出流时，写出到的文件如果不存在，则在执行过程中自动创建。
 *   如果写出到的文件存在，则会对原有文件内容进行覆盖。（默认情况下，从头覆盖）
 *
 * 4. 可以通过相关的操作，实现RandomAccessFile“插入”数据的效果
 */
public class RandomAccessFileTest {
    @Test
    public void test01() throws Exception{
        RandomAccessFile raf1 = new RandomAccessFile(new File("touxi.png"),"r");
        RandomAccessFile raf2 = new RandomAccessFile(new File("touxi3.png"),"rw");
        byte[] buffer = new byte[5];
        int len;
        while ((len = raf1.read(buffer)) != -1){
            raf2.write(buffer,0,len);
        }
        raf1.close();
        raf2.close();
    }
    //对内容的一种覆盖
    @Test
    public void test02() throws Exception{
        RandomAccessFile raf1 = new RandomAccessFile("hello.txt","rw");
        raf1.seek(3);//将指针调到角标为3的位置
        raf1.write("xyz".getBytes());//

        raf1.close();
    }
    @Test
    public void test03() throws Exception{
        RandomAccessFile raf1 = new RandomAccessFile("hello.txt","rw");

        raf1.seek(3);//将指针调到角标为3的位置
        //保存指针3后面的所有数据到StringBuilder中
        StringBuilder builder = new StringBuilder((int) new File("hello.txt").length());
        byte[] buffer = new byte[20];
        int len;
        while((len = raf1.read(buffer)) != -1){
            builder.append(new String(buffer,0,len)) ;
        }
        //调回指针，写入“xyz”
        raf1.seek(3);
        raf1.write("xyz".getBytes());

        //将StringBuilder中的数据写入到文件中
        raf1.write(builder.toString().getBytes());

        raf1.close();

    }
}
