package com.my.esindex;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by yexianxun on 2016/8/24.
 */
public class WeekIndex {
    private static String settingJd = null;
    private static String settingTaoxi = null;
    private static String settingVip = null;
    public static String jd = "jd";
    public static String taobao = "taobao";
    public static String tmall = "tmall";
    public static String vip = "vip";

    static {
        try {
            settingJd = FileUtils.readFileToString(new File(MonthIndex.class.getResource("/").getFile() + jd + ".txt"));
            settingTaoxi = FileUtils.readFileToString(new File(MonthIndex.class.getResource("/").getFile() + "taoxi.txt"));
            settingVip = FileUtils.readFileToString(new File(MonthIndex.class.getResource("/").getFile() + vip + ".txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        createWeekIndex(jd, settingJd);
        createWeekIndex(taobao, settingTaoxi);
        createWeekIndex(tmall, settingTaoxi);
        createWeekIndex(vip, settingVip);
    }

    private static void createWeekIndex(String type, String setting) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, 8 - calendar.get(Calendar.DAY_OF_WEEK));
        int week = calendar.get(Calendar.WEEK_OF_YEAR);
        int year = calendar.get(Calendar.YEAR);
        String index = "ec_" + type + "_" + year + "_w" + week;
        try {
            String result = Http.doPost(Http.url + index, setting);
            System.out.println(index + " result:" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
