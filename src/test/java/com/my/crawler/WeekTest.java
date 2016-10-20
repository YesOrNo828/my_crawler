package com.my.crawler;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by yexianxun on 2016/8/24.
 */
public class WeekTest {
    public static void main(String[] args) {
        System.out.println(new SimpleDateFormat("yyyyWW"));
        System.out.println();
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        System.out.println(calendar.get(Calendar.WEEK_OF_YEAR));

    }
}
