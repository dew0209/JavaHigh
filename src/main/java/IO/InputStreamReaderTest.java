package IO;

import org.junit.Test;

import java.io.*;

/*
处理流之二：转换流的使用
1.转换流：属于字符流
InputStreamReader：将一个字节的输入流转换为字符的输入流
OutputStreamWriter：将一个字符的输出流转换为字节的输出流
2.作用：提供字节流与字符流之间的转换
3. 解码：字节、字节数组  --->字符数组、字符串
   编码：字符数组、字符串 ---> 字节、字节数组
 */
public class InputStreamReaderTest {
    @Test
    public void test01() throws Exception{
        FileInputStream fis = new FileInputStream("hello.txt");
        InputStreamReader isr = new InputStreamReader(fis,"UTF-8");
        int len;
        char buff[] = new char[5];
        while ((len = isr.read(buff)) != -1){
            System.out.println(new String(buff,0,len));
        }
        isr.close();
    }
    @Test
    public void test02() throws Exception {
        File file1 = new File("hello.txt");
        File file2 = new File("hello_copy.txt");
        FileInputStream fis = new FileInputStream(file1);
        FileOutputStream fos = new FileOutputStream(file2);
        InputStreamReader streamReader = new InputStreamReader(fis);
        OutputStreamWriter writer = new OutputStreamWriter(fos);
        char[] buff = new char[5];
        int len;
        while ((len = streamReader.read(buff)) != -1){
            writer.write(buff,0,len);
        }
        streamReader.close();
        writer.close();
    }
}
