package com.my.crawler.lock;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by yexianxun on 2016/8/4.
 */
public class LockDemo {
    private static final ThreadPoolExecutor exportExecutorService = new ThreadPoolExecutor(5, 5, 30, TimeUnit.MINUTES, new LinkedBlockingQueue<>());

    public static void main(String[] args) {
        int i = 0;
        do {
            PersonThread thread = new PersonThread("Li" + i);
            thread.run();
        } while (++i < 1000000);
    }

    static class PersonThread implements Runnable {
        private String name;

        public PersonThread(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            new Person(name).say();
        }
    }


}
