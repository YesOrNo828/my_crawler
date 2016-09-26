package com.my.crawler.processor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by 叶贤勋 on 2016/7/14.
 */
public class WewenPageProcessor implements PageProcessor {
    private Site site = Site.me().setRetryTimes(1).setSleepTime(100);

    public void process(Page page) {
        page.putField("title", page.getHtml().xpath("//div[@class='ui very relaxed divided unstackable items']/div[@class='item']/div[@class='content']/a[@class='header']/text()").all());
        page.putField("desc", page.getHtml().xpath("//div[@class='ui very relaxed divided unstackable items']/div[@class='item']/div[@class='content']/div[@class='description']/p/text()").all());
        page.putField("view", page.getHtml().xpath("//div[@class='ui very relaxed divided unstackable items']/div[@class='item']/div[@class='content']/div[@class='extra']/div[@class='ui horizontal  link list']/div[@class='item'][1]/text()").all());
        page.putField("collect", page.getHtml().xpath("//div[@class='ui very relaxed divided unstackable items']/div[@class='item']/div[@class='content']/div[@class='extra']/div[@class='ui horizontal  link list']/div[@class='item'][2]/text()").all());
        page.putField("pub_time", page.getHtml().xpath("//div[@class='ui very relaxed divided unstackable items']/div[@class='item']/div[@class='content']/div[@class='extra']/div[@class='ui horizontal  link list']/div[@class='item'][3]/text()").all());
    }

    public Site getSite() {
        return site;
    }
}
