package date;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;

/**
 * jdk 8 之前日期和时间的api测试
 */
public class DateTimeTest {
    /*
    java.util.Date类
        |---java.sql.Date类
    1.两个构造器的使用：
        构造器1：Date():创建一个对应当前时间的Date对象
        构造器2：创建指定毫秒数的Date对象
    2.两个方法的使用
        toString()：显示当前的年，月，日
        getTime()：获取当前Date对象对应的毫秒数。[时间戳]
    3.java.sql.Date对应着数据库中的日期类型的变量
        如何实例化
        如何将java.util.Date对象转换为java.sql.Date对象
     */
    @Test
    public void test01(){
        Date date1 = new Date();
        System.out.println(date1.toString());

        System.out.println(date1.getTime());

        Date date2 = new Date(155030620410L);
        System.out.println(date2.toString());

        java.sql.Date date3 = new java.sql.Date(35235325345L);
        System.out.println(date3);

        //如何将java.util.Date对象转换为java.sql.Date对象
        //情况一：
        Date date4 = new java.sql.Date(2343243242323L);
        java.sql.Date date5 = (java.sql.Date) date4;
        System.out.println(date5);
        //情况二：
        Date date6 = new Date();
        java.sql.Date date7 = new java.sql.Date(date6.getTime());
        System.out.println(date7);
    }
    //System类中的currentTimeMillis()
    @Test
    public void test02(){
        //返回当前时间与1970.1.1.0.0.0之间以毫秒为单位的时间差
        long time = System.currentTimeMillis();
        System.out.println(time);
    }
    /*
    SimpleDateFormat的使用：SimpleDateFormat对日期Date类的格式化和解析
    1.两个操作
    1.1 格式化：日期-->字符串
    1.2 解析：格式化的逆过程，字符串-->日期
    2.SimpleDateFormat的实例化
     */
    @Test
    public void test03() throws ParseException {
        //实例化SimpleDateFormat：使用默认的构造器
        SimpleDateFormat sdf = new SimpleDateFormat();
        //格式化：日期-->字符串
        Date date = new Date();
        System.out.println(date);
        String format = sdf.format(date);
        System.out.println(format);

        //解析：格式化的逆过程，字符串-->日期
        String str = "21-9-13 下午6:31";
        Date date1 = sdf.parse(str);
        System.out.println(date1);//Mon Sep 13 18:31:00 CST 2021

        /*************按照指定的方式格式化和解析：调用带参的构造器*****************/
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String format1 = sdf1.format(date);
        System.out.println(format1);
        Date parse = sdf1.parse("2021-09-13 06:34:04");
        System.out.println(parse);//Mon Sep 13 06:34:04 CST 2021
    }
    /*
    Calendar 日历类(抽象类)的使用
     */
    @Test
    public void test04(){
        //实例化方式1：创建其子类（GregorianCalendar）的对象
        //实例化方式2：调用其静态方法getInstance()
        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar);//GregorianCalendar
        //2.常用方法
        //get()
        int days = calendar.get(Calendar.DAY_OF_MONTH);
        System.out.println(days);
        System.out.println(calendar.get(Calendar.DAY_OF_YEAR));

        //set()
        //calendar可变性
        calendar.set(Calendar.DAY_OF_MONTH,11);
        System.out.println(calendar.get(Calendar.DAY_OF_MONTH));
        System.out.println(calendar.get(Calendar.DAY_OF_YEAR));

        //add()
        calendar.add(Calendar.DAY_OF_MONTH,1);
        System.out.println(calendar.get(Calendar.DAY_OF_MONTH));
        System.out.println(calendar.get(Calendar.DAY_OF_YEAR));

        //getTime():日历类--->Date
        Date time = calendar.getTime();
        System.out.println(time);

