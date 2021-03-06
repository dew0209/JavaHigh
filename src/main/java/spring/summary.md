# 组件注册

```xml
Spring 是一种开源轻量级框架，是为了解决企业应用程序开发复杂性而创建的
Spring致力于解决Java EE的各层解决方案，而不仅仅于某一层的方案

spring的目标：
	1.让现有的技术更容易使用
	2.促进良好的编程习惯
```

## spring体系结构

```xml
spring core:spring核心，它是框架的最基础的部分，提供IOC和依赖注入特性
spring context:上下文容器，它是BeanFactory功能加强的一个子接口
spring web:它提供web应用开发的支持
spring mvc:它针对web应用中mvc思想的实现
spring dao:它针对jdbc抽象层，简化了jdbc编码，同时，编码更具有健壮性
spring orm:它支持用于流行的orm框架的整合
spring aop:面向切面编程
```

## ComponentScan

```xml
@ComponentScan(value = "spring.baseanno")
扫描指定包下类的组件

includeFilters：包含指定

excludeFilters：排除指定
```

```java
包含指定，需要将useDefaultFilters设置为false即可正常使用，只注册带Controller注解的
@ComponentScan(value = "spring.baseanno",includeFilters = {
        @Filter(type = FilterType.ANNOTATION,classes = {Controller.class})
},useDefaultFilters = false)
  
排除指定，需要将useDefaultFilters设置为true(默认就是true)即可正常使用，排除带Controller注解的
@ComponentScan(value = "spring.baseanno",excludeFilters = {
        @Filter(type = FilterType.ANNOTATION,classes = {Controller.class})
},useDefaultFilters = true)
  

FilterType可以使用以下类型
ANNOTATION,
ASSIGNABLE_TYPE,
ASPECTJ,
REGEX,
CUSTOM;

按类型注册，排除OrderController类
@ComponentScan(value = "spring.baseanno",excludeFilters = {
        @Filter(type = FilterType.ASSIGNABLE_TYPE,classes = {OrderController.class})
},useDefaultFilters = true)

自定义类型
@ComponentScan(value = "spring.baseanno",includeFilters = {
        @Filter(type = FilterType.CUSTOM,classes = {MyType.class})
},useDefaultFilters = false)
  
useDefaultFilters = true
有组件配置相关注解的，会启用默认的配置，没有则启用自定义或者规定的
  
ComponentScans:配置多个扫描规则，满足其中之一就行了，是 || 的关系
@ComponentScans({
        @ComponentScan(value = "spring.baseanno",includeFilters = {
                @Filter(type = FilterType.CUSTOM,classes = {MyType.class})
        },useDefaultFilters = false),
        @ComponentScan(value = "spring.baseanno",includeFilters = {
                @Filter(type = FilterType.ANNOTATION,classes = {Controller.class})
        },useDefaultFilters = false)
})
```

![](img/1.png)

修改为

```java
package spring.filtertype;

import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;

/**
 * 自定义过滤
 * metadataReader：注解的元数据,读取到当前正在扫描的类信息
 * metadataReaderFactory：可以获取到其他任何类的信息
 *
 */
public class MyType implements TypeFilter {

    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();//获取当前类注解信息
        ClassMetadata classMetadata = metadataReader.getClassMetadata();//获取当前正在扫描的类的信息
        Resource resource = metadataReader.getResource();//获取当前类资源
        String className = classMetadata.getClassName();
        /**
         * 对spring内置的bean的注册是不起作用的
         * --->spring.baseanno.controller.OrderController
         * --->spring.baseanno.dao.OrderDao
         * --->spring.baseanno.service.OrderService
         * --->spring.baseanno.TestAll
         */
        System.out.println("--->" + className);
        if (className.contains("er")){
            System.out.println("添加-->" + className);
            return true;
        }
        return false;
    }
}
```

结果为：

