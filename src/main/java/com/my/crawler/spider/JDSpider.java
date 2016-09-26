package com.my.crawler.spider;

import com.my.crawler.processor.JDPageProcessor;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * Created by 叶贤勋 on 2016/7/13.
 */
public class JDSpider {

    public static void main(String[] args) {
//        Spider.create(new JDPageProcessor()).addUrl("http://item.jd.com/2600254.html").run();
        Spider.create(new JDPageProcessor()).addPipeline(new Pipeline() {
            @Override
            public void process(ResultItems resultItems, Task task) {

            }
        }).addUrl("http://item.jd.com/1856586.html").run();
    }
}
