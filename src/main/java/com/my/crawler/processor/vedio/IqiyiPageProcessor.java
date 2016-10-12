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
        page.putField("authors", page.getHtml().xpath("//div[@class='result_info_cont result_info_cont-half'][2]").all());
        page.putField("introduce", page.getHtml().xpath("//div[@class='info_item'][2]/div[@class='result_info_cont result_info_cont-row1']/span[@class='result_info_txt']").all());
        page.putField("title", page.getHtml().xpath("//h3[@class='result_title']/a/@title").all());
        page.putField("pubtime", page.getHtml().xpath("//div[@class='result_info_cont result_info_cont-half'][1]/em[@class='result_info_desc']/text()").all());
        page.putField("time", page.getHtml().xpath("//span[@class='icon-vInfo']").all());
    }

    @Override
    public Site getSite() {
        return site;
    }
}
