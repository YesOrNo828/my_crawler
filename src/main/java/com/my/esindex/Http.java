package com.my.esindex;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by yexianxun on 2016/8/24.
 */
public class Http {
    private static final int conntimeout = 1 * 60 * 1000;
    private static final int readtimeout = 10 * 60 * 1000;
    private static final String charset = "utf-8";
    public static String url = null;
    private static HttpClient httpClient = HttpClients.createDefault();

    static {
        try {
            url = FileUtils.readFileToString(new File(MonthIndex.class.getResource("/").getFile() + "esUrl.txt")).trim();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * @param @param  url
     * @param @param  data
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
     * @Title: doPost
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @date 2014-1-2 下午1:06:58
     */
    public static String doPost(String url, String data) throws Exception {
        //读取返回内容
        StringBuilder buffer = new StringBuilder();
        HttpURLConnection con = null;
        //尝试发送请求
        try {
            con = (HttpURLConnection) new URL(url).openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setUseCaches(false);
            con.setConnectTimeout(conntimeout);
            con.setReadTimeout(readtimeout);
            con.setRequestProperty("Content-Type", "application/json");
            OutputStream out = con.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(out, charset);
            osw.write(new String(data.getBytes(charset), charset));//new String(data.getBytes(), charset)
            osw.flush();
            osw.close();
            out.flush();
            out.close();
            if (con.getResponseCode() == 200) {
                InputStream in = con.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(in, charset));
                String temp;
                while ((temp = br.readLine()) != null) {
                    buffer.append(temp);
                    //buffer.append("\n");
                }
                in.close();
                br.close();
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (con != null) {
                con.disconnect();
                con = null;
            }
        }
        return buffer.toString();
    }

    /**
     *
     * @param url
     * @param data
     * @return
     * @throws IOException
     */
    public static String doPost2(String url, String data) throws IOException {
        HttpPost post = new HttpPost(url);
        HttpEntity entity = EntityBuilder.create().setText(data).setContentType(ContentType.APPLICATION_JSON).build();
        post.setEntity(entity);
        HttpResponse httpResponse = httpClient.execute(post);
        String result = EntityUtils.toString(httpResponse.getEntity());
        return result;
    }
}
