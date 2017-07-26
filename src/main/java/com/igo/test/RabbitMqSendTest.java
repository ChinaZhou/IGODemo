package com.igo.test;

import com.igo.core.rabbitMq.RabbitMQConfig;
import com.igo.util.RabbitMqQueueNames;
import com.igo.util.RabbitMqUtil;

/**
 * Created by Administrator on 2017/7/19.
 */
public class RabbitMqSendTest {

    public static void main(String[] args) {
        //RabbitMqUtil.sendMes(RabbitMqQueueNames.TEST, "how old are you !");
        RabbitMqUtil.sendTransactionMes(RabbitMqQueueNames.TEST, "Hello World 3");
    }

}
