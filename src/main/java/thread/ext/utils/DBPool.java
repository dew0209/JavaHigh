package thread.ext.utils;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;

public class DBPool {
    private final static int POOL_SIZE = 10;
    private final Semaphore useful = new Semaphore(POOL_SIZE);//尚未被使用
    private final Semaphore useless = new Semaphore(0);//已经在用了   空位也是一种资源

    //初始化数据库连接池的容器
    private static LinkedList<Connection> pool = new LinkedList<>();
    public DBPool(){
        if(POOL_SIZE > 0){
            for(int i = 0;i < POOL_SIZE;i++){
                pool.add(SqlConnectionImpl.fetchConnection());
            }
        }
    }
    //在mils时间内拿不到，就返回null
    public Connection getConn() throws InterruptedException {
        useful.acquire();
        Connection connection;
        synchronized (pool){
            connection = pool.removeFirst();
        }
        useless.release();
        return connection;
    }
    public void releaseConn(Connection conn) throws InterruptedException {
        if(conn != null){
            System.out.println("当前有 " + useful.getQueueLength() + " 等待连接");
            useless.acquire();
            synchronized (pool){
                pool.addLast(conn);
            }
            useful.release();
        }
    }
}
