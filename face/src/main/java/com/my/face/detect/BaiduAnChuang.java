package com.my.face.detect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.my.common.util.Base64Util;
import com.my.common.util.HttpUtil;
import com.my.common.util.Param;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yexianxun@corp.netease.com on 2018/11/14.
 */
public class BaiduAnChuang {
    private static final Logger LOG = LoggerFactory.getLogger(BaiduAnChuang.class);
    private static final String apiUrl = "https://aip.baidubce.com/rpc/2.0/ai_custom/v1/classification/";
    private static final String access_token = "24.910bbae670d6d1bc631194edfad57c25.2592000.1544710989.282335-14777381";
    protected static final String dir = "E:\\task\\百度图片抓取\\";

    public static void main(String[] args) throws IOException {
//        String apiType = "anchuang";
        String apiType = "heiyanquan";
        classify(apiType);
    }

    public static void classify(String apiType) throws IOException {
        File in = new File(dir + "detect_v3_facepp_skin.txt");
        File out = new File(dir + "detect_v3_" + apiType + ".txt");
        if (!out.exists()) {
            out.createNewFile();
        }
        List<String> lines = IOUtils.readLines(new FileInputStream(in));
        List<String> outLines = IOUtils.readLines(new FileInputStream(out));
        FileWriter fileWriter = null;
        int start = 0;
        if (CollectionUtils.isNotEmpty(outLines)) {
            start = outLines.size();
        }
        for (int i = start; i < lines.size(); i++) {
            String line = lines.get(i);
            String[] lineArr = line.split("\t");
            String imgName = lineArr[0];
            String baidu_face_token = lineArr[1];
            String baidu_face_pro = lineArr[2];
            String baidu_face_type = lineArr[3];
            String baidu_face_type_pro = lineArr[4];
            String facepp_token = lineArr[5];
            Double acne = Double.valueOf(lineArr[6]);
            Double dark_circle = Double.valueOf(lineArr[7]);
            Double health = Double.valueOf(lineArr[8]);
            Double stain = Double.valueOf(lineArr[9]);
            String image_id = lineArr[10];
            String baidu_data = lineArr[11];
            String baidu_data1 = lineArr[12];
            String facepp_data1 = lineArr[12];
            File img = new File(dir + "down\\" + imgName);
            if (!img.exists()) {
                continue;
            }
            try {
                fileWriter = new FileWriter(out, true);
                String base64 = Base64Util.fileToBase64(img);
                String data = post(base64, apiType);
                LOG.info("{}", data);
                if (StringUtils.isNotBlank(data) && data.contains(apiType)) {
                    JSONArray results = JSON.parseObject(data).getJSONArray("results");
                    for (int r = 0; r < results.size(); r++) {
                        JSONObject result = results.getJSONObject(r);
                        if (result.containsKey("name") && result.getString("name").contains(apiType)) {
                            Double anchuangScore = result.getDouble("score");

                            IOUtils.write(imgName + "\t" + baidu_face_token + "\t" + baidu_face_pro + "\t" + baidu_face_type + "\t"
                                    + baidu_face_type_pro + "\t" + facepp_token + "\t" + acne + "\t" + dark_circle + "\t"
                                    + health + "\t" + stain + "\t" + image_id + "\t" + anchuangScore + "\t" + baidu_data + "\t" + baidu_data1 + "\t"
                                    + facepp_data1 + "\t" + data, fileWriter);
                            break;
                        }
                    }
                } else if (data.contains("Open api daily request limit reached")) {
                    try {
                        Thread.currentThread().sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
            } finally {
                if (fileWriter != null) {
                    fileWriter.close();
                }
            }
            try {
                Thread.currentThread().sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static String post(String imageBase64, String apiType) {
        String url = apiUrl + apiType + "?access_token=" + access_token;
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("image", imageBase64);
        return HttpUtil.doPost(url, JSON.toJSONString(paramMap));
    }
}
