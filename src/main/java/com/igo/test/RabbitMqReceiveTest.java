package com.igo.test;

import com.igo.core.rabbitMq.MQReceiver;
import com.igo.util.RabbitMqQueueNames;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeoutException;

/**
 * 应答模式之confirm机制：消息接收
 * basicAck：成功消费，消息从队列中删除
 * basicNack：requeue=true，消息重新进入队列，false被删除
 * basicReject：等同于basicNack
 * basicRecover：消息重入队列，requeue=true，发送给新的consumer，false发送给相同的consumer
 *
 * Created by Administrator on 2017/7/19.
 */
public class RabbitMqReceiveTest {

    public static void main(String[] args) {
        MQReceiver receiver = null;
        Channel channel = null;
        try {
            receiver = new MQReceiver(RabbitMqQueueNames.TEST);
            channel = receiver.getChannel();
            QueueingConsumer consumer = receiver.getConsumer();
            System.out.println("start........");
            while (true) {
                 QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                 byte[] body = delivery.getBody();
                 String mes = new String(body);
                 System.out.println("get mes ==== " + mes);
                 int i = new Random().nextInt(10);
                System.out.println("i ====== " + i);
                 if (i <= 3) {
                     //通知消费成功
                     channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                 } else {
                     //通知其重新加入队列，如果不做任何通知，消息将处于unacked状态，直至该消费者挂掉或重启才会重新加入队列
                     channel.basicNack(delivery.getEnvelope().getDeliveryTag(), false, true);
                 }
                 Thread.sleep(2*1000);
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
