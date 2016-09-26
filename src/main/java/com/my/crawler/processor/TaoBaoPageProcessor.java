package com.my.crawler.processor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by 叶贤勋 on 2016/7/20.
 */
public class TaoBaoPageProcessor implements PageProcessor {
    private Site site = Site.me().setRetryTimes(1).setSleepTime(100);

    public void process(Page page) {
        page.putField("title", page.getHtml().xpath("//h3[@class='tb-main-title']/text()"));
        page.putField("title2", page.getHtml().xpath("//div[@id='J_Title']/p[@class='tb-subtitle']"));
        page.putField("shop", page.getHtml().xpath("//div[@class='tb-shop-name']/dl/dd/strong/a"));
        page.putField("shopId", page.getHtml().xpath("//meta[@name='microscope-data']"));
    }

    public Site getSite() {
        return site;
    }
}
