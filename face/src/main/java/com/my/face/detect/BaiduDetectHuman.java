package com.my.face.detect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.my.common.util.Base64Util;
import com.my.common.util.HttpUtil;
import com.my.common.util.Param;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by yexianxun@corp.netease.com on 2018/11/14.
 */
public class BaiduDetectHuman extends BaiduDetect {
    private static final Logger LOG = LoggerFactory.getLogger(BaiduDetectHuman.class);
    protected static String detectV3HumanPath = "E:\\task\\百度图片抓取\\detect_v3_human.txt";

    public static void main(String[] args) throws IOException {
        File detectFile = new File(detectV3Path);
        List<String> lines = IOUtils.readLines(new FileInputStream(detectFile));
        File detectHumanFile = new File(detectV3HumanPath);
        if (!detectHumanFile.exists()) {
            detectHumanFile.createNewFile();
        }
        for (String line : lines) {
            FileWriter fileWriter = null;
            try {
                if (StringUtils.isBlank(line) || StringUtils.isBlank(line.split("\t")[1])) {
                    continue;
                }
                fileWriter = new FileWriter(detectHumanFile, true);
                String[] lineArr = line.split("\t");
                String imgName = lineArr[0];
                String face_token = lineArr[1];
                String facePro = lineArr[2];
                String faceData = lineArr[3];
                File img = new File(path + imgName);
                String base64 = Base64Util.fileToBase64(img);
                base64 = URLEncoder.encode(base64, "UTF-8");
                String result = detect(base64);
                LOG.info("result: {}", result);
                if (result.contains("cartoon")) {
                    continue;
                }
                JSONObject faceType = JSON.parseObject(result).getJSONObject("result").getJSONArray("face_list").getJSONObject(0).getJSONObject("face_type");
                String type = faceType.getString("type");
                Double pro = faceType.getDouble("probability");
                IOUtils.write(imgName + "\t" + face_token + "\t" + facePro + "\t" + type + "\t" + pro + "\t" + faceData + "\t" + result +"\n", fileWriter);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (fileWriter != null) {
                    fileWriter.close();
                }
            }
            try {
                Thread.currentThread().sleep(800);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

//        for (Iterator<Map.Entry<String, String>> iterator = faceTokenMaps.entrySet().iterator(); iterator.hasNext(); ) {
//            Map.Entry<String, String> item = iterator.next();
//            String face_token = item.getKey();
//            String data = item.getValue();
//            LOG.info("{}\t{}", face_token, data);
//        }
    }

    public static String detect(String imageBase64) {
        List<Param> params = new ArrayList<>(3);
//        params.add(new Param("image", face_token));
//        params.add(new Param("image_type", "FACE_TOKEN"));
        params.add(new Param("image", imageBase64));
        params.add(new Param("image_type", "BASE64"));
        params.add(new Param("face_field", "face_type"));
        params.add(new Param("", ""));
        String contentType = "application/json";
        String url = apiUrl + "?access_token=" + access_token;
        return HttpUtil.doPost(url, params, contentType);
    }
}
