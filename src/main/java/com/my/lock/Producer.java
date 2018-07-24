package com.my.lock;

/**
 * Created by yexianxun@corp.netease.com on 2018/3/29.
 */
public class Producer implements Runnable {
    private QueueBuffer q;

    Producer(QueueBuffer q) {
        this.q = q;
        new Thread(this, "Producer").start();
    }

    public void run() {
        int i = 0;
        while (true) {
            q.put(i++);
        }
    }
}
