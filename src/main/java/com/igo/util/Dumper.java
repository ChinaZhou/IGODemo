package com.igo.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * 打印信息
 * Created by Administrator on 2017/6/3.
 */
public class Dumper {

    /**
     * 打印实体对象信息
     * @param object
     */
    public static void dump(Object object) {
        System.out.println("++++++++++++++++++++++++++++++++");
        try {
            Gson gson = new GsonBuilder().serializeSpecialFloatingPointValues().serializeNulls().setPrettyPrinting().create();
            System.out.println(gson.toJson(object));
        } catch (Exception e) {
            System.out.println(ToStringBuilder.reflectionToString(object, ToStringStyle.MULTI_LINE_STYLE));
        }
        System.out.println("++++++++++++++++++++++++++++++++");
    }

    /**
     * 返回异常信息
     * @param aThrowable
     * @return
     */
    public static String getStackTrace(Throwable aThrowable) {
        Writer result = new StringWriter();
        PrintWriter printWriter = new PrintWriter(result);
        aThrowable.printStackTrace(printWriter);
        return result.toString();
    }

    public static void main(String[] args) {
        try {
            int i = 1/0;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.printf(Dumper.getStackTrace(e));
        }
    }
}
