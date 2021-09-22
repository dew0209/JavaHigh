package httprpc.rpc.server;

import httprpc.rpc.RegisterCenter.RegisterCenter;
import httprpc.rpc.Te;
import httprpc.rpc.impl.TeImpl;

import java.io.IOException;

public class Server {
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                RegisterCenter registerCenter = new RegisterCenter(8888);
                registerCenter.register(Te.class, TeImpl.class);
                try {
                    registerCenter.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }
}
