package com.my.crawler.spider.vedio;

import com.my.crawler.processor.vedio.IqiyiPageProcessor;
import com.my.crawler.processor.vedio.YoukuPageProcessor;
import us.codecraft.webmagic.*;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

/**
 * Created by yexianxun on 2016/9/8.
 */
public class IqiyiSpider {

    public static void main(String[] args) {
        // list
//        Spider.create(new IqiyiPageProcessor()).addUrl("http://so.iqiyi.com/so/q_iphone7_ctg__t_0_page_1_p_1_qc_0_rd__site_iqiyi_m_1_bitrate_").run();

        // content
        Spider.create(new ContentIqiyiPageProcessor()).addUrl("http://www.iqiyi.com/w_19rstfuzy9.html#vfrm=2-3-0-1").run();
    }

    static class ContentIqiyiPageProcessor implements PageProcessor{
        private Site site = Site.me().setRetryTimes(1).setSleepTime(100);

        public ContentIqiyiPageProcessor() {
        }

        @Override
        public void process(Page page) {
            page.putField("category", page.getHtml().xpath("//div[@class='mod-crumb_ft']/div[@class='mod-crumb_bar'][1]/span[@id='widget-videonav thirdPartyTagList']").all());
            // 播放次数
            page.putField("playTimes", page.getHtml().xpath("//div[@id='playcountWrapper']/a[@id='chartTrigger']/span[@id='widget-playcount']").all());
            // 评论条数
            page.putField("comments", page.getHtml().xpath("//div[@class='cs-feed-hd']/ul/li[@class='selected']/em/i").all());
            // tags
            page.putField("tags", page.getHtml().xpath("//span[@id='widget-videotag']/a/text()").all());
            // 点赞数/点踩数
            page.putField("voteUp", page.getHtml().xpath("//span[@id='widget-voteupcount']").all());
            page.putField("voteDown", page.getHtml().xpath("//span[@id='widget-votedowncount']").all());
        }

        @Override
        public Site getSite() {
            return site;
        }
    }

}
