package com.my.crawler;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 输入：类目id
 * 输出：商品数量
 * 媒体：国美
 * Created by yexianxun on 2016/8/11.
 */
public class GomeCount {

    public static void main(String[] args) throws IOException {
        List<String> urls = readFromGome("D:\\tmp\\ec\\gome.txt");
        urls.stream().forEach(url -> {
            String bef = "http://list.gome.com.cn/cloud/asynSearch?callback=callback_product&module=product&from=category&page=23&sorts=0&paramJson=%7B+%22mobile%22+%3A+false+%2C+%22catId%22+%3A+%22";
            String aft = "%22+%2C+%22shopId%22+%3A+%22%22+%2C+%22regionId%22+%3A+%2222050200%22+%2C+%22pageName%22+%3A+%22list%22+%2C+%22et%22+%3A+%22%22+%2C+%22XSearch%22+%3A+false+%2C+%22pageSize%22+%3A+48+%2C+%22promoFlag%22+%3A+0+%2C+%22instock%22+%3A+0+%2C+%22sale%22+%3A+%220%22+%2C+%22cookieId%22+%3A+%22146892232119779769%22+%2C+%22market%22+%3A+10+%2C+%22productTag%22+%3A+0+%2C+%22sorts%22+%3A+%5B+%5D+%2C+%22priceTag%22+%3A+0+%2C+%22tuanFlag%22+%3A+%220%22+%2C+%22qiangFlag%22+%3A+%220%22+%2C+%22saleFlag%22+%3A+%220%22+%2C+%22listTag%22+%3A+true+%2C+%22rawqustion%22+%3A+%22%22%7D&_=1470897109070";
            try {
                int count = getUrlTotalCount(bef + url + aft);
                System.out.println(url + "\t" + count);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private static List<String> readFromGome(String path) throws IOException {
        List<String> list = FileUtils.readLines(new File(path), "utf-8");
        return list;
    }

    public static int getUrlTotalCount(String url) throws IOException {
        HttpGet get = new HttpGet(url);
        HttpClientBuilder builder = HttpClientBuilder.create();
        CloseableHttpClient client = builder.build();
        CloseableHttpResponse response = client.execute(get);
        HttpEntity entity = response.getEntity();
        String result = EntityUtils.toString(entity, "utf-8");
        result = result.substring(17, result.length() - 1);
        JSONObject json = JSONObject.parseObject(result);
        int count = (int) ((JSONObject) json.get("pageBar")).get("totalCount");
        return count;
    }
}