```xml
--->spring.baseanno.controller.OrderController
添加-->spring.baseanno.controller.OrderController
--->spring.baseanno.dao.OrderDao
添加-->spring.baseanno.dao.OrderDao
--->spring.baseanno.service.OrderService
添加-->spring.baseanno.service.OrderService
--->spring.baseanno.TestAll
org.springframework.context.annotation.internalConfigurationAnnotationProcessor   org.springframework.context.annotation.ConfigurationClassPostProcessor@323b36e0
org.springframework.context.annotation.internalAutowiredAnnotationProcessor   org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor@44ebcd03
org.springframework.context.annotation.internalRequiredAnnotationProcessor   org.springframework.beans.factory.annotation.RequiredAnnotationBeanPostProcessor@694abbdc
org.springframework.context.annotation.internalCommonAnnotationProcessor   org.springframework.context.annotation.CommonAnnotationBeanPostProcessor@2e005c4b
org.springframework.context.event.internalEventListenerProcessor   org.springframework.context.event.EventListenerMethodProcessor@4567f35d
org.springframework.context.event.internalEventListenerFactory   org.springframework.context.event.DefaultEventListenerFactory@5ffead27
confg   spring.baseanno.confg.Confg$$EnhancerBySpringCGLIB$$2ca232a0@6356695f
orderController   spring.baseanno.controller.OrderController@4f18837a
orderDao   spring.baseanno.dao.OrderDao@359f7cdf
orderService   spring.baseanno.service.OrderService@1fa268de
person   Person{name='null', age=null}
```

将其自定义规则修改为

```java
className.contains("Dao")
```

将配置类上的扫描改为

```java
@ComponentScans({
        @ComponentScan(value = "spring.baseanno",includeFilters = {
                @Filter(type = FilterType.CUSTOM,classes = {MyType.class})
        },useDefaultFilters = false),
        @ComponentScan(value = "spring.baseanno",includeFilters = {
                @Filter(type = FilterType.ANNOTATION,classes = {Controller.class})
        },useDefaultFilters = false)
})
```

得到结果为

```xml
--->spring.baseanno.controller.OrderController
--->spring.baseanno.dao.OrderDao
添加-->spring.baseanno.dao.OrderDao
--->spring.baseanno.service.OrderService
--->spring.baseanno.TestAll
org.springframework.context.annotation.internalConfigurationAnnotationProcessor   org.springframework.context.annotation.ConfigurationClassPostProcessor@5af3afd9
org.springframework.context.annotation.internalAutowiredAnnotationProcessor   org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor@323b36e0
org.springframework.context.annotation.internalRequiredAnnotationProcessor   org.springframework.beans.factory.annotation.RequiredAnnotationBeanPostProcessor@44ebcd03
org.springframework.context.annotation.internalCommonAnnotationProcessor   org.springframework.context.annotation.CommonAnnotationBeanPostProcessor@694abbdc
org.springframework.context.event.internalEventListenerProcessor   org.springframework.context.event.EventListenerMethodProcessor@2e005c4b
org.springframework.context.event.internalEventListenerFactory   org.springframework.context.event.DefaultEventListenerFactory@4567f35d
confg   spring.baseanno.confg.Confg$$EnhancerBySpringCGLIB$$bf42c3b@5ffead27
orderDao   spring.baseanno.dao.OrderDao@6356695f
orderController   spring.baseanno.controller.OrderController@4f18837a
person   Person{name='null', age=null}
```

具体见相关代码



## Scope

```xml
IOC容器：多实例bean，在使用的时候才会创建这个bean
				单实例bean，创建IOC容器的时候，这个bean就被创建
```

```java
/**
 * prototype:多实例 IOC容器启动的时候，不会去调用方法创建对象，而是每次获取的时候才会调用方法创建对象
 * singleton：单实例，默认的 IOC容器启动的时候会调用方法创建对象并放到IOC容器中，以后每次获取就是直接从容器中拿的同一个对象
 * request：主要针对web应用，递交一次请求，创建一次实例
 * session：同一个session，创建一个实例
 * @return
 */
@Bean
@Scope(value = "prototype")
public Person person1(){
    return new Person();
}
```



