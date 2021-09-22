package httprpc.rpc.client;

import httprpc.rpc.Te;

public class Client {
    public static void main(String[] args) throws Exception {
        Te te = RpcClientFrame.getRemoteProxyObj(Te.class);
        System.out.println(te.XJ("king"));
    }
}
