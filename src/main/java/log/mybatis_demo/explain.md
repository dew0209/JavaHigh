# MyBatis日志模块分析

前提：

```xml
MyBatis没有提供日志的实现类，需要接入第三方的日志组件，但第三方日志组件都有各自的log级别，且各不相同，MyBatis提供了统一的trace，debug，warn，error四个级别。[适配器模式]
自动扫描日志实现，提供的第三方日志插件加载有优先级
日志的使用要优雅的嵌入到主体功能中去。[动态代理]
```

提供统一的日志接口[提供了日志的等级]

```java
public interface Log {
  boolean isDebugEnabled();
  boolean isTraceEnabled();
  void error(String s, Throwable e);
  void error(String s);
  void debug(String s);
  void trace(String s);
  void warn(String s);
}
```

## LogFactory

==private static Constructor<? extends Log> logConstructor;==被选定的第三方日志组件适配器的构造方法。

日志优先级,加载顺序

```xml
	static {
    tryImplementation(LogFactory::useSlf4jLogging);
    tryImplementation(LogFactory::useCommonsLogging);
    tryImplementation(LogFactory::useLog4J2Logging);
    tryImplementation(LogFactory::useLog4JLogging);
    tryImplementation(LogFactory::useJdkLogging);
    tryImplementation(LogFactory::useNoLogging);
  }
```

加载日志大体流程

```xml
tryImplementation(LogFactory::useSlf4jLogging);--->tryImplementation(Runnable runnable)--->runnable.run();--->useSlf4jLogging()--->setImplementation(Class<? extends Log> implClass)--在setImplementation(Class<? extends Log> implClass)中如果candidate.newInstance(LogFactory.class.getName());抛出异常，则会捕获，然后进行下一个tryImplementation的尝试。
```

jdk8新特性

```xml
tryImplementation(LogFactory::useSlf4jLogging);就是
tryImplementation(new Runnable() {
      @Override
      public void run() {
        useSlf4jLogging();
      }
});其它三个tryImplementation也是一样的道理。
```

适配第三方日志框架

```xml
XxxxImpl提供适配能力
```

在进行jdbc操作时候，提供对应的打印日志的能力。

```xml
动态代理技术实现
ConnectionLogger：负责打印来连结信息和SQL语句，并创建PreparedStatementLogger
PreparedStatementLogger：负责打印参数信息，并创建ResultSetLogger
ResultSetLogger：负责打印数据结果信息
```

简单实现[见代码]

