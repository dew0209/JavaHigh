Tomcat目录结构

启动tomcat时，如果控制台输出乱码，请将conf下的logging.properties文件中的UTF-8修改成GBK

```xml
conf		配置文件
bin			执行命令
lib		加载的jar包
logs	日志目录
webapps	默认应用程序

配置文件
	server.xml:核心配置文件
	web.xml:servlet标准的web.xml部署文件
lib
	Tomcat依赖的jar包
logs目录
	localhost-xxx.log
	catalina-xxx.log
webapps目录
	三种应用的部署方式
```

Tomcat的配置文件

```xml
前提：利用java自带的工具jconsole监控以便查看我们的修改

1.server.xml修改连接池的大小
	默认是不使用连接池的
	<Connector port="8080" protocol="HTTP/1.1"
               connectionTimeout="20000"
               redirectPort="8443" />
	使用连接池的方式
	<Executor name="tomcatThreadPool" namePrefix="yan-king-"
        maxThreads="150" minSpareThreads="88"/>
	<Connector executor="tomcatThreadPool"
               port="8080" protocol="HTTP/1.1"
               connectionTimeout="20000"
               redirectPort="8443" />
2.server.xml修改I/O模式（BIO与NIO）
	protocol="org.apache.coyote.http11.Http11NioProtocol"
	protocol="org.apache.coyote.http11.Http11Protocol"

3.server.xml去掉AJP的connector

4.server去掉access-log日志
```

修改之前的监控：

![image-20210923074352644](C:/Users/吕露的小可爱/AppData/Roaming/Typora/typora-user-images/image-20210923074352644.png)

修改之后

Tomcat启动时如果没有请求过来，那么线程数（都是指线程池的）为0；一旦有请求，Tomcat会初始化minSpareThreads设置的线程数.

**Tomcat的三种部署模式**

```xml
1.显示部署
	添加context元素方式(server.xml)
	<Context path="/de1" docBase="D:/d1" reloadable="true"></Context>
	创建xml的方式(conf/Catalina/localhost)
2.隐式部署
	将一个war文件或者整个应用程序复制到tomcat的webapps
```

**Tomcat总体架构**

![image-20210923085352745](C:/Users/吕露的小可爱/AppData/Roaming/Typora/typora-user-images/image-20210923085352745.png)

```xml
Connector组件：连接器。主要负责Tomcat与客户端的通讯
Container组件：Servlet容器

connector -- >  container
					<--
```

```xml
一个请求的处理流程
	1.监听服务器端口，读取来自客户端的请求
	2.将请求数据按照指定协议进行解析
	3.根据请求地址匹配正确的内容
	4.将响应返回客户端
具体描述：
	1.用户点击网页内容，请求被发送到本机端口8080，被在哪里监听的Coyote HTTP/1.1 Connector获得。
	2.Connector把该请求交给它所在的service的Engine来处理，并等待Engine的回应。
	3.Engine获得请求为localhost/test/index.jsp,匹配所有的虚拟主机Host
	4.Engine匹配到名为localhost的Host（即使匹配不到也把请求交给该Host处理，因为该Host被定义位Engine的默认主机），名为localhost的Host获得请求/test/index.jsp，匹配它所拥有的所有的Context。Host匹配到路径为/test的Context（如果匹配不到就把该请求交给路径名为""的Context去处理）
	5.path="/test"的Context获得请求/index.jsp,在它的mapper rable中寻找出对应的servlet。Context匹配到URL PATTERN为*.jsp的servlet，对应JspServlet类
	6.构造HttpServletRequest对象和HttpServletResponse对象，作为参数调用JspServlet的doGet()或doPost().执行业务逻辑，数据存储等程序
	7.Context把执行完之后的HttpServletReponse对象返回给Host
	8.Host把HttpServletResponse对象返回给Engine
	9.Engine把HttpServletResponse对象返回给COnnector
	10.Connector把HttpServletResponse对象返回给客户Browser
```

**NIO**

![image-20210923091035978](C:/Users/吕露的小可爱/AppData/Roaming/Typora/typora-user-images/image-20210923091035978.png)

```xml
责任链模式
	1.请求被Connector组件接受，创建Resquest和Response对象
	2.Connector将Request和Response交给Container，先通过Engine的pipeline组件流经内部的每个valve
	3.请求流转到Host的pipeline组件中，并且经过内部valve过滤
	4.请求流转到Context的pipeline组件中，并且经过内部valve
	5.请求流转到Wrapper的pipeline组件中，并且经过内部的valve的过滤
	6.wrapper内部的wrappervalve创建FilterChain实例，调用指定的servlet实例处理请求
	7.返回
```

**Tomcat顶层结构**

```xml
server：服务器的意思，代表整个tomcat服务器，一个tomcat只有一个server
service：server中的一个逻辑功能层，一个server可以包含多个service
connector：称作连接器，是service的核心组件之一，一个service可以有多个connector，主要是连接客户端请求
Container：service的另一个核心组件，按照层级有Engine，Host，Context，Wrapper四种，一个service只有一个Engine，其主要作用是执行业务逻辑
```

**server**

```xml
server是Tomcat最顶层的容器，代表着整个服务器
	Tomcat中其标准实现：
		StandardServer
		除了实现server接口还要继承Lifecycle
	好处：生命周期统一接口Lifecycle把所有的启动，停止，关闭等都放在一起统一管理
```

![image-20210923092918558](C:/Users/吕露的小可爱/AppData/Roaming/Typora/typora-user-images/image-20210923092918558.png)

**service**

```xml
service：一个tomcat包含多个service
	tomcat中标准实现：
		StandardService
	除了实现service接口还要继承Lifecycle
	好处：
		生命周期统一接口Lifecycle把所有的启动，停止，关闭等都放在一起统一管理
```

