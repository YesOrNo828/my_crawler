package com.my.crawler.lock;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by yexianxun on 2016/8/4.
 */
public class Person {
    private String name;

    public Person() {
    }

    public Person(String name) {
        this.name = name;
    }

    public synchronized void say() {
        Lock lock = new ReentrantLock();
//        lock.lock();
//        try {
//            TimeUnit.SECONDS.sleep(1);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        System.out.println(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + " " + name + ".say");
//        lock.unlock();
    }
}
