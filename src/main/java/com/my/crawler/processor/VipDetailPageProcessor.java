package com.my.crawler.processor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by 叶贤勋 on 2016/7/14.
 */
public class VipDetailPageProcessor implements PageProcessor {
    private Site site = Site.me().setRetryTimes(1).setSleepTime(100);

    public void process(Page page) {
        page.putField("title", page.getHtml().xpath("//p[@class='pib-title-detail']/text()"));
        page.putField("price", page.getHtml().xpath("//span[@class='pbox-price showthis']/em[@class='J-price']/text()"));
        page.putField("ori_price", page.getHtml().xpath("//span[@class='pbox-market']/del/text()"));
        page.putField("off_price", page.getHtml().xpath("//span[@class='pbox-off J-discount']/text()"));
        page.putField("trans_fee", page.getHtml().xpath("//em[@class='freight-free']/text()"));
        page.putField("vip_num", page.getHtml().xpath("//p[@class='vip-currency-title-v1']/em/text()"));
        page.putField("detail", page.getHtml().xpath("//table[@class='dc-table fst']/tbody/tr/td").all());
    }

    public Site getSite() {
        return site;
    }
}
