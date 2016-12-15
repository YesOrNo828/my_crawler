package com.my.crawler.spider;

import com.adtime.http.resource.*;
import org.apache.commons.io.FileUtils;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yexianxun on 2016/12/14.
 */
public class BaiduSpider {
    protected final static WebResource webResource = HttpIns.httpUrlConnection();
    protected final static String[] cookies = {
            "BIDUPSID=A1910778DFD688A2783063010FF804A7; BAIDUID=7C32A6C867D08889D6F41BE14A745298:FG=1; PSTM=1463969279; BDUSS=3ZQY3ZMblJPNm5RV0pSNzB5bi1kQmVxOWk2WGxJQmVzQTdOaDdRYTlxTWJRelpZSVFBQUFBJCQAAAAAAAAAAAEAAAAJfOcreWVzb3JubzQyMwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABu2Dlgbtg5YN; MCITY=-179%3A; uc_login_unique=d5dcc4893aa243b380313ad16099dbf3; pgv_pvi=6213217280; BD_HOME=1; BDRCVFR[feWj1Vr5u3D]=I67x6TjHwwYf0; BD_CK_SAM=1; PSINO=5; H_PS_PSSID=1448_13548_21114_18134_17001_21805_21553_21408_21673_20929; BD_UPN=12314353; sug=3; sugstore=0; ORIGIN=2; bdime=0; H_PS_645EC=6f56uIlaOB%2FGsbaAVWn2rMU23IwAEXSGNUIvGwRXGpknBALNr%2BPp5j1E9K4",
    };


    public static void main(String[] args) throws IOException {
        String url = "https://www.baidu.com/s?wd=$keyword&rsv_spt=1&rsv_iqid=0x97dbfefa00022d36&issp=1&f=8&rsv_bp=1&rsv_idx=2&ie=utf-8&rqlang=cn&tn=baiduhome_pg&rsv_enter=0&rsv_t=190bY2B3VGhnGFzC4iV7oJSes7nAHN%2BXql2Ut4BFAZjGF8Xs4KiG%2BVpamqjPO8N4FtMM&oq=$keyword&rsv_pq=d4857f1f00028d66&rsv_sug3=4&rsv_sug4=786";
        List<String> companyNames = FileUtils.readLines(new File("d://tmp//companys.txt"));
        List<String> lastUrls = new ArrayList<>(companyNames.size());
        for (int i = 0; i < companyNames.size(); i++) {
            String keyword = URLEncoder.encode(companyNames.get(i), "UTF-8");
            String _url = url.replace("$keyword", keyword);
            Result result = request(_url);
            if (result.getStatus() == 301) {
                result = request(result.getMoveToUrl());
            }
            Document document = result.getDocument();
            String baiduUrl = null;
            try {
                baiduUrl = document.select("div#content_left div h3.t a").get(0).attr("href");
            } catch (Exception e) {
                System.out.println(companyNames.get(i) + "\t");
                lastUrls.add(companyNames.get(i) + "\n");
                e.printStackTrace();
                continue;
            }
            String real = getRealUrl(baiduUrl);
            System.out.println(i + companyNames.get(i) + "\t" + real);
            lastUrls.add(companyNames.get(i) + "\t" + real + "\n");
        }
        long d = System.currentTimeMillis();
        lastUrls.stream().forEach(u -> {
                    try {
                        FileUtils.writeStringToFile(new File("d://tmp/company_" + d + ".txt"), u, "utf-8", true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    private static Result request(String url) {
        Request request = RequestBuilder.buildRequest(url);
        request.setMethod(Request.Method.GET);
        request.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        request.setHeader("Accept-Encoding", "gzip, deflate, sdch, br");
        request.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
        request.setHeader("Connection", "keep-alive");
        request.setHeader("Cookie", "BIDUPSID=A1910778DFD688A2783063010FF804A7; BAIDUID=7C32A6C867D08889D6F41BE14A745298:FG=1; PSTM=1463969279; BDUSS=3ZQY3ZMblJPNm5RV0pSNzB5bi1kQmVxOWk2WGxJQmVzQTdOaDdRYTlxTWJRelpZSVFBQUFBJCQAAAAAAAAAAAEAAAAJfOcreWVzb3JubzQyMwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABu2Dlgbtg5YN; MCITY=-179%3A; uc_login_unique=d5dcc4893aa243b380313ad16099dbf3; pgv_pvi=6213217280; BD_HOME=1; BDRCVFR[feWj1Vr5u3D]=I67x6TjHwwYf0; BD_CK_SAM=1; PSINO=5; H_PS_PSSID=1448_13548_21114_18134_17001_21805_21553_21408_21673_20929; BD_UPN=12314353; sug=3; sugstore=0; ORIGIN=2; bdime=0; H_PS_645EC=6f56uIlaOB%2FGsbaAVWn2rMU23IwAEXSGNUIvGwRXGpknBALNr%2BPp5j1E9K4");
        request.setHeader("Host", "www.baidu.com");
        request.setHeader("Upgrade-Insecure-Requests", "1");
        request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.101 Safari/537.36");
        Result result = webResource.fetchPage(request);
        return result;
    }

    private static String getRealUrl(String url) {
        url = url.indexOf("https://") > -1 ? url : url.replace("http", "https");
        Result result = request(url);
        return result.getMoveToUrl();
    }
}
