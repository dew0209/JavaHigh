package datasource.mybatis_demo;

import java.sql.Connection;
import java.sql.SQLException;

public class TestDataSource {
    public static void main(String[] args) throws SQLException {
        //不使用数据库连接池拿连接
        UnpooledDataSource dataSource  = new UnpooledDataSource(TestDataSource.class.getClassLoader(),"com.jdbc.mysql.Driver","jdbc:mysql:///mybatisend?characterEncoding=UTF-8&amp;serverTimezone=UTC","root","020922");
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
        //使用数据库连接池拿连接

    }
}
