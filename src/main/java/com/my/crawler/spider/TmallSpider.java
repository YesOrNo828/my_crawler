package com.my.crawler.spider;

import com.my.crawler.processor.TmallPageProcessor;
import us.codecraft.webmagic.Spider;

/**
 * Created by 叶贤勋 on 2016/7/20.
 */
public class TmallSpider {

    public static void main(String[] args) {
        Spider.create(new TmallPageProcessor()).addUrl("https://detail.tmall.com/item.htm?id=523029919958").run();
        Spider.create(new TmallPageProcessor()).addUrl("https://detail.tmall.com/item.htm?id=523029919958").run();
    }
}
