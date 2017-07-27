package com.igo.util;

import com.igo.core.rabbitMq.ExchangeType;
import com.igo.core.rabbitMq.MQSender;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.Random;

/**
 * Created by Administrator on 2017/7/19.
 */
public class RabbitMqUtil {

    public static void sendMes(String queueName, String mes) {
        MQSender mqSender = null;
        try {
            mqSender = new MQSender();
            mqSender.queueDeclare(queueName, true, false, false, null);//
            mqSender.basicPublish(queueName, mes);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != mqSender) {
                    mqSender.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void sendTransactionMes(String queueName, String mes) {
        MQSender mq = null;
        try {
            mq = new MQSender();
            mq.queueDeclare(queueName, true, false, false, null);//
            mq.getChannel().txSelect();
            try {
                mq.basicPublish(queueName, mes);
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

    public static void sendFanoutMes(String exchangeName, String mes) {
        MQSender mqSender = null;
        try {
            mqSender = new MQSender();
            mqSender.exchangeDeclare(exchangeName, ExchangeType.FANOUT.getType());
           // mqSender.queueDeclare("", true, false, false, null);//
            mqSender.basicPublish(exchangeName, "", null, mes);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != mqSender) {
                    mqSender.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
