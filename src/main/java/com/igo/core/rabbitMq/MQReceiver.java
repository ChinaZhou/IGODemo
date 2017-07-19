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

        boolean durable = true;
        //设置队列持久化
        this.channel.queueDeclare(queueName, durable, false, false, null);
        this.consumer = new QueueingConsumer(this.channel);
        boolean autoAck = false;
        this.channel.basicConsume(queueName, autoAck, this.consumer);
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
