package com.my.crawler.processor.vedio;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by yexianxun on 2016/9/8.
 */
public class DianPingPageProcessor implements PageProcessor {
    private Site site = Site.me().setRetryTimes(1).setSleepTime(100);

    @Override
    public void process(Page page) {
        page.putField("title", page.getHtml().xpath("//div[@class='txt']/div[@class='tit']/a/h4").all());
        page.putField("crumb", page.getHtml().xpath("//div[@class='section Fix']/div[@class='bread J_bread']").all());
    }

    @Override
    public Site getSite() {
        return site;
    }
}
