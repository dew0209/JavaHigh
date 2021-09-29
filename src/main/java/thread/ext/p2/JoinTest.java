package thread.ext.p2;

public class JoinTest {
    static class JumpQueue implements Runnable{
        private Thread thread;

        public JumpQueue(Thread thread) {
            this.thread = thread;
        }

        @Override
        public void run() {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " terminted");
        }
    }

    public static void main(String[] args) {
        Thread previous = Thread.currentThread();
        for(int i = 0;i < 10;i++){
            Thread thread = new Thread(new JumpQueue(previous),String.valueOf(i));
            System.out.println(previous.getName() + "jump --> " + thread.getName());
            previous = thread;
            thread.start();
        }
        try {
            Thread.sleep(2000);//其他线程也会被等待，因为join的存在
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " 结束");
    }
}
