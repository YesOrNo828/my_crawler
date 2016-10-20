package com.my.java;

/**
 * Created by yexianxun on 2016/10/20.
 */
public class HProfTest {
    public void slowMethod() {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void slowerMethod() {
        try {
            Thread.sleep(10000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        HProfTest test = new HProfTest();
        test.slowerMethod();
        test.slowMethod();
    }
}
