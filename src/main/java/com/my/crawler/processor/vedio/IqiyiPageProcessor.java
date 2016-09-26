package com.my.crawler.processor.vedio;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by yexianxun on 2016/9/8.
 */
public class IqiyiPageProcessor implements PageProcessor {
    private Site site = Site.me().setRetryTimes(1).setSleepTime(100);

    @Override
    public void process(Page page) {
        page.putField("title", page.getHtml().xpath("//h3[@class='result_title']/a/@title").all());
//        page.putField("view", page.getHtml().xpath("//div[@class='v']/div[@class='v-meta va']/div[@class='v-meta-entry']/div[@class='v-meta-data'][2]/span[@class='pub']/text()").all());
        page.putField("pubtime", page.getHtml().xpath("//div[@class='result_info_cont result_info_cont-half'][1]/em[@class='result_info_desc']/text()").all());
        page.putField("playTimes", page.getHtml().xpath("//a[@class='figure figure-180101']/p/span/text()").all());
        page.putField("authors", page.getHtml().xpath("//div[@class='result_info_cont result_info_cont-half'][2]").all());
    }

    @Override
    public Site getSite() {
        return site;
    }
}
