package com.my.crawler.spider.clothing;

import com.my.crawler.processor.clothing.UniqloPageProcessor;
import us.codecraft.webmagic.Spider;

/**
 * Created by 叶贤勋 on 2016/7/13.
 */
public class UniqloSpider {

    public static void main(String[] args) {
        Spider.create(new UniqloPageProcessor()).addUrl("http://www.uniqlo.cn/item.htm?id=538340556763").run();
    }
}
