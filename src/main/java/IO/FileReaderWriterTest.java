package IO;

import org.junit.Test;

import java.io.*;

/**
 * 流的分类
 *  1.数据操作单为：字节流，字符流
 *  2.数据的流向：输入流，输出流
 *  3.流的角色：节点流，处理流
 * 流的体系结构
 *   抽象基类         节点流（或文件流）                               缓冲流（处理流的一种）
 *   InputStream     FileInputStream   (read(byte[] buffer))        BufferedInputStream (read(byte[] buffer))
 *   OutputStream    FileOutputStream  (write(byte[] buffer,0,len)  BufferedOutputStream (write(byte[] buffer,0,len) / flush()
 *   Reader          FileReader (read(char[] cbuf))                 BufferedReader (read(char[] cbuf) / readLine())
 *   Writer          FileWriter (write(char[] cbuf,0,len)           BufferedWriter (write(char[] cbuf,0,len) / flush()
 */
public class FileReaderWriterTest {
    @Test
    public void test01(){
        File file = new File("hello.txt");
        System.out.println(file.getAbsoluteFile());

    }
    /*
    说明点：
    1. read()的理解：返回读入的一个字符。如果达到文件末尾，返回-1
    2. 异常的处理：为了保证流资源一定可以执行关闭操作。需要使用try-catch-finally处理
    3. 读入的文件一定要存在，否则就会报FileNotFoundException。
     */
    @Test
    public void test02() {

        FileReader reader = null;
        try {
            File file = new File("hello.txt");
            reader = new FileReader(file);
            //数据的读入
            //read()：返回读入的一个字符，如果达到文件末尾，则返回-1
            int data = reader.read();
            while (data != -1){
                System.out.println((char)data);
                data = reader.read();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @Test
    public void test03(){
        FileReader reader = null;
        try {
            File file = new File("hello.txt");
            reader = new FileReader(file);
            //数据的读入
            //read()：返回读入的一个字符，如果达到文件末尾，则返回-1
            char[] buff = new char[5];
            int len;
            while ((len = reader.read(buff)) != -1){
                String str = new String(buff,0,len);
                System.out.println(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //资源的关闭
            if (reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /*
    从内存中写出数据到硬盘的文件里。

    说明：
    1. 输出操作，对应的File可以不存在的。并不会报异常
    2.
         File对应的硬盘中的文件如果不存在，在输出的过程中，会自动创建此文件。
         File对应的硬盘中的文件如果存在：
                如果流使用的构造器是：FileWriter(file,false) / FileWriter(file):对原有文件的覆盖
                如果流使用的构造器是：FileWriter(file,true):不会对原有文件覆盖，而是在原有文件基础上追加内容
     */
    @Test
    public void test04(){
        File file = new File("hello1.txt");
        FileWriter writer = null;
        try {
            writer = new FileWriter(file);
            writer.write("I have a dream!\n");
            writer.write("you need to have a dream!");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null){
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    @Test
    public void test05(){
        FileWriter writer = null;
        FileReader reader = null;
        try {
            writer = new FileWriter(new File("hello2.txt"));
            reader = new FileReader(new File("hello.txt"));
            char buff[] = new char[5];
            int len;
            while ((len = reader.read(buff)) != -1){
                writer.write(buff,0,len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(writer != null){
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
