package spring.baseanno1.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Sun {
    private Moon moon;

    @Autowired
    public Sun(Moon moon){
        this.moon = moon;
        System.out.println("+++++++++++++++++");
    }
    /*
    失效了？？？？？？？
    public Sun(@Autowired Moon moon){
        this.moon = moon;
        System.out.println("+++++++++++++++++");
    }
     */
    public Sun(){
        System.out.println("Sun  构造函数");
    }

    public Moon getMoon() {
        return moon;
    }
    //@Autowired
    public void setMoon(Moon moon) {
        this.moon = moon;
    }

    @Override
    public String toString() {
        return "Sun{" +
                "moon=" + moon +
                '}';
    }
}
