package com.igo.base.test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class RabbitMqJava {

	ConnectionFactory factory = null;
	private final static String QUEUE_NAME = "hello";
	
	public RabbitMqJava() {
		factory = new ConnectionFactory();
		factory.setHost("192.168.189.128");
		//factory.setVirtualHost("/");
		factory.setPort(5672);
		factory.setUsername("rabbitadmin");
		factory.setPassword("123456");
	}
	
	public void send(String message) {
		 try {
			Connection connection = factory.newConnection();
			Channel channel = connection.createChannel();
			channel.queueDeclare(QUEUE_NAME, false, false, false, null);
			channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
			channel.close();
			connection.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void receive() throws Exception {
		Connection connection = factory.newConnection();  
        Channel channel = connection.createChannel();  
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);  
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");  
        QueueingConsumer consumer = new QueueingConsumer(channel);  
        channel.basicConsume(QUEUE_NAME, true, consumer);  
        while(true){  
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();  
            String message = new String(delivery.getBody());  
            System.out.println(" [x] Received '" + message + "'");  
        }  
	}
	
	public static void main(String[] args) {
		RabbitMqJava rabbitMqJava = new RabbitMqJava();
		//rabbitMqJava.send("hello");
		try {
			rabbitMqJava.receive();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
