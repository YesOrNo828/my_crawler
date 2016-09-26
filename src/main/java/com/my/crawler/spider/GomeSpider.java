package com.my.crawler.spider;

import com.my.crawler.processor.GomePageProcessor;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

/**
 * Created by yexianxun on 2016/8/11.
 */
public class GomeSpider {

    public static void main(String[] args) {
        Spider.create(new GomePageProcessor()).addPipeline(new Pipeline() {
            @Override
            public void process(ResultItems resultItems, Task task) {
                List<String> list = resultItems.get("itemList");
                int page = Integer.valueOf(resultItems.get("page").toString().substring(2));
                if (page == 1) {
                    System.out.println(resultItems.get("url").toString() + "\t" + list.size());
                } else {

                }

            }
        }).addUrl("http://list.gome.com.cn/cat15985757-00-0-48-0-0-0-0-1-0-0-0-0-0-0-0-0-0.html").
                addUrl("http://list.gome.com.cn/cat21755542-00-0-48-0-0-0-0-1-0-0-0-0-0-0-0-0-0.html").
                run();
    }

}
