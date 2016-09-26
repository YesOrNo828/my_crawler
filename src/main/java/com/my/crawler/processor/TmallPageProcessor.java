package com.my.crawler.processor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by 叶贤勋 on 2016/7/20.
 */
public class TmallPageProcessor implements PageProcessor {
    private Site site = Site.me().setRetryTimes(1).setSleepTime(100);

    public void process(Page page) {
        page.putField("title", page.getHtml().xpath("//div[@class='tb-detail-hd']/h1/a/text()"));
        page.putField("title2", page.getHtml().xpath("//div[@class='tb-detail-hd']/p/text()"));
        page.putField("renqi", page.getHtml().xpath("//span[@id='J_CollectCount']/text()"));
        page.putField("shop", page.getHtml().xpath("//div[@id='shopExtra']/div[@class='slogo']/a[@class='slogo-shopname']/strong"));
        page.putField("shopUrl", page.getHtml().xpath("//div[@id='shopExtra']/div[@class='slogo']/a[@class='slogo-shopname']"));
        page.putField("shopId", page.getHtml().xpath("//div[@id='LineZing']"));
    }

    public Site getSite() {
        return site;
    }
}
