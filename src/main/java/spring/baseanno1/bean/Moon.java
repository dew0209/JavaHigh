package spring.baseanno1.bean;

import org.springframework.stereotype.Component;

@Component
public class Moon {
    public Moon(){
        System.out.println("Moon 构造函数");
    }
    public void init(){
        System.out.println("Moon init");
    }
    public void destroy(){
        System.out.println("Moon destroy");
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
