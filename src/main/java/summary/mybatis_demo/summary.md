# MyBatis核心流程分析

```xml
三大阶段
	1.初始化阶段：读取xml配置文件和注解中的配置信息，创建配置对象，并完成各个模块的初始化工作
	2.代理阶段：封装iBatis的编程模型，使用mapper接口开发的初始化工作
	3.读写数据阶段：通过SqlSession完成SQL解析，参数的映射，SQL的执行，结果的解析过程
```

## 配置加载阶段

```xml
建造者模式
	使用多个简单的对象一步一步构建成一个复杂的对象。这种类型的设计模式属于创建型模式，它提供了一种创建对象的最佳方式。
与工厂模式的区别：
	对象的复杂度：
		建造者建造的对象更加复杂，是一个复合产品，它由各个部件复合而成，部件不同产品对象不同，生成的产品粒度细
		在工厂方法模式里，我们关注的是一个产品整体，无须关心产品的各部分是如何创建出来的
客户端参与程度
		建造者模式，导演对象参与了产品的创建，决定了产品的类型和呢容，参与度高，适合实例化对象时属性变化频繁的场景
		工厂模式，客户端对产品的创建过程参与度低，对象实例化时属性值相对比较固定
建造者模式 使用场景
		需要生成的对象具有复杂的内部结构，实例化对象时要屏蔽掉对象内部的细节，让上层代码与复杂对象的实例化过程解耦，可以使用建造者模式，简而言之，如果”遇到多个构造器参数时要考虑用构造器“
		一个对象的实例化是依赖各个组件的产生以及装配顺序，关注的是一步一步地组装出目标对象，可以使用建造器模式
```

核心类库

```xml
XMLConfigBuilder： 主要负责解析mybatis-config.xml
XMLMapperBuilder：主要负责解析映射配置文件
XMLStatementBuiler：主要负责解析配置文件中的SQL语句
```

核心组件[组成部分，关键的数据载体]

```xml
Configuration：MyBatis启动初始化的核心就是将所有的xml配置文件信息加载到Configuration对象中，Configuration是单例的，声明周期是应用级别的。
MapperRegistry：mapper接口动态代理工厂类的注册中心。在MyBatis中，通过mapperProxy实现InvocationHandler接口，MapperProxyFactory用于生成动态代理的实例对象
ResultMap：用于解析mapper.xml文件中的resultMap节点，使用ResultMapping来封装id，result等子元素
MapperStatement：用于存储mapper.xml文件中的select,insert,update和delete节点，同时还包含了这些节点的很多重要属性
SqlSource：mapper.xml文件中的sql语句会被解析成SqlSource对象，经过解析SqlSouece包含的语句最终仅仅包含?占位符，可以直接提交给数据库执行
```

Binding模块分析

```xml
MapperRegistry：mapper接口和对应的代理对象工厂的注册中心
MapperProxyFactory：用于生成mapper接口动态代理的实例对象
MapperProxy：实现了InvocationHandler接口，它是增强mapper接口的体现
MapperMethod：封装了Mapper接口中对应方法的信息，以及对应的sql语句的信息，它是mapper接口与映射配置文件中sql语句的桥梁
```

