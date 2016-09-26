package com.my.crawler.processor.vedio;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by yexianxun on 2016/9/8.
 */
public class TudouPageProcessor implements PageProcessor {
    private Site site = Site.me().setRetryTimes(1).setSleepTime(100);

    @Override
    public void process(Page page) {
        page.putField("dianZan", page.getHtml().xpath("//span[@class='btn active']/span[@class='num']"));
        page.putField("title", page.getHtml().xpath("//div[@id='summary']/div[@class='summary_main']/div[@class='fix']/h1[@id='videoKw']/text()"));
        page.putField("playTimes", page.getHtml().xpath("//div[@class='txt']/ul[@class='info']/li/span"));
        page.putField("commentTimes", page.getHtml().xpath("//div[@id='actionComments']/a[@class='btn']/i[@class='num']"));
        page.putField("authorAndPubTime", page.getHtml().xpath("//div[@id='gContainer']/div[@class='page-container']/div[@class='page_wrap']/div[@class='auto fix mt_32']/div[@class='con_main']/div[@class='information_t fix']/div[@class='v_user']"));
    }

    @Override
    public Site getSite() {
        return site;
    }
}
