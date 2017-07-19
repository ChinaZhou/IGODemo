package com.igo.util;

import com.igo.core.rabbitMq.MQSender;

import java.io.IOException;

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
}