**service拓展出Tomcat原型**

```xml
service中请求负责监听和请求处理分开为两个模块
	connector负责处理请求监听
	container负责处理请求处理

一个service可以有多个connector，但只能有一个container

任何容器都有启动satrt()和关闭stop()方法
```

**Connector解析**

```xml
Connector使用ProtocolHandler来处理请求的
	ProtocolHandler包含了三个部件
		Endpoint Processor Adapter
		Endpoint：用来处理底层socket的网络连接
		Processor：用于将Endpoint接收到的socket封装成Request
		Adapter：充当适配器，用于将Request转换为ServletRequest交给container进行具体的处理
```

![image-20210923094111047](C:/Users/吕露的小可爱/AppData/Roaming/Typora/typora-user-images/image-20210923094111047.png)

**启动流程**

init流程和start流程

```xml
init流程:分别在Bootstrap、Catalina、StandardServer、StandardService 的init方法加入断点和输出日志
start流程:分别在Bootstrap、Catalina、StandardServer、StandardService 的start方法加入断点和输出日志
```

**Lifecycle与模板方法模式**

```xml
模板方法模式就是为多种类似业务提供一个算法执行的统一框架，把这些业务中共同的部分抽取出来进行具体实现，而某些业务中特定的需求推迟到子类中进行重写实现

Tomcat的启动过程中Catalina调用StandardService中的start()方法，但是StandardService自身没有start()方法

原来StandardService继承了抽象类LifecycleBase,它有start()并且它在此方法中调用了一个未实现的抽象方法startInternal() ，Catalina调用StandardService中的start()最后会调用至startInternal()

这种模式使得StandardService等这些类抽出一个共同的start()在LifecycleBase中进行实现（方便统一生命周期管理）。如果它需进行特殊的业务处理的话可以在startInternal()中处理
```

**嵌入式Tomcat**

```xml
嵌入式Tomcat：
	将Tomcat嵌入到主程序中进行运行
优点：灵活部署，任意指定位置，通过复杂的条件判断
发展趋势：
	springboot默认集成的是Tomcat容器

springboot 嵌入式 maven依赖
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-tomcat</artifactId>
  <scope>provided</scope>
</dependency>
```

maven集成tomcat插件

```xml
<dependency>
  <groupId>org.apache.tomcat.maven</groupId>
  <artifactId>tomcat7-maven-plugin</artifactId>
  <version>2.2</version>
</dependency>

插件运行：选择pom.xml文件，点击右键，选择Run As --> maven build --> tomcat7:run

maven集成tomcat插件启动tomcat是常用的tomcat嵌入式启动
```

Maven集成tomcat插件启动分析

```xml
分析它的启动
	Tomcat7RunnerCli是引导类
进一步分析
	Tomcat7RunnerCli主要依靠Tomcat7Runner
分析结论
	原来嵌入式启动就是调用了Tomcat的api来实现的
```

TomcatApi接口

```xml
实现嵌入式Tomcat的基础
	tomcat本身提供了外部可以调用的api
tomcat类
	1.位置：org.apache.catalina.startup.Tomcat;
	2.该类是public
	3.该类有server，service，Engine，connector，host等属性
	4.该类有init(),start(),stop(),destory()等方法
分析结论
	tomcat类是外部调用的入口
```

**Tomcat性能优化**

```xml
性能优化的三个指标
	降低响应时间
	提高系统吞吐量(QPS)
	提高服务的可用性
性能优化的原则
	具体情况具体分析
	积少成多
使用工具：
	JConsole：一个Java性能分析器
	JMeter：压力测试工具

性能优化测试原则：
	指标：正确率，cpu占有率，QPS，JVM
	程序分类：I/O密集型（网络，文件读写），CPU密集型（加密/解密算法）

server.xml优化
	Connector连接器的I/O模式
		连接器改为NIO模式
		NIO模式最大化压榨了CPU，把时间片更好的利用起来
		NIO适合大量长连接
	关闭自动重载
		<Context docBase="" reloadable="false"/>
		关闭自动重载，默认是true(不同版本中有差异)
		自动加载增加运行开销并且很容易内存溢出
		[需要注意可能的风险：
      1、在开发阶段将reloadable属性设为true，有助于调试servlet和其它的class文件，但这样用加重服务器运行负荷，建议在Web应用的发存阶段将reloadable设为false。
      2、由于动态替代class，引发自动部署；如果部署失败，可能导致出现了2个相同的部署包。再次重启，然后2个缓存包都正常启动。 其本质原因是Tomcat在卸载应用的过程中，如果出现异常，则无法继续删除缓存；在重新加载的时候，无法清空已有的缓存，这个才是问题的根源。
		]
	配置线程池
		Executor标签中属性
			【namePrefix】线程命名前缀
			【maxThreads】最大允许线程数
			【minSpareThreads】最少空闲线程，相当于初始化的线程，线程池中的线程
		Connector标签中的属性
			【executor】对上面Executor标签标签的引用

web.xml优化
	应用程序运行时最终会加载conf/web.xml和应用web.xml的合集
	servlet优化
		当前应用时REST应用(微服务)
		1.去掉不必要的资源，jspservlet
		2.session也可以移除
	valve优化：
		移除掉AccessLogValve
		valve实现都需要消耗java应用的计算时间，一般我们可以使用nginx来记录日志
	jsp预编译优化	
		JSP->JAVA->CLASS
		可使用ant先编译jsp
	Jspservlet开发模式(development)设置为false

springboot中tomcat优化
	设置线程池 server.tomcat.accesslog=false  【线程池可以确保无用的线程进行回收，确保系统的稳定性】
	关闭accesslog日志 server.tomcat.accesslog=false
```

