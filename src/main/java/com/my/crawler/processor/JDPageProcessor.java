package com.my.crawler.processor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by 叶贤勋 on 2016/7/13.
 */
public class JDPageProcessor implements PageProcessor {
    private Site site = Site.me().setRetryTimes(1).setSleepTime(100);

    public void process(Page page) {
        page.putField("title", page.getHtml().xpath("//div[@class='sku-name']/text()"));
        page.putField("title2", page.getHtml().xpath("//div[@id='p-ad']/text()"));
        page.putField("price", page.getHtml().xpath("//span[@class='price J-p-2600254']//text()"));
        page.putField("introduce", page.getHtml().xpath("//ul[@class='parameter2 p-parameter-list']//text()"));
        page.putField("navigation", page.getHtml().xpath("//div[@class='crumb fl clearfix']//text()"));
        page.putField("sku", page.getHtml().xpath("//div[contains(@class, 'sku')]/span/text()"));
        page.putField("detail", page.getHtml().xpath("//div[@class='p-parameter']/ul/li").all());
    }

    public Site getSite() {
        return site;
    }
}
