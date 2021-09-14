package thread.base;

import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 创建线程的方式三：实现Callable接口。--jdk5.0
 * 如何理解实现callable接口的方式创建多线程比实现Runnable接口创建多线程方式强大：
 *  可以有返回值
 *  可以抛出异常，被外面的操作捕获，获取异常的信息
 *  callable是支持泛型的
 */
public class ThreadNew {
    @Test
    public void test01() throws ExecutionException, InterruptedException {
        NumThread numThread = new NumThread();
        FutureTask futureTask = new FutureTask(numThread);
        new Thread(futureTask).start();
        Object o = futureTask.get();
        System.out.println("总和为：" + o);
    }
}

class NumThread implements Callable{
    @Override
    public Object call() throws Exception {
        int sum = 0;
        for(int i = 1;i <= 100;i++){
            if(i % 2 == 0){
                System.out.println(i);
                sum += i;
            }
        }
        return sum;
    }
}