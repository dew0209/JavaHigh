package thread.ext.aqs.example;

import java.util.Random;

/**
 * 读写锁和syn的比较：
 *      读写锁比synchronized在读多写少的情况下快的多
 */

public class App {
    private static final int readWriteRatio = 10;//读写线程的比例
    private static final int minthreadCount = 3;
    private static class GetThread implements Runnable{
        private GoodsService goodsService;

        public GetThread(GoodsService goodsService) {
            this.goodsService = goodsService;
        }

        @Override
        public void run() {
            long start = System.currentTimeMillis();
            for(int i = 0;i < 100;i++){
                goodsService.getNum();

            }
            System.out.println("读取商品耗时:" + (System.currentTimeMillis() - start));
        }
    }
    private static class SetThread implements Runnable{
        private GoodsService goodsService;

        public SetThread(GoodsService goodsService) {
            this.goodsService = goodsService;
        }

        @Override
        public void run() {
            long start = System.currentTimeMillis();
            Random r = new Random();

            for(int i = 0;i < 10;i++){
                try {
                    Thread.currentThread().sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                goodsService.setNum(r.nextInt(10));
            }
            System.out.println("写商品耗时:" + (System.currentTimeMillis() - start));
        }
    }

    public static void main(String[] args) throws InterruptedException {
        GoodsInfo cup = new GoodsInfo("Cup", 10000, 10000);
//        GoodsService goodsService = new UseSyn(cup);//synchronized
        GoodsService goodsService = new UseRwLock(cup);//读写锁
        for(int i = 0;i < minthreadCount;i++){
            Thread thread = new Thread(new SetThread(goodsService));
            for(int j = 0;j < readWriteRatio;j++){
                Thread getT = new Thread(new GetThread(goodsService));
                getT.start();
            }
            Thread.currentThread().sleep(100);
            thread.start();
        }
    }
}
