package com.igo.core.rabbitMq;

/**
 * rabbitMq转发器类型
 * Created by Administrator on 2017/7/27.
 */
public enum ExchangeType {
    FANOUT("fanout"),
    DIRECT("direct"),
    TOPIC("topic"),
    HEADERS("headers");

    private String type;

    public String getType()  {
        return  type;
    }
    private ExchangeType(String type) {
        this.type = type;
    }

}

