package com.my.crawler.spider;

import com.adtime.http.resource.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by yexianxun on 2016/12/1.
 */
public class WeiXinSpider {
    protected final static WebResource webResource = HttpIns.httpUrlConnection();

    public static void main(String[] args) throws IOException, InterruptedException {
        String[] cookies = {
                "SUV=1463558244925869; SMYUV=1463558244926892; IPLOC=CN3301; SUID=8E2DEC732320940A00000000575E7ADE; ssuid=441792772; CXID=33D925291ECE220A8A16169AB72E4715; ABTEST=2|1479352706|v1; LSTMV=625%2C235; LCLKINT=6356; pgv_pvi=6249703424; weixinIndexVisited=1; SNUID=882BEA760503477F9CF8F3120643E4A9; JSESSIONID=aaaa7bQcf82ZH9Perk0Iv; PHPSESSID=7lg5jlr7pu0c6d037hi091ef05; SUIR=882BEA760503477F9CF8F3120643E4A9; sct=60; seccodeRight=success; successCount=2|Thu, 01 Dec 2016 10:49:05 GMT",
                "IPLOC=CN3301; SUV=00C2179173EC2D8E57197AC54464A429; GOTO=Af99046; CXID=7F3C203CED01075753474A4CA34E20BE; pgv_pvi=5264844800; usid=MhtCGWVX8EQQIydp; wuid=AAHQupBrEwAAAAqLEm91FAgAGwY=; ssuid=3190532596; sw_uuid=8237805189; ld=BgY5$yllll2Y3H$QlllllVk$LIYllllltMZH0kllll9lllllRZlll5@@@@@@@@@@; ABTEST=1|1480052618|v1; SNUID=AD09C8502426665E94B62F56241A6B76; ad=0Zllllllll2gfk6XlllllVkMqt1llllltMZH0klllx9lllll4Oxlw@@@@@@@@@@@; SUID=8E2DEC734FC80D0A0000000057197AC5; weixinIndexVisited=1; JSESSIONID=aaaw51nnOk1L84SkZ9UIv; pgv_si=s3719351296; sct=114"};
        List<String> wxKeywords = FileUtils.readLines(new File("d://huawei_wx.txt"));
        String url = "http://weixin.sogou.com/weixin?type=1&query=${keyword}&ie=utf8";
        File file = new File("d://huawei_wx_re_" + new Date().getTime() + ".txt");
        int i = 0;
        for (String keyword : wxKeywords) {
            i++;
            Thread.sleep(1000);
            String _url = url.replace("${keyword}", keyword);
            Request request = RequestBuilder.buildRequest(_url);
            request.setMethod(Request.Method.GET);
            request.setHeader("Cookie", cookies[new Random().nextInt(2)]);
            Result result = webResource.fetchPage(request);
            Document document = result.getDocument();
            Elements elements = document.select("div.news-box ul.news-list2 li");
            int maxPage = 1;
            if (elements.size() > 0) {
                String msgPage = document.select("div.mun").text();
                if (StringUtils.isNotBlank(msgPage)) {
                    maxPage = Integer.valueOf(msgPage.replace("找到约", "").replace("条结果", "")) / 10;
                    int last = Integer.valueOf(msgPage.replace("找到约", "").replace("条结果", "")) % 10 == 0 ? 0 : 1;
                    maxPage = maxPage > 10 ? 10 : maxPage + last;
                }
                for (int p = maxPage; p >= 1; ) {
                    if (p == 1 && maxPage == 0) break;
                    for (Element element : elements) {
                        String name = element.select("div div.txt-box p a").text();
                        String id = element.select("div div.txt-box p label").text();
                        String introduce = element.select("dl dd").text();
                        String w_url = element.select("div div.txt-box p a").attr("href");
                        System.out.println(keyword + "\t" + name + "\t" + id + "\t" + w_url + "\t" + introduce);
                        FileUtils.writeStringToFile(file, keyword + "\t" + name + "\t" + id + "\t" + w_url + "\t" + introduce + "\n", true);
                    }
                    p = maxPage--;
                    if (p > 1) {

//                        result = webResource.fetchPage(_url + "&page=" + p);
                        request = RequestBuilder.buildRequest(_url + "&page=" + p);
                        request.setMethod(Request.Method.GET);
                        request.setHeader("Cookie", cookies[new Random().nextInt(2)]);
                        result = webResource.fetchPage(request);
                        document = result.getDocument();
                        elements = document.select("div.news-box ul.news-list2 li");
                    }
                }
            } else {
                System.out.println( i + "\t" + keyword);
            }
        }


    }

}
