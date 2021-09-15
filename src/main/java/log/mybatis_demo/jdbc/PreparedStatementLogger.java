package log.mybatis_demo.jdbc;

import log.mybatis_demo.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;

public class PreparedStatementLogger extends BaseJdbcLogger implements InvocationHandler {
    private final PreparedStatement statement;

    public PreparedStatementLogger(Log statementLog, PreparedStatement statement) {
        super(statementLog);
        this.statement = statement;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //打印日志的能力
        System.out.println("statement正在打印日志~~~~");
        return method.invoke(statement,args);
    }
    public static PreparedStatement neeInstance(PreparedStatement stmt,Log stmtLog){
        InvocationHandler handler = new PreparedStatementLogger(stmtLog,stmt);
        ClassLoader classLoader = PreparedStatement.class.getClassLoader();
        return (PreparedStatement) Proxy.newProxyInstance(classLoader,new Class[]{PreparedStatement.class},handler);
    }
}
