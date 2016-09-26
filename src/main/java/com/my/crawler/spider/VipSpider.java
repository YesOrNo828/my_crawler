package com.my.crawler.spider;

import com.my.crawler.processor.VipDetailPageProcessor;
import us.codecraft.webmagic.Spider;

/**
 * Created by 叶贤勋 on 2016/7/14.
 */
public class VipSpider {

    public static void main(String[] args) {
        Spider.create(new VipDetailPageProcessor()).addUrl("http://www.vip.com/detail-799738-111233366.html").run();
//        Spider.create(new VipDetailPageProcessor()).addUrl("http://www.vip.com/detail-800467-110812560.html").run();
    }
}
