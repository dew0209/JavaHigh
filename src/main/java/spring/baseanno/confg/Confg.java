package spring.baseanno.confg;

import org.springframework.context.annotation.*;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.stereotype.Controller;
import spring.baseanno.controller.OrderController;
import spring.filtertype.MyType;
import spring.quickstart.pojo.Person;

@Configuration
@ComponentScans({
        @ComponentScan(value = "spring.baseanno",includeFilters = {
                @Filter(type = FilterType.CUSTOM,classes = {MyType.class})
        },useDefaultFilters = false),
        @ComponentScan(value = "spring.baseanno",includeFilters = {
                @Filter(type = FilterType.ANNOTATION,classes = {Controller.class})
        },useDefaultFilters = false)
})
public class Confg {
    @Bean
    public Person person(){
        return new Person();
    }
}
