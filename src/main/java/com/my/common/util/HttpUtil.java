package com.my.common.util;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Created by yexianxun@corp.netease.com on 2017/12/14.
 */
public class HttpUtil {
    private static final Logger LOG = LoggerFactory.getLogger(HttpUtil.class);
    private static final int connTimeout = 1 * 60 * 1000;
    private static final int readTimeout = 10 * 60 * 1000;
    private static final String charset = "utf-8";
    private final static String HTTP_POST = "POST";
    /**
     * url-post请求参数中的连接字符
     */
    static final String AND_TAG = "&";
    static final String EQUAL_TAG = "=";

    private static String configParams(Collection<Param> params) {
        StringBuilder builder = new StringBuilder();
        Iterator<Param> iterator = params.iterator();
        while (iterator.hasNext()) {
            Param pr = iterator.next();
            String key = StringUtils.trim(pr.getName());
            builder.append(key + EQUAL_TAG + StringUtils.trim(pr.getValue()));
            if (iterator.hasNext()) {
                builder.append(AND_TAG);
            }
        }
        return builder.toString();
    }

    public static String doPost(String url, List<Param> params, String contentType) {
        // 读取返回内容
        StringBuffer buffer = new StringBuffer();
        HttpURLConnection con = null;
        PrintWriter out = null;
        InputStream in = null;
        BufferedReader br = null;
        // 尝试发送请求
        try {
            // 构建请求参数
            con = (HttpURLConnection) new URL(url).openConnection();
            con.setDoInput(true);
            con.setUseCaches(false);
            con.setConnectTimeout(connTimeout);
            con.setReadTimeout(readTimeout);
            con.setRequestProperty("Content-Type", contentType);
            con.setDoOutput(true);
            con.setRequestMethod(HTTP_POST);
            if (CollectionUtils.isNotEmpty(params)) {
                String postParam = configParams(params);
                out = new PrintWriter(con.getOutputStream());
                out.print(postParam);
                out.flush();
            }
            in = con.getInputStream();
            br = new BufferedReader(new InputStreamReader(in, charset));
            String temp;
            while ((temp = br.readLine()) != null) {
                buffer.append(temp);
            }
            br.close();
        } catch (Exception e) {
            LOG.error("", e);
        } finally {
            IOUtils.closeQuietly(br);
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);
            if (con != null) {
                con.disconnect();
                con = null;
            }
        }
        return buffer.toString();
    }

    public static String doPost(String url, List<Param> params) {
        return doPost(url, params, "application/x-www-form-urlencoded");
    }

    /**
     * post请求(用于key-value格式的参数)
     *
     * @param url
     * @param params
     * @return
     */
    public static String doPost(String url, Map params) {
        BufferedReader in = null;
        try {
            // 定义HttpClient
            HttpClient client = HttpClients.createDefault();
            // 实例化HTTP方法
            HttpPost request = new HttpPost();
            request.setURI(new URI(url));

            //设置参数
            List<NameValuePair> nvps = new ArrayList<>();
            for (Iterator iter = params.keySet().iterator(); iter.hasNext(); ) {
                String name = (String) iter.next();
                String value = String.valueOf(params.get(name));
                nvps.add(new BasicNameValuePair(name, value));
            }
            request.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

            HttpResponse response = client.execute(request);
            int code = response.getStatusLine().getStatusCode();
            if (code == 200) {    //请求成功
                in = new BufferedReader(new InputStreamReader(response.getEntity()
                        .getContent(), "utf-8"));
                StringBuffer sb = new StringBuffer();
                String line;
                String NL = System.getProperty("line.separator");
                while ((line = in.readLine()) != null) {
                    sb.append(line + NL);
                }
                in.close();
                return sb.toString();
            } else {
                System.out.println("状态码：" + code);
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * post请求（用于请求json格式的参数）
     *
     * @param url
     * @param params
     * @return
     */
    public static String doPost(String url, String params) {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);// 创建httpPost
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-Type", "application/json");
        String charSet = "UTF-8";
        StringEntity entity = new StringEntity(params, charSet);
        httpPost.setEntity(entity);
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpPost);
            StatusLine status = response.getStatusLine();
            int state = status.getStatusCode();
            if (state == HttpStatus.SC_OK) {
                HttpEntity responseEntity = response.getEntity();
                String jsonString = EntityUtils.toString(responseEntity);
                return jsonString;
            } else {
                LOG.error("请求返回:" + state + "(" + url + ")");
            }
        } catch (Exception e) {
            LOG.error("", e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * @param url
     * @param params
     * @return
     */
    public static String doGet(String url, Map<String, String> params) {
        // 读取返回内容
        StringBuffer buffer = new StringBuffer();
        HttpURLConnection con = null;
        // 尝试发送请求
        try {
            // 构建请求参数
            StringBuffer sb = new StringBuffer();
            if (params != null) {
                for (Map.Entry<String, String> e : params.entrySet()) {
                    sb.append(e.getKey()).append("=");
                    sb.append(URLEncoder.encode(e.getValue(), "UTF-8")).append("&");
                }
                sb.substring(0, sb.length() - 1);
            }
            if (StringUtils.isNotBlank(sb.toString())) {
                con = (HttpURLConnection) new URL(url + "?" + sb.toString()).openConnection();
            } else {
                con = (HttpURLConnection) new URL(url).openConnection();
            }
            con.setDoInput(true);
            con.setUseCaches(false);
            con.setConnectTimeout(connTimeout);
            con.setReadTimeout(readTimeout);
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            InputStream in = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in, charset));
            String temp;
            while ((temp = br.readLine()) != null) {
                buffer.append(temp);
            }
            in.close();
            br.close();
        } catch (Exception e) {
            LOG.error("url:{}", url, e);
        } finally {
            if (con != null) {
                con.disconnect();
                con = null;
            }
        }
        return buffer.toString();
    }
}
