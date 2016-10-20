package com.my.crawler;

import org.apache.commons.io.FileUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 输入：类目id
 * 输出：商品数量
 * 媒体：京东
 * Created by yexianxun on 2016/8/11.
 */
public class JDCount {

    public static void main(String[] args) throws IOException {
        List<String> urls = FileUtils.readLines(new File("D:\\tmp\\ec\\jd.txt"), "utf-8");
        getUrlTotalCount(urls);
    }

    public static void getUrlTotalCount(List<String> urls) throws IOException {
        Spider spider = Spider.create(new JdPageProcessor()).addPipeline((resultItems, task) ->
                System.out.println(resultItems.get("url") + "\t" + resultItems.get("count")));
        urls.stream().forEach(url -> spider.addUrl(url));
        spider.run();
    }

    static class JdPageProcessor implements PageProcessor {
        private Site site = Site.me().setRetryTimes(1).setSleepTime(100);

        public void process(Page page) {
            page.putField("url", page.getUrl().get());
            page.putField("count", page.getHtml().xpath("//div[@id='J_selector']/div[@class='s-title']/div[@class='st-ext']/span/text()"));
        }

        public Site getSite() {
            return site;
        }
    }
}
