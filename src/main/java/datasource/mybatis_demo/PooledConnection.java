package datasource.mybatis_demo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;

public class PooledConnection implements InvocationHandler {
    //封装真正的连接对象
    private final Connection realConnection;
    private final Connection proxyConnection;
    public PooledConnection(Connection realConnection) {
        this.realConnection = realConnection;
        this.proxyConnection = (Connection) Proxy.newProxyInstance(PooledConnection.class.getClassLoader(),new Class[]{Connection.class},this);
    }

    public Connection getRealConnection() {
        return realConnection;
    }

    public Connection getProxyConnection() {
        return proxyConnection;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if("close".equals(method.getName())){
            //一般不会执行，连接应该复用，而不是直接关闭，要关闭请直接调用真实对象进行关闭
            System.out.println("关闭方法，应该增强");
            Thread.currentThread().sleep(100);
            return null;
        }
        System.out.println("Connection：" + method.getName() + " debug ");
        Thread.currentThread().sleep(100);
        return method.invoke(realConnection,args);
    }
}
