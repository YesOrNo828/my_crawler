package com.my.crawler.spider.vedio;

import com.my.crawler.processor.vedio.YoukuPageProcessor;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

/**
 * Created by yexianxun on 2016/9/8.
 */
public class YoukuSpider {

    public static void main(String[] args) {
        Spider.create(new YoukuPageProcessor()).addPipeline(new Pipeline() {
            @Override
            public void process(ResultItems resultItems, Task task) {
                List<String> titles = resultItems.get("title");
                List<String> views = resultItems.get("view");
                List<String> pubtimes = resultItems.get("pubtime");
                List<String> playTimes = resultItems.get("playTimes");
                for (int i = 0; i < titles.size(); i++) {
                    System.out.println((i + 1) + "\t" + titles.get(i) + "\t" + views.get(i) + "\t" + pubtimes.get(i) + "\t" + playTimes.get(i));
                }
            }
        }).addUrl("http://www.soku.com/search_video/q_iphone_orderby_2_limitdate_0?page=1").
                addUrl("http://www.soku.com/search_video/q_iphone_orderby_2_limitdate_0?page=2").run();
    }

}
