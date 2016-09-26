package com.my.crawler.spider;

import com.my.crawler.processor.JDPageProcessor;
import com.my.crawler.processor.QianJiaPageProcessor;
import us.codecraft.webmagic.Spider;

/**
 * Created by 叶贤勋 on 2016/7/13.
 */
public class QianJiaSpider {

    public static void main(String[] args) {
        Spider.create(new QianJiaPageProcessor()).addUrl("http://bbs.qianjia.com/forum.php?mod=viewthread&tid=612932&extra=page%3D1").run();
    }
}
