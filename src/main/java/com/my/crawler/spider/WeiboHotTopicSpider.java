package com.my.crawler.spider;

import com.adtime.http.resource.*;
import org.jsoup.nodes.Document;

import java.util.Random;

/**
 * 微博热门话题
 * Created by yexianxun on 2016/12/2.
 */
public class WeiboHotTopicSpider {
    protected final static WebResource webResource = HttpIns.httpUrlConnection();
    private static String[] cookies = {
            "SINAGLOBAL=9382709942292.422.1461217733831; wvr=6; un=dejiajishu@sina.com; SCF=AkkPSFKbKXXrO2gTO3Ot2A6CWdlHTSaoGlGYNhSZpOyJDEWnnmTJ4mQc7JjUC6apml_8bGQjaRxXhIqw8oo_Y2Q.; SUB=_2A251RKwoDeTxGeNL61sU8CfKyjSIHXVWM5rgrDV8PUNbmtBeLVXukW8x14VV6A1ywkGvrwd0zFNuEp56oQ..; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WWc5ysxFKfv77v3m88Ikr.U5JpX5KMhUgL.Fo-feh.feh.ceKn2dJLoIEQLxK-LB.-LBoMLxK-L1KnL1h.LxKqL122L1h5LxKqL1-eLB-9Dxsvr9c2t; SUHB=0pHhB2puIZGrRI; ALF=1512181752; SSOLoginState=1480645752; _s_tentry=login.sina.com.cn; Apache=8478888210512.874.1480645627683; ULV=1480645627977:10:3:5:8478888210512.874.1480645627683:1480581894696; UOR=,,club.huawei.com; YF-Page-G0=bf52586d49155798180a63302f873b5e"
            };

    public static void main(String[] args) {
        String url = "http://d.weibo.com/100803_ctg1_101_-_ctg1101?cfs=920&Pl_Discover_Pt6Rank__4_filter=&Pl_Discover_Pt6Rank__4_page=${page}";
        int page = 1, maxPage = 1;
        do {
            String _url = url.replace("${page}", String.valueOf(page));
            Request request = RequestBuilder.buildRequest(_url);
            request.setMethod(Request.Method.GET);
            request.setHeader("Cookie", cookies[new Random().nextInt(1)]);
            Result result = webResource.fetchPage(request);
            Document document = result.getDocument();
            System.out.println(document.select("div.m_wrap clearfix ul li"));
            page++;
        } while (page <= maxPage);
    }
}
