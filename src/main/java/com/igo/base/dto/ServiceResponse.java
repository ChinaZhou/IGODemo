package com.igo.base.dto;

/**
 * Created by Administrator on 2017/7/11.
 */
public class ServiceResponse<T>
{
    private Double version;
    private Integer code;
    private String description;
    private T result;

    public ServiceResponse()
    {
        this.version = Double.valueOf(1.0D);
        this.code = Integer.valueOf(200);
        this.description = "Success";
    }

    public Integer getCode()
    {
        return this.code;
    }

    public void setCode(Integer code)
    {
        this.code = code;
    }

    public String getDescription()
    {
        return this.description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Object getResult()
    {
        return this.result;
    }

    public void setResult(T result)
    {
        this.result = result;
    }

    public Double getVersion()
    {
        return this.version;
    }
}

