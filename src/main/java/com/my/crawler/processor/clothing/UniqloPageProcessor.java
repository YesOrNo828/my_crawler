package com.my.crawler.processor.clothing;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by 叶贤勋 on 2016/7/13.
 */
public class UniqloPageProcessor implements PageProcessor {
    private Site site = Site.me().setRetryTimes(1).setSleepTime(100);

    public void process(Page page) {
        page.putField("title", page.getHtml().xpath("//div[@class='detail-hd']/h3/text()"));
        page.putField("price", page.getHtml().xpath("//strong[@id='J_StrPrice']//text()"));
        page.putField("size", page.getHtml().xpath("//div[@class='skin']/dl[@class='prop clearfix'][1]/dd/ul[@class='clearfix J_ulSaleProp']"));
        page.putField("color", page.getHtml().xpath("//div[@class='key']/div[@class='skin']/dl[@class='prop clearfix'][2]/dd/ul[@class='clearfix J_ulSaleProp img']"));
        // 异步
        page.putField("view", page.getHtml().xpath("//div[@class='property']/div[@class='wrap']/ul[@class='other clearfix']/li[1]/em"));
        page.putField("goodType", page.getHtml().xpath("//div[@class='property']/div[@class='wrap']/ul[@class='other clearfix']/li[2]/em"));
        page.putField("detail", page.getHtml().xpath("//div[@id='detail']/div[@id='attributes']/ul[@class='attributes-list']").all());


    }

    public Site getSite() {
        return site;
    }
}
