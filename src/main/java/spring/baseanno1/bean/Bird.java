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
