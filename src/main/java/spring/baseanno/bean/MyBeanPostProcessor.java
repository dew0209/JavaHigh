package spring.baseanno.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class MyBeanPostProcessor implements BeanPostProcessor {
    //后置处理
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        //返回一个对象
        //在初始化方法调用之前进行后置处理工作
        //在init-method=init之前调用
        System.out.println("postProcessAfterInitialization：" + beanName + " , " + bean);
        return bean;
    }
    //前置处理
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessBeforeInitialization：" + beanName + " , " + bean);
        return bean;
    }
}
