package com.igo.test;

import com.igo.core.rabbitMq.MQReceiver;
import com.igo.util.RabbitMqQueueNames;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 应答模式之transaction机制
 * transaction机制发送消息,事务机制：手动提交和回滚
 * 执行txSelect，开启事务
 * 执行txCommit，消息才会转发给队列进入ready状态
 * 执行txRollback，消息被取消
 *
 * Created by Administrator on 2017/7/19.
 */
public class RabbitMqReceiveTest2 {


    public static void main(String[] args) {
        MQReceiver receiver = null;
        try {
            receiver = new MQReceiver(RabbitMqQueueNames.TEST);
            Channel channel = receiver.getChannel();
            channel.txSelect();
            QueueingConsumer consumer = receiver.getConsumer();
            System.out.println("start........");
            while (true) {
                 QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                 byte[] body = delivery.getBody();
                 String mes = new String(body);
                 System.out.println("get mes ==== " + mes);
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                //不提交事务相当于未给通知，消息会处于unacked状态
                channel.txCommit();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
