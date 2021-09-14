package socket.base;


import org.junit.Test;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 实现TCP的网络编程
 *
 */
public class TCPTest2 {
    //客户端
    @Test
    public void test01() throws Exception{
        //1.
        Socket socket = new Socket(InetAddress.getByName("127.0.0.1"),9090);
        //2.
        OutputStream os = socket.getOutputStream();
        //3.
        FileInputStream fis = new FileInputStream(new File("touxi.png"));
        //4.
        byte[] buffer = new byte[1024];
        int len;
        while((len = fis.read(buffer)) != -1){
            os.write(buffer,0,len);
        }
        socket.shutdownOutput();
        InputStream is = socket.getInputStream();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte buff[] = new byte[20];
        int len1;
        while ((len1 = is.read(buff)) != -1){
            baos.write(buff,0,len1);
        }
        System.out.println(baos.toString());
        //5.
        baos.close();
        is.close();
        fis.close();
        os.close();
        socket.close();
    }
    //服务端
    @Test
    public void server() throws Exception{
        //1.
        ServerSocket ss = new ServerSocket(9090);
        //2.
        Socket socket = ss.accept();
        //3.
        InputStream is = socket.getInputStream();
        //4.
        FileOutputStream fos = new FileOutputStream(new File("111.png"));
        //5.
        byte[] buffer = new byte[1024];
        int len;
        while((len = is.read(buffer)) != -1){
            fos.write(buffer,0,len);
        }
        System.out.println("图片传输完成！！");
        OutputStream os = socket.getOutputStream();
        os.write("图片已经收到啦！！！！".getBytes());
        //6.
        os.close();
        fos.close();
        is.close();
        socket.close();
        ss.close();
    }
}
