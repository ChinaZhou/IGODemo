package com.igo.test;

import com.igo.core.rabbitMq.ExchangeType;
import com.igo.core.rabbitMq.MQReceiver;
import com.igo.util.RabbitMqQueueNames;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Exchange Fanout
 *
 * Created by Administrator on 2017/7/19.
 */
public class RabbitMqReceiveTest3 {


    public static void main(String[] args) {
        MQReceiver receiver = null;
        try {
            receiver = new MQReceiver(RabbitMqQueueNames.FANOUN_TEST_1);
            Channel channel = receiver.getChannel();
            String exchangeName = "fanout_test";
            channel.exchangeDeclare(exchangeName, ExchangeType.FANOUT.getType());
            channel.queueBind(RabbitMqQueueNames.FANOUN_TEST_1, exchangeName, "");
            QueueingConsumer consumer = receiver.getConsumer();
            System.out.println("start........");
            while (true) {
                 QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                 byte[] body = delivery.getBody();
                 String mes = new String(body);
                 System.out.println("get mes ==== " + mes);
                //channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
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
