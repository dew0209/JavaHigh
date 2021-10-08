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
