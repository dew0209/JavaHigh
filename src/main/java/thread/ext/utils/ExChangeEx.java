package thread.ext.utils;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Exchanger;

/**
 * 两个线程间的数据交换
 */
public class ExChangeEx {
    private static final Exchanger<Set<String>> exchange = new Exchanger<>();
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Set<String> setA = new HashSet<>();
                setA.add("A111");
                setA.add("A222");
                try {
                    setA = exchange.exchange(setA);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("AAA1111");
                System.out.println("AAA");
                for (String s : setA) {
                    System.out.println(s);
                }
            }
        }).start();
        System.out.println("=========");
        new Thread(new Runnable() {
            @Override
            public void run() {
                Set<String> setB = new HashSet<>();
                setB.add("B111");
                setB.add("B222");
                try {
                    setB = exchange.exchange(setB);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }
}
