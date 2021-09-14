package generic.mvc;
//表的共性操作的DAO
public interface DAO<T>{
    //添加一条数据
    public void add(T t);
    //删除一条记录
    public boolean remove(int index);
    //修改一条记录
    public void update(int index,int T);
    //泛型方法
    //获取表中一共有多少条记录？获取最大的员工入职时间
    public <E> E getValue();
}
