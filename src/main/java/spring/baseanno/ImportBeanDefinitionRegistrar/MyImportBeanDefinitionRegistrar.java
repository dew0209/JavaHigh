package spring.baseanno.ImportBeanDefinitionRegistrar;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import spring.baseanno.bean.Pig;

public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    /**
     *  把所有需要添加到容器的bean加入
     * @param annotationMetadata 当前类的注解信息
     * @param beanDefinitionRegistry beanDefinition注册类
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        boolean b1 = beanDefinitionRegistry.containsBeanDefinition("spring.baseanno.bean.Cat");
        boolean b2 = beanDefinitionRegistry.containsBeanDefinition("spring.baseanno.bean.Dog");
        //对于要注册的bean，要进行封装
        if (b1 && b2){
            RootBeanDefinition beanDefinition = new RootBeanDefinition(Pig.class);
            //bean的id不一定要全限定类名
            beanDefinitionRegistry.registerBeanDefinition("spring.baseanno.bean.Pig", beanDefinition);
        }
    }
}
