package com.my.lock;

/**
 * Created by yexianxun@corp.netease.com on 2018/3/29.
 */
public class Main {
    public static void main(String[] args) {
        QueueBuffer q = new QueueBuffer();
        new Producer(q);
        new Consumer(q);
        System.out.println("Press Control-C to stop.");
    }
}
