package thread.ext.safe;

/**
 *
 */
//双重检查机制
public class OneObj {
    private static volatile OneObj obj;//volatile禁止指令重排即可解决
    private OneObj(){}
    public static OneObj getInstance(){
        if(obj == null){
            synchronized (OneObj.class){
                if(obj == null){//对象的引用有了，但是域不一定完成初始化了
                    obj = new OneObj();//构造方法不是一个线程安全的，
                }
            }
        }
        return obj;
    }
}

//饿汉式
class OneObj1{
    private static OneObj1 obj = new OneObj1();//类的加载是安全的，因为虚拟机会再类加载的时候保证只加载一次
    private OneObj1(){

    }
}

//懒汉式，延迟占位模式
class OneObj2{

    private OneObj2(){

    }
    private static class Instance{
        public static OneObj2 obj = new OneObj2();
    }
    public static OneObj2 getInstance(){
        return Instance.obj;
    }
}