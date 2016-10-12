package com.my.crawler.spider.vedio;

import com.my.crawler.processor.vedio.DianPingPageProcessor;
import us.codecraft.webmagic.Spider;

/**
 * Created by yexianxun on 2016/9/8.
 */
public class DianPingSpider {

    public static void main(String[] args) {
        Spider.create(new DianPingPageProcessor()).addUrl("http://www.dianping.com/search/category/3/30/g136").run();

    }


}
