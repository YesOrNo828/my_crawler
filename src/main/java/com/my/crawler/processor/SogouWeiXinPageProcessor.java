package com.my.crawler.processor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by yexianxun on 2016/12/1.
 */
public class SogouWeiXinPageProcessor implements PageProcessor {
    private Site site = Site.me().setRetryTimes(1).setSleepTime(100);

    public void process(Page page) {

        page.putField("title", page.getHtml().xpath("//ul[@class='news-list2']/li/div[@class='gzh-box2']/div[@class='txt-box']/p[@class='tit']/a/text()").all());
    }

    public Site getSite() {
        return site;
    }
}
