package reflection.base;

import org.junit.Test;

/**
 * 通过反射创建对应的运行时类的对象
 */
public class NewInstanceTest {
    @Test
    public void test01() throws IllegalAccessException, InstantiationException {
        Class<String> clazz = String.class;
        String s = clazz.newInstance();
        System.out.println(s);
    }
}
