package com;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;

@SpringBootApplication
@MapperScan("com.winter.springbootmybatisdemo.mapper")//将项目中对应的mapper类的路径加进来就可以了
@EnableTransactionManagement
public class SpringbootMybatisDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootMybatisDemoApplication.class, args);
	}

	//创建 ActiveMQ 连接工厂
	//代替application.yml文件中activemq的配置
	@Bean
	public ConnectionFactory connectionFactory() {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		connectionFactory.setBrokerURL("tcp://localhost:61616");
		connectionFactory.setUserName("admin");
		connectionFactory.setPassword("admin");
		return connectionFactory;
	}

	//-----点对点模式
	@Bean
	public JmsTemplate jmsQueueTemplate() {
		return new JmsTemplate(connectionFactory());
	}

	//-----订阅发布模式
	@Bean
	public JmsTemplate jmsTopicTemplate() {
		JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory());
		jmsTemplate.setPubSubDomain(true);//开启
		return jmsTemplate;
	}

	/**
	 * JMS 队列的监听容器工厂-----点对点模式
	 */
	@Bean(name = "ptpContainer")
	public DefaultJmsListenerContainerFactory jmsQueueListenerContainerFactory() {
		DefaultJmsListenerContainerFactory factory =
				new DefaultJmsListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory());
		//设置连接数
		factory.setConcurrency("3-100");
		factory.setPubSubDomain(false);
		//重连间隔时间
		factory.setRecoveryInterval(1000L);
		return factory;
	}

	/**
	 * JMS 队列的监听容器工厂-----发布订阅模式
	 */
	@Bean(name = "pubsubContainer")
	public DefaultJmsListenerContainerFactory jmsTopicListenerContainerFactory() {
		DefaultJmsListenerContainerFactory factory =
				new DefaultJmsListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory());
		factory.setConcurrency("1");
		factory.setPubSubDomain(true);//发布订阅模式开启
		return factory;
	}

	//法2：另一种更少代码的零配置写法。注意：需要将上面的全部注释，其次要将application.yml文件，添加activemq的地址、用户名密码
	/*@Bean
	public Queue textMsgDestination() {
		return new ActiveMQQueue("LISTENER_TEXT_MSG");
	}*/
}