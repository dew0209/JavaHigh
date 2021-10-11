package spring.baseanno.bean;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component("jeep")
public class Jeep {
    public Jeep(){
        System.out.println("jeep 构造函数");
    }
    @PostConstruct
    public void init(){
        System.out.println("jeep 初始化");
    }
    @PreDestroy
    public void destroy(){
        System.out.println("jeep 销毁");
    }
}
