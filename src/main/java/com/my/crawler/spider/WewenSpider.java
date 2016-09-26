package com.my.crawler.spider;

import com.my.crawler.processor.WewenPageProcessor;
import us.codecraft.webmagic.Spider;

/**
 * Created by yexianxun on 2016/8/3.
 */
public class WewenSpider {

    public static void main(String[] args) {
//        Spider.create(new JDPageProcessor()).addUrl("http://item.jd.com/2600254.html").run();
        Spider.create(new WewenPageProcessor()).addUrl("http://wewen.io/articles?author=hikvisionsecu").run();
    }
}