## Lazy

```xml
什么是懒加载
	针对单实例bean，容器启动时候不创建对象，仅当第一次使用(获取)bean的时候才创建被初始化
```

```java
@Bean
@Lazy
public Person person2(){
    return new Person();
}
```



## Conditional

条件注册bean

```java
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
}
```

```java
/**
 * 运行操作系统的时候，如果windows--->lison
 *                     linux--->james
 * @Conditional
 *
 *
 * FactoryBean：把Java实例通过FactoryBean注入到容器中
 * BeanFactory：从我们的容器中获取实例化后的bean
 */
@Test
public void test05(){
    AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext(Confg.class);
    System.out.println("IOC容器创建完成");
}
```

```java
package spring.baseanno.filter;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class WinConditional implements Condition {

    /**
     *
     * @param conditionContext 判断条件能够使用的上下文环境
     * @param annotatedTypeMetadata 注解的信息
     * @return
     */
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        //TODO 是否为windows系统
        //获取到IOC容器正在使用的beanFactory
        ConfigurableListableBeanFactory factory = conditionContext.getBeanFactory();
        //获取当前环境变量
        Environment environment = conditionContext.getEnvironment();
        String os = environment.getProperty("os.name");
        if(os.contains("Windows"))return true;
        return false;
    }
}
```

```java
package spring.baseanno.filter;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class LinConditional implements Condition {

    /**
     *
     * @param conditionContext 判断条件能够使用的上下文环境
     * @param annotatedTypeMetadata 注解的信息
     * @return
     */
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        //TODO 是否为Linux系统
        //获取到IOC容器正在使用的beanFactory
        ConfigurableListableBeanFactory factory = conditionContext.getBeanFactory();
        //获取当前环境变量
        Environment environment = conditionContext.getEnvironment();
        String os = environment.getProperty("os.name");
        if(os.contains("linux"))return true;
        return false;
    }
}
```



## Import

```
@Import：快速给容器导入一个组件，注意：@Bean有点简单
  a.@Import(要导入到容器中的组件)：容器会自动注册这个组件，bean的id为全限定类名
  b.ImportSelector：是一个接口，返回需要导入到容器的组件的全类名数组
  c.ImportBeanDefinitionRegistrar 可以手动添加组件到IOC容器，所有的Bean的注册可以使用BeanDefinitionRegistry
  	参考MyImportBeanDefinitionRegistrar类
```



## FactoryBean

使用FactoryBean进行对象的注册

```java
package spring.baseanno.FactoryBean;

import org.springframework.beans.factory.FactoryBean;
import spring.baseanno.bean.Mokey;

/**
 * 这种思想是mybatis整合spring的实现方式，因为mapper接口动态代理对象不是线程安全的，所以每次拿都是新的对象
 */
public class MyFactoryBean implements FactoryBean<Mokey> {
    @Override
    public Mokey getObject() throws Exception {
        return new Mokey();
    }

    @Override
    public Class<?> getObjectType() {
        return Mokey.class;
    }

    /**
     * 是否单实例
     * @return true 是单例模式，false 多例模式
     */
    @Override
    public boolean isSingleton() {
        return true;
    }
}
```

```java
@Bean
public MyFactoryBean myFactoryBean(){
    return new MyFactoryBean();
}
```

 

## Bean的生命周期1

```xml
Bean的生命周期指Bean的创建->初始化->销毁
我们可以自定义Bean初始化和销毁方法
容器在bean进行到当前生命周期的时候，来调用自定义的初始化和销毁方法
```

单实例

```java
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
```

```java
@Bean(initMethod = "init",destroyMethod = "destroy")
public Bike bike(){
    return new Bike();
}
```

多实例bean在调用的时候创建，销毁不由IOC容器负责

## Bean的生命周期2

