package com.my.crawler.spider;

import com.my.crawler.processor.TaoBaoPageProcessor;
import us.codecraft.webmagic.Spider;

/**
 * Created by 叶贤勋 on 2016/7/20.
 */
public class TaoBaoSpider {

    public static void main(String[] args) {
        Spider.create(new TaoBaoPageProcessor()).addUrl("https://item.taobao.com/item.htm?id=44536725993").run();
    }
}
