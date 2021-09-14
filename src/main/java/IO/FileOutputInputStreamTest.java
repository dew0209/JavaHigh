package IO;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/*
1. 对于文本文件(.txt,.java,.c,.cpp)，使用字符流处理
2. 对于非文本文件(.jpg,.mp3,.mp4,.avi,.doc,.ppt,...)，使用字节流处理
 */
public class FileOutputInputStreamTest {
    //使用FileInputStream处理文本文件，可能会出现乱码
    @Test
    public void test01(){
        FileInputStream fis = null;
        try{
            fis = new FileInputStream(new File("hello.txt"));
            byte[] buff = new byte[10];
            int len;
            while ((len = fis.read(buff)) != -1){
                System.out.println(new String(buff,0,len));
            }
        }catch (Exception e){

        }finally {
            if (fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * 实现对图片的复制操作
     */
    @Test
    public void test02(){
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try{
            fis = new FileInputStream(new File("touxi.png"));
            fos = new FileOutputStream(new File("touxi1.png"));
            int len;
            byte[] buff = new byte[10];
            while ((len = fis.read(buff)) != -1){
                fos.write(buff,0,len);
            }
        }catch (Exception e){

        }finally {
            if (fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