```xml
实现InitializingBean接口的afterPropertiesSet方法，当创建对象，且把bean所有的属性设置好之后，会调用这个方法，相当于初始化方法
实现DisposableBean接口的destroy方法，当bean销毁时，会把单实例bean进行销毁
```

```java
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
```

## Bean的生命周期3

可以使用JSR250规范定义的(Java规范）两个注解来实现

```xml
@PostConstruct：在Bean创建完成，且属于赋值完成后进行初始化，属于jdk规范的注解
@PreDestroy：在Bean被移除之前进行通知，在容器销毁之前进行清理工作
```

## Bean的生命周期4

```xml
bean的后置处理器，在bean初始化之前调用进行拦截，在bean初始化之前后进行一些处理工作，使用BeanPostProcessors控制Bean的生命周期，紧跟初始化，看原理的代码
实现BeanPostProcessors接口即可
对所有的bean都其作用
在init前后进行拦截
postProcessBeforeInitialization：bike , spring.baseanno.bean.Bike@4fb0f2b9
Bike init
postProcessAfterInitialization：bike , spring.baseanno.bean.Bike@4fb0f2b9
```

原理：

```java
if (mbd == null || !mbd.isSynthetic()) {
    wrappedBean = this.applyBeanPostProcessorsBeforeInitialization(bean, beanName);
}

try {
    this.invokeInitMethods(beanName, wrappedBean, mbd);//初始化之后
} catch (Throwable var6) {
    throw new BeanCreationException(mbd != null ? mbd.getResourceDescription() : null, beanName, "Invocation of init method failed", var6);
}

if (mbd == null || !mbd.isSynthetic()) {
    wrappedBean = this.applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
}
```

### BeanPostProcessors调试

- ```java
  AnnotationConfigApplicationContext-->AnnotationConfigApplicationContext(Class<?>... annotatedClasses)
  ```

- ```java
  AbstractApplicationContext-->refresh()
  ```

- ```java
  DefaultListableBeanFactory-->finishBeanFactoryInitialization()
  ```

- ```java
  AbstractBeanFactory-->getBean(String name)
  ```

- ```java
  AbstractBeanFactory-->doGetBean(String name, @Nullable Class<T> requiredType, @Nullable Object[] args, boolean typeCheckOnly)
  ```

- ```java
  AbstractAutowireCapableBeanFactory-->createBean(String beanName, RootBeanDefinition mbd, @Nullable Object[] args)
  ```

- ```java
  AbstractAutowireCapableBeanFactory-->doCreateBean(String beanName, RootBeanDefinition mbd, @Nullable Object[] args)
  ```

- ```java
  AbstractAutowireCapableBeanFactory-->initializeBean(String beanName, Object bean, @Nullable RootBeanDefinition mbd) 在调用这个方法的前后进行了BeanPostProcessors的增强
  ```

### ApplicationContextAwareProcessor Aware接口的使用

```java
package spring.baseanno.bean;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class Plane implements ApplicationContextAware {
    ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        //将ApplicationContext传进来
        this.applicationContext = applicationContext;
    }
}
```

就是在

```java
applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName)-->invokeAwareInterfaces(bean);
```

做了相应的处理

```xml
总结：Spring底层对BeanPostProcessor的使用，包括bean的赋值，注入其它组件，生命周期注解等功能。
```

# 组件赋值

## Value

```xml
1.使用bean.xml配置文件进行赋值
2.使用@Value赋值，基本字符
3.使用@Value从*.properties取值
```

```java
package spring.baseanno1.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("bird")
public class Bird {
    /**
     * 可以使用@Value进行赋值，1：基本字符。2：Spring el表达式。3：
     */
    @Value("james")//使用基本字符
    private String name;
    @Value("#{20-2}")//使用el
    private Integer age;

    @Value("${bird.color}")
    private String color;

    public Bird(){

    }
    public Bird(String name, Integer age,String color) {
        this.name = name;
        this.age = age;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Bird{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", color='" + color + '\'' +
                '}';
    }
}
```

```java
package spring.baseanno1;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import spring.baseanno1.bean.Bird;
import spring.baseanno1.confg.Confg;

public class TestAll {
    @Test
    public void test01(){
        AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext(Confg.class);
        System.out.println("容器已经创建完成");
        Bird bird = (Bird)app.getBean("bird");
        System.out.println(bird);
        System.out.println("分割线");
        String[] names = app.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name + "---" + app.getBean(name));

        }
        ConfigurableEnvironment en = app.getEnvironment();
        System.out.println("en === " + en.getProperty("bird.color"));
    }
}
```

## Autowired Qualifier Primary

```xml
什么是自动装配：
	spring利用依赖注入（DI），完成对IOC容器中的各个组件的依赖关系赋值
思考：
	如果容器中存在两个id相同的bean，会加载哪个bean呢?报错
	如何指定装配组件ID进行加载?
	容器加载不存在的bean会出现什么问题?
	@Primary注解bean首选如何使用?
	@Autowired @Resource @Inject区别?
```

```java
package spring.baseanno1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import spring.baseanno1.dao.TestDao;

@Service
public class TestService {



    @Autowired
    @Qualifier("testDao2")//利用这个在多个类型一样的bean的时候，去按名字找
    private TestDao testDao;

    public void testDao(){
        System.out.println(testDao);
    }
}
```

```java
package spring.baseanno1.confg;

import org.springframework.context.annotation.*;
import org.springframework.stereotype.Component;
import spring.baseanno1.dao.TestDao;
import thread.ext.aqs.conditionex.Test;


@Configuration
@ComponentScans({
        @ComponentScan(value = "spring.baseanno1")
})
@PropertySource(value = "classpath:/bird.properties")
@Component
public class Confg {

    //两个id一样的话，@Bean更占优势
    @Bean("testDao")
    public TestDao testDao(){
        TestDao testDao = new TestDao();
        testDao.setFlag("2");
        return testDao;
    }

}
```

```xml
Resource效果是一样的
```

```xml
@Primary首选项，会优先加载这个。类型首选[有多个相同类型存在时候]
```

```xml
多个类型，单纯用Autowired，报错，可以通过Primary进行配置，或者使用Qualifier进行配置
```

## Aware注入Spring组件原理

```xml
自定义组件想要使用Spring容器底层的组件(ApplicationContext,BeanFactory,...)
自定义组件实现xxxAware，在创建对象的时候，会调用接口规定的方法注入到相关组件
```

## 常见的xxxAware接口

```xml
ApplicationContextAware接口：获取IOC容器
BeanNameAware：获取Bean信息
EmbeddedValueResolverAware接口：解析器（表达式及相关脚本解析）
```

# AOP



```java
package spring.aopcs.aop;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * 日志切面类的方法需要动态感知到div方法运行
 *  通知方法：
 *      前置通知：logStart():在我们执行div()除法之前运行 @Before
 *      后置通知：logEnd():在目标方法div()运行结束之后，不管有没有异常 @After
 *      返回通知：logReturn():在我们的目标方法div()正常返回值后运行 @AfterReturn
 *      异常通知：longException()：在我们的目标方法div()出现异常后运行 @AfterThrowing
 *      环绕通知：动态代理，需要手动执行joinPoint.procced() @Round
 */
@Aspect
public class LogAspects {

    @Pointcut("execution(public int spring.aopcs.aop.Calculator.div(int,int))")
    public void pointCut(){}


    //前置通知
    /*
    可以使用通配符
     */
    //JoinPoint可以获取一些有关方法的信息，不能使用ProceedingJoinPoint
    @Before("pointCut()")
    public void logStart(JoinPoint joinPoint){
        System.out.println("除法运行...参数列表是：{}");
        Object[] args = joinPoint.getArgs();
        System.out.println(Arrays.toString(args));
        System.out.println("111");
    }
    //后置通知
    @After("pointCut()")
    public void logEnd(){
        System.out.println("除法结束...参数列表是：{}");
    }
    //返回通知
    @AfterReturning(value = "pointCut()",returning = "result")
    public void logReturn(int result){
        System.out.println("除法正常返回...参数列表是：{}" + result);
    }
    //异常通知
    @AfterThrowing(value = "pointCut()",throwing = "exception")
    public void logException(Exception exception){
        System.out.println("除法异常...参数列表是：{}+++++++++++++++++++");
        System.out.println(exception);
    }
    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint){
        System.out.println("环绕之前");
        Object ret = null;
        try {

            ret = joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        System.out.println("环绕之后");
        return ret;
    }
}
```

```java
package spring.aopcs.confg;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import spring.aopcs.aop.Calculator;
import spring.aopcs.aop.LogAspects;

@Configuration
@EnableAspectJAutoProxy//this is must
public class Confg {
    @Bean("calculator")
    public Calculator calculator(){
        return new Calculator();
    }

    @Bean
    public LogAspects logAspects(){
        return new LogAspects();
    }
}
```

# AOP源码

JDK代理：InvocationHandler，Proxy，反射  ==实现接口==

CGLB：继承要被动态代理的类  ==继承==

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(AspectJAutoProxyRegistrar.class)
public @interface EnableAspectJAutoProxy {
  //动态代理采用的方式
  boolean proxyTargetClass() default false;
  //暴露代理对象
  boolean exposeProxy() default false;
}
```

```java
AnnotationAwareAspectJAutoProxyCreator.class + org.springframework.aop.config.internalAutoProxyCreator 注入到IOC通过ImportBeanDefinitionRegistrar的方式进行注册
  
AnnotationAwareAspectJAutoProxyCreator的父类
  AnnotationAwareAspectJAutoProxyCreator
  	AbstractAdvisorAutoProxyCreator
  		AbstractAutoProxyCreator
  			ProxyProcessorSupport(Ordered接口)
  			SmartInstantiationAwareBeanPostProcessor
  			BeanFactoryAware：能把我们的beanfactory传进来
InstantiationAwareBeanPostProcessor在我们的bean初始化完成前后要做的事情，自动装配BeanFactory

  
明确目标：拦截
需要做的：createBean()
  
关注AnnotationAwareAspectJAutoProxyCreator的父类怎么创建
internalAutoProxyCreator == AnnotationAwareAspectJAutoProxyCreator
  
  
// Register bean processors that intercept bean creation.
registerBeanPostProcessors(beanFactory);
在这里面创建的
  
最后利用
return BeanUtils.instantiateClass(constructorToUse, new Object[0]);
进行创建
```

# 声明式事务

```xml
以方法为单位，进行事物控制，抛出异常，事物回滚
最小执行单位为方法。决定执行成败是通过抛出异常来判断的，抛出异常即执行失败


InfrastructureAdvisorAutoProxyCreator
注册
利用后置处理器机制在创建以后，包装对象，返回一个代理对象（增强），代理对象执行方法时，利用拦截器进行调用

AnnotationTransactionAttributeSource
事物增强器要用事务注解的信息，使用这个类解析事务注解


TransactionInterceptor
保存事务属性信息，事务管理器 MethodInterceptor
当执行目标方法时：
	执行拦截器
	事务拦截器
	1.先获取事务相关属性
	2.拿到事务管理器，到容器中获取，final PlatformTransactionManager tm = determineTransactionManager(txAttr);
	2.执行目标方法retVal = invocation.proceedWithInvocation();
		有异常：completeTransactionAfterThrowing(txInfo, ex);回滚在这个方法里面
		正常：利用事务管理器，提交
TransactionAspectSupport进行拦截
completeTransactionAfterThrowing(@Nullable TransactionAspectSupport.TransactionInfo txInfo, Throwable ex) 进行回滚
```

