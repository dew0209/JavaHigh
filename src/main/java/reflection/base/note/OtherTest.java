package reflection.base.note;

import com.sun.xml.internal.ws.wsdl.writer.document.ParamType;
import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class OtherTest {
    /*
    获取类构造器结构
     */
    @Test
    public void test01(){
        Class<Person> clazz = Person.class;
        //getConstructors():获取当前运行时类中声明为public的构造器
        Constructor<?>[] constructors = clazz.getConstructors();
        for (Constructor<?> constructor : constructors) {
            System.out.println(constructor);
        }
        System.out.println();
        //getDeclaredConstructors():获取当前运行时类中声明的所有的构造器
        Constructor<?>[] constructors1 = clazz.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors1) {
            System.out.println(constructor);
        }
    }
    /*
    获取运行时类的父类
     */
    @Test
    public void test02(){
        Class<Person> aClass = Person.class;
        Class<? super Person> superclass = aClass.getSuperclass();
        System.out.println(superclass);
    }
    /*
    获取运行时类的带泛型的父类
     */
    @Test
    public void test03(){
        Class<Person> aClass = Person.class;
        Type genericSuperclass = aClass.getGenericSuperclass();
        System.out.println(genericSuperclass);
    }
    /*
    获取运行时类的带泛型的父类的泛型
     */
    @Test
    public void test04(){
        Class<Person> aClass = Person.class;
        ParameterizedType genericSuperclass = (ParameterizedType)aClass.getGenericSuperclass();
        System.out.println(genericSuperclass);
        Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
        System.out.println(actualTypeArguments[0].getTypeName());
    }
    /*
    获取运行时类实现的接口
     */
    @Test
    public void test05(){
        Class<Person> aClass = Person.class;
        Class<?>[] interfaces = aClass.getInterfaces();
        for (Class<?> anInterface : interfaces) {
            System.out.println(anInterface);
        }
        System.out.println();
        Class<?>[] interfaces1 = aClass.getSuperclass().getInterfaces();
        for (Class<?> aClass1 : interfaces1) {
            System.out.println(aClass1);
        }
    }
    /*
    获取运行时类所在的包
     */
    @Test
    public void test06(){
        Class<Person> aClass = Person.class;
        Package aPackage = aClass.getPackage();
        System.out.println(aPackage);
    }
    /*
    获取运行时类声明的注解
     */
    @Test
    public void test07(){
        Class<Person> aClass = Person.class;
        Annotation[] annotations = aClass.getAnnotations();
        for (Annotation annotation : annotations) {
            System.out.println(annotation);
        }
    }
}
