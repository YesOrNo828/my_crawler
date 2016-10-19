package com.my.crawler.spider;

import com.adtime.http.resource.HttpIns;
import com.adtime.http.resource.Result;
import com.adtime.http.resource.WebResource;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by yexianxun on 2016/10/19.
 */
public class Che168Spider {

    protected final static WebResource webResource = HttpIns.httpUrlConnection();
    private static String url_bef = "http://www.che168.com/hangzhou/a0_0msdgscncgpiltocsp";
    private static String url_aft = "exb104y48x0/";


    public static void main(String[] args) {
//        Spider.create(new Che168PageProcessor()).addUrl("http://www.che168.com/hangzhou/a0_1msdgscncgpiltocsp1exb1x0/").run();
        int maxPage = 1, page = 1;
        String url = url_bef + page + url_aft;
        Result result = webResource.fetchPage(url);
        while (page <= maxPage) {
            Document document = result.getDocument();
            int total = Integer.valueOf(document.select(".sequence p span").text());
            maxPage = total % 48 == 0 ? total / 48 : (total / 48 + 1);
            Elements elements = document.select("#viewlist_ul li div.info-con");
            for (Element element : elements) {
                String title = element.select("h3").text();
                String use = element.select("p").text();
                String mileage = use.split("/")[0].split("／")[0];
                String licenseYear = use.split("/")[0].split("／")[1];
                String person = use.split("/")[1].trim().split(" ")[0];
                String adv = "";
                if (use.split("/")[1].trim().split(" ").length > 1) {
                    adv = use.split("/")[1].trim().split(" ")[1];
                }
                String money = element.select("ins").text();
                String location = element.select("div.location").text();
                Elements elementsSpan = element.select("div.icon-area span");
                String area = "";
                for (Element span : elementsSpan) {
                    area += " " + span.attr("title");
                }
                System.out.println(title + "\t" + mileage + "\t" + licenseYear + "\t" + person + "\t" + adv + "\t"  + money + "\t"  + location + "\t"  + area.trim() + "\t");
            }
            page++;
            url = url_bef + page + url_aft;
            result = webResource.fetchPage(url);
        }
        System.out.println("finished");
    }

    static class Che168PageProcessor implements PageProcessor {
        private Site site = Site.me().setRetryTimes(1).setSleepTime(100);

        public void process(Page page) {
            page.putField("title", page.getHtml().xpath("//ul[@id='viewlist_ul']/li/div[@class='info-con']/h3/a").all());
            page.putField("msg", page.getHtml().xpath("//ul[@id='viewlist_ul']/li/div[@class='info-con']/p").all());
            page.putField("money", page.getHtml().xpath("//ul[@id='viewlist_ul']/li/div[@class='info-con']/ins").all());
            page.putField("location", page.getHtml().xpath("//ul[@id='viewlist_ul']/li/div[@class='info-con']/div[@class='location']").all());
            page.putField("iconArea", page.getHtml().xpath("//ul[@id='viewlist_ul']/li/div[@class='info-con']/div[@class='icon-area']/span/@title").all());
        }

        public Site getSite() {
            return site;
        }
    }
}
