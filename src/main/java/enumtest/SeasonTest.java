package enumtest;

/**
 * 一：枚举类的使用
 * 1.枚举类的理解：类的对象只有有限个，确定的。我们称此类为枚举类
 * 2.当需要定义一组常量时，强烈建议使用枚举类
 * 3.如果枚举类中只有一个对象，则可以作为单例模式的实现方法
 * 二：如何定义枚举类
 * 方式一：jdk5.0之前，自定义枚举类
 * 方式二：jdk5.0之后，可以使用enum关键字定义枚举类
 * 三：enum类中常用方法
 * value()方法：返回枚举类型的对象数组。该方法可以很方便地遍历所有的枚举值。
 * valueOf(String str)：可以把一个字符串转为对应的枚举类对象。要求字符串必须是枚举类对象的“名字”。如不是，会有运行时异常：IllegalArgumentException。
 * toString()：返回当前枚举类对象常量的名称
 * 四：使用enum关键字定义的枚举类实现接口的情况
 * 情况一：实现接口，在enum类中实现抽象方法
 * 情况二：让枚举类的对象分别实现接口中的抽象方法
 */
public class SeasonTest {
    public static void main(String[] args) {
        //自定义枚举类
        Season spring = Season.SPRING;
        System.out.println(spring);
        //使用enum关键字
        Season1 spring1 = Season1.SPRING;
        System.out.println(spring1);
        //values():返回所有的枚举类对象构成的数组
        Season1[] values = Season1.values();
        for (Season1 value : values) {
            System.out.println(value);
            value.show();
        }
        //valueOf(String objName):返回枚举类中对象名是objName的对象。
        //如果没有objName的枚举类对象，则抛异常：IllegalArgumentException
        Season1 spring2 = Season1.valueOf("SPRING");
        System.out.println(spring2);
    }
}

interface info{
    void show();
}

//自定义枚举类
class Season{
    //1.声明Season对象的属性：private final 修饰
    private final String seasonName;
    private final String seasonDesc;

    public Season(String seasonName, String seasonDesc) {
        this.seasonName = seasonName;
        this.seasonDesc = seasonDesc;
    }
    //3.提供当前枚举类的多个对象：public static final的
    public static final Season SPRING = new Season("春天","春暖花开");
    public static final Season SUMMER = new Season("夏天","夏日炎炎");
    public static final Season AUTUMN = new Season("秋天","秋高气爽");
    public static final Season WINTER = new Season("冬天","冰天雪地");
    //4.其他诉求1：获取枚举类对象的属性
    public String getSeasonName() {
        return seasonName;
    }

    public String getSeasonDesc() {
        return seasonDesc;
    }
    //4.其他诉求1：提供toString()
    @Override
    public String toString() {
        return "Season{" +
                "seasonName='" + seasonName + '\'' +
                ", seasonDesc='" + seasonDesc + '\'' +
                '}';
    }
}

enum Season1 implements info{
    SPRING("春天","春暖花开"){
        @Override
        public void show() {
            System.out.println(111);
        }
    },
    SUMMER("夏天","夏日炎炎"){
        @Override
        public void show() {
            System.out.println(222);
        }
    },
    AUTUMN("秋天","秋高气爽"){
        @Override
        public void show() {
            System.out.println(333);
        }
    },
    WINTER("冬天","冰天雪地"){
        @Override
        public void show() {
            System.out.println(444);
        }
    };

    //2.声明Season对象的属性:private final修饰
    private final String seasonName;
    private final String seasonDesc;

    //2.私有化类的构造器,并给对象属性赋值

    private Season1(String seasonName,String seasonDesc){
        this.seasonName = seasonName;
        this.seasonDesc = seasonDesc;
    }

    //4.其他诉求1：获取枚举类对象的属性
    public String getSeasonName() {
        return seasonName;
    }

    public String getSeasonDesc() {
        return seasonDesc;
    }

    @Override
    public String toString() {
        return "Season1{" +
                "seasonName='" + seasonName + '\'' +
                ", seasonDesc='" + seasonDesc + '\'' +
                '}';
    }
}