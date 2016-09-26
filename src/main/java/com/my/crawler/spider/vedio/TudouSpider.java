package com.my.crawler.spider.vedio;

import com.my.crawler.processor.vedio.TudouPageProcessor;
import us.codecraft.webmagic.Spider;

/**
 * Created by yexianxun on 2016/9/8.
 */
public class TudouSpider {
    public static void main(String[] args) {
        Spider.create(new TudouPageProcessor()).
                addUrl("http://www.tudou.com/listplay/_U-i1b7bBmA/T9e10JoPQuc.html").
                addUrl("http://www.tudou.com/listplay/_U-i1b7bBmA/T9e10JoPQuc.html").
                run();
    }
}
