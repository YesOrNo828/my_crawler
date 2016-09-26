package com.my.crawler.processor.vedio;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by yexianxun on 2016/9/8.
 */
public class YoukuPageProcessor implements PageProcessor {
    private Site site = Site.me().setRetryTimes(1).setSleepTime(100);

    @Override
    public void process(Page page) {
        page.putField("title", page.getHtml().xpath("//div[@class='v']/div[@class='v-meta va']/div[@class='v-meta-title']/a/@title").all());
        page.putField("view", page.getHtml().xpath("//div[@class='v']/div[@class='v-meta va']/div[@class='v-meta-entry']/div[@class='v-meta-data'][2]/span[@class='pub']/text()").all());
        page.putField("pubtime", page.getHtml().xpath("//div[@class='v']/div[@class='v-meta va']/div[@class='v-meta-entry']/div[@class='v-meta-data'][2]/span[@class='r']/text()").all());
        page.putField("playTimes", page.getHtml().xpath("//div[@class='v']/div[@class='v-thumb']/div[@class='v-thumb-tagrb']/span/text()").all());
        page.putField("authors", page.getHtml().xpath("//div[@class='v']/div[@class='v-meta va']/div[@class='v-meta-entry']/div[@class='v-meta-data'][1]/span/a/text()").all());
//        page.addTargetRequest(new Request());
    }

    @Override
    public Site getSite() {
        return site;
    }
}
