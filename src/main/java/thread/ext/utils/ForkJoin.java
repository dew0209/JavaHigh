package thread.ext.utils;

/*
    分治
        o
    o o  o o  分
    o o  o o  合
        o
      规模为N的问题，N < 阈值，直接解决，N > 阈值，分解
        子问题互相独立，与原问题形式相同，将子问题的解合并得到原问题的解
        工作密取：线程a空闲，线程b忙碌，并且线程b的task多，a就会取b的任务执行，把结果给b即可 充分利用线程的资源，不能让其空闲，压榨线程
    使用fork/join的标准范式
        pool = new ForkJoinPool();
        task = new 我们自己的任务();
        pool.invoke();
        result = task.join();
    使用fork/join不一定比单线程快，因为上下文切换需要时间，但是大数据量情况下要快一些。[并不是多线程一定要比单线程快，多线程的切换和创建也是需要时间的]
 */

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

public class ForkJoin {

}

//fork/join 在使用的时候，需要join().这很让人疑惑，其实在每个任务的时候，是否异步，取决于调用的是execute还是invoke
class MyTask1 extends RecursiveAction{
    private File file;

    public MyTask1(File file) {
        this.file = file;
    }

    public static void main(String[] args) throws InterruptedException {
        ForkJoinPool pool = new ForkJoinPool();
        MyTask1 task1 = new MyTask1(new File("D:/"));
        pool.execute(task1);//异步
        System.out.println("Running~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        Thread.sleep(1000);
        System.out.println("debug debug debug debug");
        task1.join();
        System.out.println("end");
    }

    @Override
    protected void compute() {
        List<MyTask1> coll = new ArrayList<>();
        //file当前目录所在位置
        File[] files = file.listFiles();
        if(files != null){
            for (File file1 : files) {
                if(file1.isDirectory()){
                    coll.add(new MyTask1(file1));
                }else if (file1.getAbsolutePath().endsWith(".txt")){
                    System.out.println("文件名：" + file1.getAbsolutePath());
                }
            }
        }
        if(!coll.isEmpty()){

            Collection<MyTask1> myTask1s = invokeAll(coll);
            for (MyTask1 myTask1 : myTask1s) {
                myTask1.join();
            }
        }
    }
}

/**
 * ForkJoinTask抽象类
 *      |---RecursiveTask   有返回值   [同步]
 *      |---RecursiveAction 没有返回值 [异步]
 */

//同步：计算数组的和
class MyTask extends RecursiveTask<Integer> {

    private final static int THRESHOLD = 10;
    private int[] src;
    private int fromIndex;
    private int toIndex;

    public MyTask(int[] src, int fromIndex, int toIndex) {
        this.src = src;
        this.fromIndex = fromIndex;
        this.toIndex = toIndex;
    }

    @Override
    protected Integer compute() {
        try {
            Thread.sleep(100);
            System.out.println("222");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(toIndex - fromIndex < THRESHOLD){
            int count = 0;
            for(int i = fromIndex;i <= toIndex;i++){
                count += src[i];
            }
            return count;
        }else {
            //fromIndex....mid....toIndex
            int mid = (fromIndex + toIndex) / 2;
            MyTask left = new MyTask(src,fromIndex,mid - 1);
            MyTask right = new MyTask(src,mid,toIndex);
            invokeAll(left,right);
            return left.join() + right.join();
        }

    }

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        int src[] = new int[100];
        for(int i = 0;i < 100;i++)src[i] = i + 1;
        MyTask task = new MyTask(src,0,src.length - 1);
        //异步还是同步，取决于所调用的方法，如果需要返回值，在join()的时候，都会被阻塞，所以，异步更像是一种nio模式。让主线程不再等待，去做其他的事情
        pool.invoke(task);
        System.out.println("11111");
        Integer join = task.join();
        System.out.println(join);
    }
}
