package com.tedu.rabbitmq;

import java.util.UUID;

import org.junit.Test;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;

public class Test_5_topic_consumer2 {
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
String exchangeName="E3";
channel.exchangeDeclare(exchangeName, "topic");
String queueName=UUID.randomUUID().toString();		
channel.queueDeclare(queueName, 
		true, false, false, null);
channel.queueBind(queueName, exchangeName, "domestic.*");
channel.basicQos(1);		
QueueingConsumer consumer=new QueueingConsumer
(channel);
channel.basicConsume(queueName, true, consumer);
		//5,遍历消费者consumer
boolean isRunning=true;
System.out.println("消费者2启动了");
while(isRunning)
{
	//delivery代表的是消息队列中的一个数据
	Delivery delivery=consumer.nextDelivery();
	byte[] body=delivery.getBody();
	String msg=new String(body);
	System.out.println("消费者2收到："+msg+"国内购");
}
		//6,连接关
	}

}
