package com.my.esindex;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by yexianxun on 2016/8/24.
 */
public class MonthIndex {
    private static String settingGome = null;
    public static String gome = "gome";
    private static String settingSuning = null;
    public static String suning = "suning";
    private static String settingVideo = null;
    public static String video = "video";
    public static String mia = "mia";
    public static String vip = "vip";
    private static String settingVip = null;


    static {
        try {
            settingSuning = FileUtils.readFileToString(new File(MonthIndex.class.getResource("/").getFile() + suning + ".txt"));
            settingGome = FileUtils.readFileToString(new File(MonthIndex.class.getResource("/").getFile() + gome + ".txt"));
            settingVideo = FileUtils.readFileToString(new File(MonthIndex.class.getResource("/").getFile() + video + ".txt"));
            settingVip = FileUtils.readFileToString(new File(MonthIndex.class.getResource("/").getFile() + vip + ".txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        createMonthIndex(gome, settingGome);
//        createMonthIndex(suning, settingSuning);
        createVideoIndex(video, settingVideo);
    }

    private static void createMonthIndex(String type, String setting) {
        String index = getIndexName(type);
        try {
            String result = Http.doPost(Http.url + index, setting);
            System.out.println(index + " result:" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getIndexName(String type) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, 1);
        int m = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        return "ec_" + type + "_" + year + "_m" + (m + 1);
    }

    private static void createVideoIndex(String type, String setting) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, 1);
        int m = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        String indexName = type + "_" + year + "_" + (m );
        try {
            String result = Http.doPost(Http.url + indexName, setting);
            System.out.println(indexName + " result:" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
