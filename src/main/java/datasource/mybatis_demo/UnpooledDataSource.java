package datasource.mybatis_demo;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class UnpooledDataSource implements DataSource {
    private ClassLoader driverClassLoader;//驱动类的类加载
    //缓存已经注册的数据库驱动类
    private static Map<String, Driver> registeredDrivers = new ConcurrentHashMap<>();
    private String driver;
    private String url;
    private String username;
    private String password;
    //是否自动提交
    private Boolean autoCommit;

    public UnpooledDataSource(ClassLoader driverClassLoader, String driver, String url, String username, String password) {
        this.driverClassLoader = driverClassLoader;

        this.driver = driver;
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public Connection doGetConnection(Properties properties) throws SQLException {
        initializeDriver();
        Connection connection = DriverManager.getConnection(url,properties);
        configureConnection(connection);
        return connection;
    }
    //设置事物的自动提交
    private void configureConnection(Connection connection) throws SQLException {
        if (autoCommit != null){
            connection.setAutoCommit(autoCommit);
        }
    }

    public synchronized void initializeDriver(){
        if(!registeredDrivers.containsKey(driver)){//未被缓存
            Class<?> driverType;
            try{
                driverType = Class.forName(driver,true,driverClassLoader);
                Driver o = (Driver)driverType.newInstance();
                registeredDrivers.put(driver,o);
            }catch (Exception e){

            }
        }
    }



    @Override
    public Connection getConnection() throws SQLException {
        Properties properties = new Properties();
        if (username != null) {
            properties.setProperty("user", username);
        }
        if (password != null) {
            properties.setProperty("password", password);
        }
        return doGetConnection(properties);
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return null;
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
