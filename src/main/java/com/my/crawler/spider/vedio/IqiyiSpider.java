package com.my.crawler.spider.vedio;

import com.my.crawler.processor.vedio.IqiyiPageProcessor;
import com.my.crawler.processor.vedio.YoukuPageProcessor;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

/**
 * Created by yexianxun on 2016/9/8.
 */
public class IqiyiSpider {

    public static void main(String[] args) {
        Spider.create(new IqiyiPageProcessor()).addPipeline(new Pipeline() {
            @Override
            public void process(ResultItems resultItems, Task task) {
                List<String> titles = resultItems.get("title");
                List<String> views = resultItems.get("view");
                List<String> pubtimes = resultItems.get("pubtime");
                List<String> playTimes = resultItems.get("playTimes");
                List<String> authors = resultItems.get("authors");
                for (int i = 0; i < titles.size(); i++) {
                    System.out.println((i + 1) + "\t" + titles.get(i) + "\t" + views.get(i) + "\t" + pubtimes.get(i) + "\t" + playTimes.get(i) + "\t" + authors.get(i));
                }
            }
        }).addUrl("http://so.iqiyi.com/so/q_iphone_ctg__t_0_page_20_p_1_qc_0_rd__site__m_4_bitrate_").run();
    }

}
