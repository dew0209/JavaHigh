package spring.baseanno.confg;

import org.hamcrest.Factory;
import org.junit.Test;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import spring.baseanno.FactoryBean.MyFactoryBean;
import spring.baseanno.ImportBeanDefinitionRegistrar.MyImportBeanDefinitionRegistrar;
import spring.baseanno.bean.Bike;
import spring.baseanno.bean.Cat;
import spring.baseanno.bean.Dog;
import spring.baseanno.controller.OrderController;
import spring.baseanno.filter.LinConditional;
import spring.baseanno.filter.WinConditional;
import spring.baseanno.selector.MyImportSelector;
import spring.filtertype.MyType;
import spring.quickstart.pojo.Person;

@Configuration
@ComponentScans({
        @ComponentScan(value = "spring.baseanno",includeFilters = {
                @Filter(type = FilterType.CUSTOM,classes = {MyType.class})
        },useDefaultFilters = false),
        @ComponentScan(value = "spring.baseanno",includeFilters = {
                @Filter(type = FilterType.ANNOTATION,classes = {Controller.class})
        },useDefaultFilters = false),
        @ComponentScan(value = "spring.baseanno",includeFilters = {
                @Filter(type = FilterType.ANNOTATION,classes = {Component.class})
        },useDefaultFilters = false)
})
@Import({Dog.class, Cat.class, MyImportSelector.class, MyImportBeanDefinitionRegistrar.class})
public class Confg {
    /*//给容器中注册一个bean，类型为返回值类型，默认是单例
    @Bean
    public Person person(){
        return new Person();
    }

    *//**
     * prototype:多实例 IOC容器启动的时候，不会去调用方法创建对象，而是每次获取的时候才会调用方法创建对象
     * singleton：单实例，默认的 IOC容器启动的时候会调用方法创建对象并放到IOC容器中，以后每次获取就是直接从容器中拿的同一个对象
     * request：主要针对web应用，递交一次请求，创建一次实例
     * session：同一个session，创建一个实例
     * @return
     *//*
    @Bean
    @Scope(value = "prototype")
    public Person person1(){
        return new Person();
    }*/

   /* *//**
     * 针对单实例bean，容器启动时候不创建对象，仅当第一次使用(获取)bean的时候才创建被初始化
     * @return
     *//*
    @Bean
    @Lazy
    public Person person2(){
        return new Person();
    }*/
    /*@Bean("person")
    public Person person3(){
        System.out.println("给容器中添加person");
        return new Person("person",20);
    }
    @Conditional(WinConditional.class)
    @Bean("lison")
    public Person lison(){
        System.out.println("给容器中添加lison...");
        return new Person("lison",58);
    }
    @Conditional(LinConditional.class)
    @Bean("james")
    public Person james(){
        System.out.println("给容器中添加james...");
        return new Person("james",20);
    }*/

    /**
     * 1.@Bean：导入第三方的类或者包的组件，比如Person为第三方的类，需要在IOC中使用
     * 2.包扫描+组件的标注注解(@ComponentScan @Controller @Repository @Service @Component)：一般针对自己写的类
     * 3.@Import：快速给容器导入一个组件，注意：@Bean有点简单
     *              a.@Import(要导入到容器中的组件)：容器会自动注册这个组件，bean的id为全限定类名
     *              b.ImportSelector：是一个接口，返回需要导入到容器的组件的全类名数组
     *              c.ImportBeanDefinitionRegistrar 可以手动添加组件到IOC容器，所有的Bean的注册可以使用BeanDefinitionRegistry
     *                  参考MyImportBeanDefinitionRegistrar类
     * 4.使用FactoryBean进行对象的注册：
     * @return
     */
    @Bean(initMethod = "init",destroyMethod = "destroy")
    //@Scope("prototype")
    public Bike bike(){
        return new Bike();
    }
    @Bean("person")
    public Person person3(){
        System.out.println("给容器中添加person");
        return new Person("person",20);
    }/*
    @Bean
    public MyFactoryBean myFactoryBean(){
        return new MyFactoryBean();
    }*/
}