        //setTime():Date-->日历类
        calendar.setTime(new Date());
        System.out.println(calendar.get(Calendar.DAY_OF_MONTH));
        System.out.println(calendar.get(Calendar.DAY_OF_YEAR));
    }
    //以下为jdk8中的
    @Test
    public void test05(){
        //偏移量
        Date date = new Date(2021 - 1900, 9 - 1, 13);
        System.out.println(date);
    }
    /*
    LocalDate,LocalTime,LocalDateTime的使用
    说明：
        1.LocalDateTime相较于LocalDate，LocalTime。使用频率最高
        2.类似Calendar
     */
    @Test
    public void test06(){
        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now();
        LocalDateTime localDateTime = LocalDateTime.now();

        System.out.println(localDate);
        System.out.println(localTime);
        System.out.println(localDateTime);

        //of()：设置指定的年，月，日，时，分，秒。没有偏移量
        LocalDateTime of = LocalDateTime.of(2021, 9, 13, 18, 47, 0);
        System.out.println(of);

        //getXxx()：获取相关属性
        System.out.println(localDateTime.getDayOfMonth());
        System.out.println(localDateTime.getDayOfWeek());
        System.out.println(localDateTime.getMonth());
        System.out.println(localDateTime.getMonthValue());
        System.out.println(localDateTime.getMinute());//获取分钟

        //体现不可变性
        //withXxx():设置相关的属性
        LocalDate localDate1 = localDate.withDayOfMonth(22);
        System.out.println(localDate);
        System.out.println(localDate1);

        LocalDateTime localDateTime1 = localDateTime.withHour(4);
        System.out.println(localDateTime);
        System.out.println(localDateTime1);

        //不可变性
        LocalDateTime localDateTime2 = localDateTime.plusMonths(3);
        System.out.println(localDateTime);
        System.out.println(localDateTime2);

        LocalDateTime localDateTime3 = localDateTime.minusDays(6);
        System.out.println(localDateTime3);
    }
    /*
    Instant 立即的，瞬时的
    类似于java.util.Date类
     */
    @Test
    public void test07(){
        //获取本初子午线对应的标准时间
        Instant instant = Instant.now();
        System.out.println(instant);
        //添加时间的偏移量
        OffsetDateTime offsetDateTime = instant.atOffset(ZoneOffset.ofHours(1));
        System.out.println(offsetDateTime);
        //toEpochMilli():获取自1970年1月1日0时0分0秒（UTC）开始的毫秒数  ---> Date类的getTime()
        long l = instant.toEpochMilli();
        System.out.println(l);
        //ofEpochMilli():通过给定的毫秒数，获取Instant实例  -->Date(long millis)
        Instant instant1 = Instant.ofEpochMilli(1550475314878L);
        System.out.println(instant1);
    }
    /*
    DateTimeFormatter:格式化或解析日期、时间
    类似于SimpleDateFormat
     */
    @Test
    public void test08(){
        //方式一：预定义的标准格式。如：ISO_LOCAL_DATE_TIME;ISO_LOCAL_DATE;ISO_LOCAL_TIME
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime localDateTime = LocalDateTime.now();
        String format = formatter.format(localDateTime);
        System.out.println(format);//2021-09-13T19:00:11.444

        //解析：字符串-->日期
        TemporalAccessor parse = formatter.parse("2021-09-13T19:00:11.444");
        System.out.println(parse);

        //方式二：本地化相关的格式。如：ofLocalizedDateTime()
        // FormatStyle.LONG / FormatStyle.MEDIUM / FormatStyle.SHORT :适用于LocalDateTime
        DateTimeFormatter formatter1 = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG);
        String str2 = formatter1.format(localDateTime);
        System.out.println(str2);//2021年9月13日 下午07时02分56秒

        //本地化相关的格式。如：ofLocalizedDate()
        // FormatStyle.FULL / FormatStyle.LONG / FormatStyle.MEDIUM / FormatStyle.SHORT : 适用于LocalDate
        DateTimeFormatter formatter2 = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);
        //格式化
        String str3 = formatter2.format(LocalDate.now());
        System.out.println(str3);//2021-9-13

        //方式三：重点： 方式三：自定义的格式。如：ofPattern(“yyyy-MM-dd hh:mm:ss”)
        DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        //格式化
        String str4 = formatter3.format(LocalDateTime.now());
        System.out.println(str4);//2019-02-18 03:52:09

        //解析
        TemporalAccessor accessor = formatter3.parse("2019-02-18 03:52:09");
        System.out.println(accessor);
        System.out.println(accessor.getClass());//java.time.format.Parsed
    }
}
