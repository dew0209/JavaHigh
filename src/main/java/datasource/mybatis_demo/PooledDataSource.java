package datasource.mybatis_demo;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

public class PooledDataSource implements DataSource {
    private final PoolState state = new PoolState(this);
    private final Integer MAX = 10;
    private final UnpooledDataSource dataSource;

    public PooledDataSource(String driver, String url, String username, String password){
        dataSource = new UnpooledDataSource(PooledDataSource.class.getClassLoader(),driver,url,username,password);
    }

    @Override
    public Connection getConnection() throws SQLException {
        return popConnection().getProxyConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return null;
    }
    //归还连接的过程
    protected void pushConnection(PooledConnection conn){
        synchronized (state){
            state.activeConnections.remove(conn);
            if (state.idleConnections.size() < MAX){
                //没有达到上限
                System.out.println("回收资源");
                PooledConnection newConn = new PooledConnection(conn.getRealConnection());//复用连接
                state.idleConnections.add(newConn);
                state.notify();
            }else {
                try {
                    //关闭真正的数据库连接
                    conn.getRealConnection().close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }
    //获得连接的过程
    private PooledConnection popConnection() throws SQLException {
        PooledConnection conn = null;
        while (conn == null){
            synchronized (state){

                if(!state.idleConnections.isEmpty()){//检查是否有空闲连接
                    conn = state.idleConnections.remove(0);
                    System.out.println("拿到连接啦！！！");
                }else {//没有空闲连接
                    //判断活跃连接池种的数量是否大于最大连接数
                    if (state.activeConnections.size() < MAX){
                        System.out.println("创建新连接");
                        conn = new PooledConnection(dataSource.getConnection());
                        state.activeConnections.add(conn);
                    }else {
                        try {
                            System.out.println("即将等待");
                            state.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        }
        return conn;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}
