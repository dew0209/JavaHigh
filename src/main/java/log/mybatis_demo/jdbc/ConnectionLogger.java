package log.mybatis_demo.jdbc;

import log.mybatis_demo.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class ConnectionLogger extends BaseJdbcLogger implements InvocationHandler {
    private final Connection connection;

    public ConnectionLogger(Log statementLog, Connection connection) {
        super(statementLog);
        this.connection = connection;
    }



    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //对连接的增强，进行日志打印
        if ("prepareStatement".equals(method.getName())){
            PreparedStatement stmt = (PreparedStatement)method.invoke(connection,args);
            stmt = PreparedStatementLogger.neeInstance(stmt,statementLog);//创建代理对象
            System.out.println("创建语句执行器成功！");
            return stmt;
        }
        return method.invoke(connection,args);
    }
    public static Connection newInstance(Connection conn, Log statementLog) {
        InvocationHandler handler = new ConnectionLogger(statementLog,conn);
        ClassLoader cl = Connection.class.getClassLoader();
        return (Connection) Proxy.newProxyInstance(cl, new Class[]{Connection.class}, handler);
    }
}
