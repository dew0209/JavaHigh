package log.mybatis_demo;

import log.mybatis_demo.jdbc.ConnectionLogger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TestLog {
    /*
    测试
     */
    public static void main(String[] args) throws Exception{
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql:///mybatisend?characterEncoding=UTF-8&amp;serverTimezone=UTC", "root", "020922");
        Connection logger = (Connection)ConnectionLogger.newInstance(connection,LogFactory.getLog("connection"));
        PreparedStatement prepareStatement = logger.prepareStatement("select * from t_user");
        System.out.println(prepareStatement);
        ResultSet resultSet = prepareStatement.executeQuery();
        System.out.println(resultSet);
    }
}
