package httprpc.rpc.impl;

import httprpc.rpc.Te;

public class TeImpl implements Te {
    @Override
    public String XJ(String name) {
        return name + " 为你服务";
    }
}
