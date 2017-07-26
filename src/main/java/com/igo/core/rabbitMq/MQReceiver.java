package com.igo.core.rabbitMq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Administrator on 2017/7/19.
 */
public class MQReceiver {

    private String queueName;
    private Channel channel;
    private int prefetchCount = 1;
    private QueueingConsumer consumer;

    public MQReceiver(String queueName, int prefetchCount) throws IOException, TimeoutException {
        this.prefetchCount = prefetchCount;

        _init(queueName, RabbitMQConfig.HOST);
    }

    public MQReceiver(String queueName, String host) throws IOException, TimeoutException {
        _init(queueName, host);
    }

    public MQReceiver(String queueName) throws IOException, TimeoutException {
        _init(queueName, RabbitMQConfig.HOST);
    }

    public void _init(String queueName, String host) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        factory.setPort(RabbitMQConfig.PORT.intValue());
        factory.setUsername(RabbitMQConfig.USER);
        factory.setPassword(RabbitMQConfig.PASSWORD);
        Connection connection = factory.newConnection();
        this.channel = connection.createChannel();

        //是否持久化
        boolean durable = true;
        //设置队列持久化
        this.channel.queueDeclare(queueName, durable, false, false, null);
        //在4.X中已被废弃，缺点：1、不支持自动连接恢复；2、数据量过大时容易产生内存溢出
        this.consumer = new QueueingConsumer(this.channel);
        //是否自动应答
        boolean autoAck = false;
        //第一个参数是Consumer绑定的队列名，
        // 第二个参数是自动确认标志，如果为true，表示Consumer接受到消息后，会自动发确认消息(Ack消息)给消息队列，消息队列会将这条消息从消息队列里删除，
        // 第三个参数就是Consumer对象，用于处理接收到的消息。
        this.channel.basicConsume(queueName, autoAck, this.consumer);
        //设置每个消费者在同一时间只有prefetchCount个消息在处理
        this.channel.basicQos(this.prefetchCount);
    }

    public String getQueueName() {
        return this.queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public Channel getChannel() {
        return this.channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public QueueingConsumer getConsumer() {
        return this.consumer;
    }

    public void setConsumer(QueueingConsumer consumer) {
        this.consumer = consumer;
    }

    public int getPrefetchCount() {
        return this.prefetchCount;
    }

    public void setPrefetchCount(int prefetchCount) {
        this.prefetchCount = prefetchCount;
    }

}
