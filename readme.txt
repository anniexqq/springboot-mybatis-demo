Springboot + Mybatis(TK mybatis)+ ActiveMQ（springboot零配置）+ shiro+ redis

1.Bootstrap登录功能（http://localhost:8081）
mm/1234
（1）使用shiro登录验证 （密码盐加密）
（2）权限控制(程序注解、HTML页面注解的方法 和 perms[index:view] 方法)
--------------------------------------------------------------------------------------------------------------------------------------------------------
2.
 双击 E:\Program Files\apache-activemq-5.15.4\bin\win64\activemq.bat 文件，启动ActivemqMQ
 ActiveMQ （点对点）消息模式 （ http://localhost:8081/msg/sendMsg ）
 ActiveMQ （发布/订阅）消息模式 （ http://localhost:8081/msg/pubSubMsg ）

3.
通过ActiveMQ消息队列发送消息，新增用户
postman请求：
post地址：http://localhost:8081/msg/addUser
入参：JSON （application/json） raw
  {
  	"userName":"唐为",
  	"password":"1234",
  	"phone":"15100000000"
  }

4.注意：.springboot启动文件需要放在消息队列的发送和接受层外，否则会监听不到消息

5.-------------------------------------------------------------simple-------------------------------------------------------------------------------------------
另一种更少代码的零配置写法，不需要在主函数里写 broker-url=tcp://localhost:61616 等，更不需要在主函数里创建 ActiveMQ 连接工厂、创建jms末模板、创建监听器等。
见main主函数、MessageServiceImpl.java中，（全工程搜“法2“能看到）,还需要注释containerFactory ="ptpContainer"等监听容器。

6.
点对点模式
 （1）每个消息只有一个消费者（Consumer）(即一旦被消费，消息就不再在消息队列中)
 （2）发送者和接收者之间在时间上没有依赖性，也就是说当发送者发送了消息之后，
不管接收者有没有正在运行，它不会影响到消息被发送到队列
 （3）接收者在成功接收消息之后需向队列应答成功

 发布/订阅模式
 （1）每个消息可以有多个消费者
 （2）发布者和订阅者之间有时间上的依赖性。针对某个主题（Topic）的订阅者，它必须创建一个订阅者之后，才能消费发布者的消息
 （3）为了消费消息，订阅者必须保持运行的状态

 7.--------------------------------------------------------404错误---------------------------------------------------------------------------------------------------
 在resource/templates/error下添加error.html页面(error替换成对应的错误码，404、401、500等，还可以用4xx、5xx等)，springBoot会自动找到该页面作为错误页面，适合内嵌Tomcat或者war方式。

--------------------------------------------------------------------------------------------------------------------------------------------------------
 8.redis
 （1）启动：
  在E:\Program Files\Redis-3.2.100
  执行命令：./redis-server.exe ./redis.windows.conf，启动redis

 （2）redis控制台与密码设置：
  再开一个控制台，执行命令：
  redis-cli.exe -h 127.0.0.1 -p 6379 -a 1234
  config get requirepass //获取当前密码
  config set requirepass "1234"//设置当前密码,服务重新启动后又会置为默认，即无密码；不建议此种方式

  密码的设置推荐使用配置文件修改的方式，redis.windows.conf文件，添加：
# requirepass foobared
  requirepass 1234  //此处注意，行前不能有空格

9.@Cacheable注解 整合Redis
  postman调用：   http://localhost:8081/user/all?pageNum=1&pageSize=5
  调用一次查询后，可在redis控制台执行：keys * ，看存储的key

  注意事项：
            1.@EnableCaching 开启注解
            2.Shiro初始化时用到的MyRealm中，UserService注入加入懒加载注解@Lazy，用来防止@Cacheable注解失效
