package thread.ext.p2;

import java.sql.Connection;
import java.util.LinkedList;

public class DBPool {

    //初始化数据库连接池的容器
    private static LinkedList<Connection> pool = new LinkedList<>();
    public DBPool(int init){
        if(init > 0){
            for(int i = 0;i < init;i++){
                pool.add(SqlConnectionImpl.fetchConnection());
            }
        }
    }
    //在mils时间内拿不到，就返回null
    public Connection fetchConnection(long mils) throws InterruptedException {
        //不可能无限的等待，设置超时时间
        synchronized (pool){
            if(mils < 0){
                while (pool.isEmpty()){
                    wait();
                }
                return pool.removeFirst();
            }else {
                long overTime = System.currentTimeMillis() + mils;
                long remain = mils;
                while (pool.isEmpty() && remain > 0){
                    pool.wait(remain);
                    remain = overTime - System.currentTimeMillis();
                }
                //有可能结束之后仍然pool.isEmpty()，这样就是超时了
                Connection result = null;
                if(!pool.isEmpty()){
                    result = pool.removeFirst();
                }
                return result;
            }
        }
    }
    public void releaseConn(Connection conn){
        if(conn != null){
            synchronized (pool){
                pool.addLast(conn);
                pool.notifyAll();
            }
        }
    }
}
