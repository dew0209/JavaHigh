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
