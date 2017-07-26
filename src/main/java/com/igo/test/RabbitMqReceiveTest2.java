package com.igo.test;

import com.igo.core.rabbitMq.MQReceiver;
import com.igo.util.RabbitMqQueueNames;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Administrator on 2017/7/19.
 */
public class RabbitMqReceiveTest2 {


    public static void main(String[] args) {
        MQReceiver receiver = null;
        try {
            receiver = new MQReceiver(RabbitMqQueueNames.TEST);
            Channel channel = receiver.getChannel();
            QueueingConsumer consumer = receiver.getConsumer();
            System.out.println("start........");
            while (true) {
                 QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                 byte[] body = delivery.getBody();
                 String mes = new String(body);
                 System.out.println("get mes ==== " + mes);
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
