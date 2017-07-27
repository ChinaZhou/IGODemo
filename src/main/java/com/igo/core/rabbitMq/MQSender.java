package com.igo.core.rabbitMq;

import com.rabbitmq.client.*;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * Created by Administrator on 2017/7/19.
 */
public class MQSender {
    private Connection connection = null;
    private Channel channel = null;

    public MQSender() throws IOException, TimeoutException {
        this(RabbitMQConfig.HOST);
    }

    public MQSender(String host) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        factory.setPort(RabbitMQConfig.PORT);
        factory.setUsername(RabbitMQConfig.USER);
        factory.setPassword(RabbitMQConfig.PASSWORD);
        this.connection = factory.newConnection();
        this.channel = this.connection.createChannel();
    }

    /**
     * 设置队列持久化
     * @param queueName   队列名称
     * @param durable     表示建立的消息队列是否是持久化(RabbitMQ重启后仍然存在,并不是指消息的持久化)
     * @param exclusive   表示建立的消息队列是否只适用于当前TCP连接
     * @param autoDelete  表示当队列不再被使用时，RabbitMQ是否可以自动删除这个队列
     * @param arguments   定义了队列的一些参数信息，主要用于Headers Exchange进行消息匹配时
     */
    public void queueDeclare(String queueName, Boolean durable, Boolean exclusive, Boolean autoDelete, Map<String, Object> arguments) throws IOException {
        this.channel.queueDeclare(queueName, durable, exclusive, autoDelete, arguments);
    }

    /**
     * 发送消息
     * @param exchange 消息发送的Exchange名称，如果没有指定，则使用Default Exchange
     * @param routingKey 消息的路由Key，是用于Exchange将消息路由到指定的消息队列时使用(如果Exchange是Fanout Exchange，这个参数会被忽略)
     * @param props  消息包含的属性信息。RabbitMQ的消息属性和消息体是分开的
     * @param mes  消息体
     */
    public void basicPublish(String exchange, String routingKey, AMQP.BasicProperties props, String mes) throws IOException {
        this.channel.basicPublish(exchange, routingKey, props, mes.getBytes());
    }

    /**
     * 发送消息
     * @param queueName
     * @param mes
     * @throws IOException
     */
    public void basicPublish(String queueName, String mes) throws IOException {
        this.basicPublish("", queueName, MessageProperties.PERSISTENT_TEXT_PLAIN, mes);
    }

    /**
     * 声明转发器和类型
     * @param exchangeName  转发器名称
     * @param exchageType 转发器类型 fanout、direct、topic、headers
     * @throws IOException
     */
    public void exchangeDeclare(String exchangeName, String exchageType) throws IOException {
        this.channel.exchangeDeclare(exchangeName, exchageType);
    }

    public void close() throws IOException, TimeoutException {
        if ((this.connection == null) || (this.channel == null)) {
            return;
        }
        this.channel.close();
        this.connection.close();
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
