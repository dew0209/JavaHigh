package httprpc.client;

import java.net.InetAddress;
import java.net.Socket;

public class Cilent {
    public static void main(String[] args) throws Exception{
        InetAddress ip = InetAddress.getByName("127.0.0.1");
        Socket socket = new Socket(ip,8080);

    }
}
