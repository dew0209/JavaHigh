# MyBatis反射模块分析

```xml
orm框架查询数据过程
	从数据库加载数据---> 实例化目标对象--->找到匹配规则--->对象属性复制
```

反射的核心类

```xml
MetaObject:封装了对象的元信息，包装了mybatis中五个核心的反射类，也是提供给外部使用的反色和工具类，可以利用它读取或者修改对象的属性信息。
	MetaObject
		originalObject
		objectWrapper
		objectFactory
		objectWrapperFactory
		reflectorFactory
ObjectFactory：MyBatis每次创建结果对象的新实例时，它都会使用对象工厂（ObjectFactory）去构建pojo
ReflectorFactory：创建Reflector工厂类，Reflector是mybatis反射模块的基础，每个Reflector对象都对应一个类，在其中缓存了反射操作所需要的类元信息
ObjectWrapper：对对象的封装，抽象了对象的属性信息，它定义了一系列查询对象属性信息的方法，以及更新属性的方法
ObjectWrapperFactory：ObjectWrapper的工厂类，用于创建ObjectWrapper
```

