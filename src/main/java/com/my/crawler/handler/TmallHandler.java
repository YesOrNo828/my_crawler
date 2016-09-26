package com.my.crawler.handler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by 叶贤勋 on 2016/7/20.
 */
public class TmallHandler {

    public static void main(String[] args) throws IOException {
        String url = "http://mdskip.taobao.com/core/initItemDetail.htm?isRegionLevel=true&itemTags=385,775,843,1035,1163,1227,1478,1483,1539,1611,1863,1867,1923,2049,2059,2242,2251,2315,2507,2635,3595,3974,4166,4299,4555,4811,5259,5323,5515,6145,6785,7809,9153,11265,12353,12609,13697,13953,16321,16513,17473,17537,17665,17857,18945,19841,20289,21762,21826,25922,28802,53954&tgTag=false&addressLevel=4&isAreaSell=false&sellerPreview=false&offlineShop=false&showShopProm=false&isIFC=false&service3C=true&isSecKill=false&isForbidBuyItem=false&cartEnable=true&sellerUserTag=839979040&queryMemberRight=true&itemId=523029919958&sellerUserTag2=306250462070310924&household=false&isApparel=false¬AllowOriginPrice=false&tmallBuySupport=true&sellerUserTag3=144467169269284992&sellerUserTag4=1152930305168967075&progressiveSupport=true&isUseInventoryCenter=false&tryBeforeBuy=false&callback=setMdskip×tamp=1420351892310";

        HttpClientBuilder builder = HttpClients.custom();
        builder.setUserAgent("Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:0.9.4)");
        CloseableHttpClient httpClient = builder.build();
        final HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("Referer", "http://detail.tmall.com/item.htm?id=40533381395&skuId=68347779144&areaId=110000&cat_id=50024400&rn=763d147479ecdc17c2632a4219ce96b3&standard=1&user_id=263726286&is_b=1");
        CloseableHttpResponse response = null;
        response = httpClient.execute(httpGet);
        final HttpEntity entity = response.getEntity();
        String result = null;
        if (entity != null) {
            result = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
        }

        //商品价格的返回值，需要解析出来价格

//        result = result.substring(10, result.length()-1);
//
//        JSONObject object = new JSONObject(result);
//        JSONObject object2 = (JSONObject)object.get("defaultModel");
//        JSONObject object3 = (JSONObject)object2.get("itemPriceResultDO");
//        JSONObject object4 = (JSONObject)object3.get("priceInfo");
//        JSONObject object5 = (JSONObject)object4.get("68347779144");
//        JSONArray jsonArray = new JSONArray(object5.get("promotionList").toString());
//        if(jsonArray.length()==1){
//            JSONObject object6 = (JSONObject)jsonArray.get(0);
//            System.out.println(object6.get("price"));
//        }
        System.out.println("---------------------");
        System.out.println(result);

        response.close();
        httpClient.close();

    }
}
