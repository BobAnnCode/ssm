package com.tedu.rabbitmq;

import java.util.UUID;

import org.junit.Test;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;

public class Test_3_publish_consumer1 {
	//从消息队列服务器取消息
	@Test
	public void consumer()throws Exception
	{
		//1.建立连接
		ConnectionFactory factory=new 
				ConnectionFactory();
		factory.setHost("192.168.211.130");
		factory.setPort(5672);
factory.setUsername("jtadmin");
factory.setPassword("jtadmin");
factory.setVirtualHost("/jt");
		//2,得到channel
//com.rabbitmq.client
Connection connection=factory.newConnection();
Channel channel=connection.createChannel();
String exchangeName="E1";
channel.exchangeDeclare(exchangeName, "fanout");
String queueName=UUID.randomUUID().toString();
		
channel.queueDeclare(queueName, 
		true, false, false, null);
//把队列和交换机绑定
channel.queueBind(queueName, exchangeName, "");
//设置每次取几个数据
channel.basicQos(1);
		//4,得到消费者
//创建的consumer通过channel来读数据
QueueingConsumer consumer=new QueueingConsumer
(channel);
//去取数据
//p2:autoack 消费者取到消息，自动确认，确认后，服务器会把消息删除
//1,从消息队列服务器上取order队列的数据
//2,自动确认，服务器就删除消息
//3,把得到消息交给第三个参数consumer处理
channel.basicConsume(queueName, true, consumer);
		//5,遍历消费者consumer
boolean isRunning=true;
System.out.println("消费者1启动了");
while(isRunning)
{
	//delivery代表的是消息队列中的一个数据
	Delivery delivery=consumer.nextDelivery();
	byte[] body=delivery.getBody();
	String msg=new String(body);
	System.out.println("消费者1收到："+msg+"发货");
}
		//6,连接关
	}

}
