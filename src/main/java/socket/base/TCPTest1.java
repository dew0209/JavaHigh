package socket.base;


import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 实现TCP的网络编程
 * 例子1：客户端发送信息给服务端，服务端将数据显示在控制台上
 *
 */
public class TCPTest1 {
    //客户端
    @Test
    public void test01(){
        Socket socket = null;
        OutputStream os = null;
        try{
            InetAddress address = InetAddress.getByName("127.0.0.1");
            socket = new Socket(address, 8080);
            os = socket.getOutputStream();
            os.write("你好，我是客户端！！！！".getBytes());
        }catch (Exception e){

        }finally {
            if (os != null){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (socket != null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    //服务端
    @Test
    public void server(){
        ServerSocket ss = null;
        Socket socket = null;
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        try{
            ss = new ServerSocket(8080);
            socket = ss.accept();
            is = socket.getInputStream();
            baos = new ByteArrayOutputStream();
            byte[] buff = new byte[5];
            int len;
            while((len = is.read(buff)) != -1){
                baos.write(buff,0,len);
            }
            System.out.println(baos.toString());
        }catch (Exception e){

        }finally {
            if(baos != null){
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(socket != null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(ss != null){
                try {
                    ss.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
