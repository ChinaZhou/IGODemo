package com.igo.core.rabbitMq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Administrator on 2017/7/19.
 */
public class MQSender {
    private String queueName;
    private Connection connection = null;
    private Channel channel = null;

    public MQSender(String queueName) throws IOException, TimeoutException {
        this(queueName, RabbitMQConfig.HOST);
    }

    public MQSender(String queueName, String host) throws IOException, TimeoutException {
        this.queueName = queueName;
        if (queueName == null) {
            return;
        }
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(StringUtils.isNotBlank(host)?host:RabbitMQConfig.HOST);
        factory.setPort(RabbitMQConfig.PORT.intValue());
        factory.setUsername(RabbitMQConfig.USER);
        factory.setPassword(RabbitMQConfig.PASSWORD);
        this.connection = factory.newConnection();
        this.channel = this.connection.createChannel();
        boolean durable = true;
        //设置队列持久化
        //第二个参数durable表示建立的消息队列是否是持久化(RabbitMQ重启后仍然存在,并不是指消息的持久化),
        // 第三个参数exclusive 表示建立的消息队列是否只适用于当前TCP连接，
        // 第四个参数autoDelete表示当队列不再被使用时，RabbitMQ是否可以自动删除这个队列,
        // 第五个参数arguments定义了队列的一些参数信息，主要用于Headers Exchange进行消息匹配时.
        this.channel.queueDeclare(queueName, durable, false, false, null);//
    }

    public void send(String message) throws IOException {
        if ((this.connection == null) || (this.channel == null)
                || (message == null)) {
            return;
        }
        //发送消息
        //第一个参数exchange是消息发送的Exchange名称，如果没有指定，则使用Default Exchange。
        // 第二个参数routingKey是消息的路由Key，是用于Exchange将消息路由到指定的消息队列时使用(如果Exchange是Fanout Exchange，这个参数会被忽略),
        // 第三个参数props是消息包含的属性信息。RabbitMQ的消息属性和消息体是分开的，不像JMS消息那样同时包含在javax.jms.Message对象中，这一点需要特别注意。
        // 第四个参数body是RabbitMQ消息体。
        this.channel.basicPublish("", this.queueName, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
    }

    public void close() throws IOException, TimeoutException {
        if ((this.connection == null) || (this.channel == null)) {
            return;
        }
        this.channel.close();
        this.connection.close();
    }

    public void sendAndClose(String channel, String title, String content) throws TimeoutException {
        MQSender mq = null;
        try {
            mq = new MQSender(channel);
            mq.send(content);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (mq != null) {
                    mq.close();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        } finally {
            try {
                if (mq != null) {
                    mq.close();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    public String getQueueName() {
        return this.queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
