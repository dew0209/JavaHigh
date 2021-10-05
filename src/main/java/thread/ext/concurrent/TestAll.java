package thread.ext.concurrent;


/**
 * ConcurrentHashMap
 *      hash：散列，把任意长度的输入通过一种算法(散列)，变换为一种固定长度的输出。属于压缩映射，容易产生哈希冲突，解决冲突：开放冲突，再散列，链地址法
 * 前置知识：位运算
 *      a % (2 ^ n) = a % (2 ^ n  - 1)
 *
 * HashMap:put操作在多线程环境下容易死循环(链表产生了环形结构)
 * ConcurrentHashMap
 *      1.7之前
 *      Segment + table + hashEntry    [Segment是锁 解决冲突的是链地址法]
 *      定位Segment：key的hashcode进行再散列值的高位，取模
 *      定位table：key的hashcode进行再散列值取模
 *      依次扫描链表，要么找到元素，要么返回null
 *      Segment不扩容
 *      size：进行两次不加锁统计，不一致，加锁进行统计
 *      put：对Segment进行加锁
 *      弱一致性
 *      如何初始化，如何定位元素在哪个位置
 *      table扩容，数组翻倍，rehash
 *      1.8
 *      取消了Segment
 *      Node数组，锁的粒度更小了，减少并发冲突的概率，key value
 *      链表+红黑树，性能提升大，链表转红黑树[链表元素超过8个]
 *      初始化做了什么事情：
 *          给成员变量赋值[有参构造] 啥也不干[无参构造]
 *       对数组的填充发生在put阶
 *       sizeCtl：
 *          负数：表示进行初始化或者扩容 -1正在初始化，-n表示有n - 1线程正在进行扩容
 *          正树：0表示还没有被初始化，>0的数，初始化或者是下一次进行扩容的阈值
 *       TreeNode:红黑树具体的节点
 *       TreeBin：管理TreeNode的，也就是红黑树
 *       treeifyBin(tab, i)：由链表转换为树
 *       扩容：
 *          transfer(Node<K,V>[] tab, Node<K,V>[] nextTab) 进行实际的扩容，table大小也是翻倍的形式，有一个并发扩容的机制
 *          tableSizeFor(int c)
 *       弱一致性，size 估计的大概数量
 *
 */

public class TestAll {
    //位运算进行权限控制
    public static final int ALLOW_SE = 1 << 0;
    public static final int ALLOW_IN = 1 << 1;
    public static final int ALLOW_UP = 1 << 2;
    public static final int ALLOW_DE = 1 << 3;

    public int flag;

    public void setPer(int per){
        flag = per;
    }
    public void enable(int per){
        flag |= per;
    }
    //删除用户权限
    public void disable(int per){
        flag = flag & (~per);
    }
    public boolean isAllow(int per){
        return (flag & per) == per;
    }
    public boolean isNotAllow(int per){
        return (flag & per) == 0;
    }
    public static void main(String[] args) {
        int flag = 15;
        TestAll testAll = new TestAll();
        testAll.setPer(flag);
        testAll.disable(ALLOW_IN | ALLOW_SE);
        System.out.println(testAll.flag);
        System.out.println(testAll.isAllow(ALLOW_IN));
        System.out.println(testAll.isAllow(ALLOW_SE));
        System.out.println(testAll.isAllow(ALLOW_DE));
        System.out.println(testAll.isAllow(ALLOW_UP));
        System.out.println("华丽的分割线");

    }
}
