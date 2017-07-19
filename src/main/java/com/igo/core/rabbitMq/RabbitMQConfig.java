package com.igo.core.rabbitMq;

/**
 * Created by Administrator on 2017/7/19.
 */
public class RabbitMQConfig {
    public static final String HOST = System.getenv("rabbit_mq_host");
    public static final Integer PORT = Integer.valueOf(5672);
    public static final String USER = "rabbitadmin";
    public static final String PASSWORD = "123456";
}
