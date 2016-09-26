package com.my.crawler.pipeline;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

/**
 * Created by yexianxun on 2016/8/3.
 */
public class WewenPipeline implements Pipeline {


    public void process(ResultItems resultItems, Task task) {
        List<String> titles = resultItems.get("title");
    }
}
