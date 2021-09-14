package socket.base;

import java.net.MalformedURLException;
import java.net.URL;

/*
 URL网络编程
  1.URL:统一资源定位符，对应着互联网的某一资源地址
  2.格式：
   http://localhost:8080/examples/beauty.jpg?username=Tom

 */
public class URLTest {
    /*
    http
    localhost
    8080
    /examples/beauty.jpg
    /examples/beauty.jpg?username=Tom
    username=Tom
     */
    public static void main(String[] args) {
        try {
            URL url = new URL("http://localhost:8080/examples/beauty.jpg?username=Tom");
            /*
            可以利用url.openConnection()完成文件下载
             */
            System.out.println(url.getProtocol());//协议名
            System.out.println(url.getHost());//主机名
            System.out.println(url.getPort());//获得端口号
            System.out.println(url.getPath());//获得文件路径
            System.out.println(url.getFile());//获得文件名
            System.out.println(url.getQuery());//获得查询名字
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
