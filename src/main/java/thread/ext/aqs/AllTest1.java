package thread.ext.aqs;

/**
 * LockSupport工具
 *      作用：
 *          阻塞一个线程
 *          唤醒一个线程
 *          构建同步组件的基础工具
 *      方法：
 *          park开头的 阻塞
 *          unpark开头的 唤醒
 * AQS：
 *      什么是AQS：构建显示锁的基础，是一个抽象的队列同步器
 *      继承，模板方法设计模式
 *          独占式(可重入锁)获取锁：
 *              模板方法：
 *                  acquire(int arg)
 *                  acquireInterruptibly(int arg)
 *                  tryAcquireNanos(int arg, long nanosTimeout)
 *              需要子类覆盖的：
 *                  tryAcquire(int arg)
 *          独占式(可重入锁)释放锁：
 *              模板方法
 *                  release(int arg)
 *              需要子类覆盖的：
 *                  tryRelease(int arg)
 *          共享式(读写锁)获取锁：
 *              模板方法：
 *                  acquireShared(int arg)
 *                  acquireSharedInterruptibly(int arg)
 *                  tryAcquireSharedNanos(int arg, long nanosTimeout)
 *              需要子类覆盖的方法
 *                  tryAcquireShared(int arg)
 *                  isHeldExclusively()  这个同步器是否处于独占模式
 *          共享式(读写锁)释放锁：
 *              模板方法
 *                  releaseShared(int arg)
 *              需要子类覆盖的方法：
 *                  releaseShared(int arg)
 *      数据结构：
 *          双向链表
 *
 *      同步状态标识位：
 *
 *          private volatile int state;
 *
 *          getState setState
 *          compareAndSetState：使用cas设置状态，保证状态的原子性
 *
 *      AQS源码分析：
 *          拿不到锁，将其构造成一个节点，放进一个同步队列[双向的链表] prev 指向上一个节点，next 指向下一个节点
 *          node的状态：
 *               static final int CANCELLED =  1; 线程等待超时了，需要被移走
 *               static final int SIGNAL    = -1; 后续的节点处于等待状态，当前节点完成之后去通知后面的节点
 *               static final int CONDITION = -2; 等待状态，处于等待队列里面
 *               static final int PROPAGATE = -3; 共享状态，表示状态要往后面传播(共享锁)
 *               0                                初始状态
 *            thread：获取锁失败的线程，会被包装成node对象，存在thread字段中
 *            nextWaiter：等待队列中，下一个等待的节点(线程)
 *            竞争失败：节点加入到同步队列的尾巴上去
 *            一个线程获得锁的过程：通过队列的首节点去获得锁，首节点进行变化
 *          独占式获取锁：
 *              获取同步状态：
 *                  成功：直接退出返回
 *                  获取失败：生成节点->加入同步队列的尾部->CAS设置(前驱是否为头节点，是->获取同步状态[自旋的获取],不是->线程进入等待状态)
 *              if (!tryAcquire(arg) && acquireQueued(addWaiter(Node.EXCLUSIVE), arg))
 *                  addWaiter(Node mode)：将当前线程打包成一个节点，加入队列 -> enq(final Node node)：自旋设置
 *              release(int arg)--> LockSupport.unpark(s.thread);唤醒
 *          共享式：
 *              tryAcquireShared(arg) < 0
 *          Condition：一个Condition包含一个等待队列，一个锁对象，可以有很多个Condition对象。
 *              await():从同步队列将此节点移到等待队列中
 *              signalAll/signal：将节点从等待队列移到同步队列
 *          锁的可重入：ReentrantLock
 *                  获取
 *                final boolean nonfairTryAcquire(int acquires) {
 *                  final Thread current = Thread.currentThread();
 *                  int c = getState();
 *                  if (c == 0) {
 *                      if (compareAndSetState(0, acquires)) {
 *                          setExclusiveOwnerThread(current);
 *                          return true;
 *                      }
 *                  }
 *                  else if (current == getExclusiveOwnerThread()) {
 *                      int nextc = c + acquires;
 *                      if (nextc < 0) // overflow
 *                          throw new Error("Maximum lock count exceeded");
 *                      setState(nextc);
 *                      return true;
 *                  }
 *                  return false;
 *              }
 *
 *              释放：
 *
 *            protected final boolean tryRelease(int releases) {
 *             int c = getState() - releases;
 *             if (Thread.currentThread() != getExclusiveOwnerThread())
 *                 throw new IllegalMonitorStateException();
 *             boolean free = false;
 *             if (c == 0) {
 *                 free = true;
 *                 setExclusiveOwnerThread(null);
 *             }
 *             setState(c);
 *             return free;
 *         }
 *
 *
 *          公平和非公平的区别：
 *              !hasQueuedPredecessors()控制的
 *          读写锁：ReentrantReadWriteLock
 *              低16位记录写锁，支持可重入，独占式
 *              高16位记录读写，支持可重入，共享式，可重入机制由ThreadLocal<HoldCounter>负责
 *
 *              降级机制：支持写锁转换为读锁，但是不支持读锁转换为写锁
 *
 *
 *
 **
 *
 */
public class AllTest1 {
}
