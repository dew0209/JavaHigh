package datasource.mybatis_demo;

import java.util.ArrayList;
import java.util.List;

/*
    用于管理PooledConnection对象状态的组件，通过两个list实现
 */
public class PoolState {

    protected PooledDataSource dataSource;
    //空闲的连接池资源集合
    protected final List<PooledConnection> idleConnections = new ArrayList<>();
    //活跃的连接池资源集合
    protected final List<PooledConnection> activeConnections = new ArrayList<>();

    public PoolState(PooledDataSource dataSource) {
        this.dataSource = dataSource;
    }

}
