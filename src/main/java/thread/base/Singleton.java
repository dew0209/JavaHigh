package thread.base;

/**
 * 使用同步机制将单例模式中的懒汉式改写为线程安全的
 */
public class Singleton {
    private Singleton(){

    }
    private static Singleton singleton = null;

    private static Singleton getInstance(){
        if (singleton == null){
            synchronized (Singleton.class){
                if (singleton == null){
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }
}
