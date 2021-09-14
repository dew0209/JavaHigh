package thread.base.waitnotify;



public class CommunicationTest {
    public static void main(String[] args) throws InterruptedException {
        Number num = new Number();
        Thread t1 = new Thread(num);
        Thread t2 = new Thread(num);
        t1.setName("线程1");
        t2.setName("线程2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}

class Number implements Runnable{
    private int number = 1;
    private Object obj = new Object();
    @Override
    public void run() {
        while (true){
            synchronized (obj){
                obj.notify();
                if (number <= 100){
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "--->" + number++);
                    try {
                        obj.wait();
                        System.out.println(Thread.currentThread().getName() + "--->");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else break;
            }
        }
    }
}