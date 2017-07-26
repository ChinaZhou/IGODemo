package com.igo.util;

import com.igo.core.rabbitMq.MQSender;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.Random;

/**
 * Created by Administrator on 2017/7/19.
 */
public class RabbitMqUtil {

    public static void sendMes(String queueName, String mes) {
        MQSender mq = null;
        try {
            mq = new MQSender(queueName, null);
            mq.send(mes);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != mq) {
                    mq.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void sendTransactionMes(String queueName, String mes) {
        MQSender mq = null;
        try {
            mq = new MQSender(queueName, null);
            mq.getChannel().txSelect();
            try {
                mq.send(mes);
                mq.getChannel().txCommit();
            } catch (Exception e) {
                e.printStackTrace();
                mq.getChannel().txRollback();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != mq) {
                    mq.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
