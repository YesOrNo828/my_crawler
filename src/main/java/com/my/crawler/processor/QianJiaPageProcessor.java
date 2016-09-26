package com.my.crawler.processor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by 叶贤勋 on 2016/7/13.
 */
public class QianJiaPageProcessor implements PageProcessor {
    private Site site = Site.me().setRetryTimes(1).setSleepTime(100);

    public void process(Page page) {
        page.putField("title", page.getHtml().xpath("/html/body[@id='nv_forum']/div[@id='wp']/div[@id='ct']/div[@class='mn']/div[@id='postlist']"));
    }

    public Site getSite() {
        return site;
    }
}
