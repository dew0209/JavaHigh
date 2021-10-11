package spring.baseanno.bean;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("train")
//@Scope("prototype")
public class Train implements InitializingBean, DisposableBean {

    public Train(){
        System.out.println("Train构造函数");
    }
    //this is destroy of this bean
    @Override
    public void destroy() throws Exception {
        System.out.println("Train销毁");
    }
    //this is init of this bean
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("Train初始化");
    }
}
