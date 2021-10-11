package spring.baseanno.bean;


public class Bike {
    //构造方法
    public Bike() {
        System.out.println("Bike constructor");
    }
    //初始化方法
    public void init(){
        System.out.println("Bike init");
    }
    //销毁方法
    public void destroy(){
        System.out.println("Bike destroy");
    }
}
