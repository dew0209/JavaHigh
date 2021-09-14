package IO;

import org.junit.Test;

import java.io.*;

/*
处理流之一：缓冲流的使用
1.缓冲流：
    BufferedInputStream
    BufferedOutputStream
    BufferedReader
    BufferedWriter
2.作用：提供流的读取、写入的速度
    提高读写速度的原因：内部提供了一个缓冲区
3. 处理流，就是“套接”在已有的流的基础上。
 */
public class BufferedTest {
    /*
    实现非文本文件的复制
     */
    @Test
    public void test01() throws Exception{
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File("touxi.png")));
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File("touxi2.png")));
        int len;
        byte[] buff = new byte[5];
        while ((len = bis.read(buff)) != -1){
            bos.write(buff,0,len);
        }
        bis.close();
        bos.close();
    }

}
