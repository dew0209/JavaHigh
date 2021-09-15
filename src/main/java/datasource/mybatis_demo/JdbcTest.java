package datasource.mybatis_demo;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;

public class JdbcTest {
    public static void main(String[] args) throws Exception{

        PooledDataSource root = new PooledDataSource("com.mysql.jdbc.Driver", "jdbc:mysql:///mybatisend?characterEncoding=UTF-8&amp;serverTimezone=UTC", "root", "020922");
        int st = 0;
        while (st < 100){
            new Thread(){
                @Override
                public void run() {
                    Connection connection = null;
                    try {
                        connection = root.getConnection();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    try {
                        connection.close();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    Field h = null;
                    try {
                        h = connection.getClass().getSuperclass().getDeclaredField("h");
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                    h.setAccessible(true);
                    try {
                        PooledConnection o = (PooledConnection)h.get(connection);
                        root.pushConnection(o);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                }
            }.start();
            st++;
        }

        Thread.sleep(50000);

    }
}
