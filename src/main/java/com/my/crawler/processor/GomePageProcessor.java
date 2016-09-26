package com.my.crawler.processor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by 叶贤勋 on 2016/7/13.
 */
public class GomePageProcessor implements PageProcessor {
    private Site site = Site.me().setRetryTimes(1).setSleepTime(100);

    public void process(Page page) {
        page.putField("itemList", page.getHtml().xpath("//ul[@id='product-box']/li[@class='product-item']").all());
        page.putField("url", page.getUrl().get());
        page.putField("page", page.getHtml().xpath("//span[@id='min-pager-number']/text()"));
    }

    public Site getSite() {
        return site;
    }
}
