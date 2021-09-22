package reflection.mybatis_demo;

import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapper;
import reflection.pojo.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class test {
    public static void main(String[] args) {
        //反射工具类初始化
        DefaultObjectFactory objectFactory = new DefaultObjectFactory();
        User user = objectFactory.create(User.class);
        DefaultObjectWrapperFactory wrapperFactory = new DefaultObjectWrapperFactory();
        DefaultReflectorFactory reflectorFactory = new DefaultReflectorFactory();
        //统一的接口
        MetaObject metaObject = MetaObject.forObject(user, objectFactory, wrapperFactory, reflectorFactory);

        //模拟数据库执行转化成对象
        Map<String,Object> dbRes = new HashMap<>();
        dbRes.put("name","李四");
        dbRes.put("age",12);
        //模拟映射关系
        Map<String,String> mapper = new HashMap<>();//字段--值
        mapper.put("name","name");
        mapper.put("age","age");
        //使用反射工具类将行数据转换成pojo
        ObjectWrapper objectWrapper = metaObject.getObjectWrapper();
        Set<Map.Entry<String, String>> entries = mapper.entrySet();//属性--字段
        for (Map.Entry<String, String> entry : entries) {
            String propName = entry.getKey();//属性
            Object propValue = dbRes.get(entry.getValue());//数据库字段对应的值
            PropertyTokenizer propertyTokenizer = new PropertyTokenizer(propName);
            objectWrapper.set(propertyTokenizer,propValue);
        }
        System.out.println(metaObject.getOriginalObject());
    }
}
