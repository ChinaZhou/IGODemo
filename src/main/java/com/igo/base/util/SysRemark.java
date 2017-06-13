package com.igo.base.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Administrator on 2017/6/8.
 */
public class SysRemark {
    public static String startFix = "【";
    public static String endFix = "】";
    public static String newLine = "<br/>";

    static private String _fix(String remark) {
        return startFix + remark + endFix;
    }

    static public String append(String existsRemark, String newRemark) {
        int rows = StringUtils.countMatches(existsRemark, startFix);
        String newIndex = new Integer(rows + 1).toString() + ": ";
        if (StringUtils.isBlank(existsRemark)) {
            existsRemark = "";
        } else {
            existsRemark += newLine;
        }

        return existsRemark + newIndex + _fix(newRemark);
    }
}
