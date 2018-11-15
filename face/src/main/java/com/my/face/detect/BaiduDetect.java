package com.my.face.detect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.my.common.util.Base64Util;
import com.my.common.util.HttpUtil;
import com.my.common.util.Param;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by yexianxun@corp.netease.com on 2018/11/14.
 */
public class BaiduDetect {
    private static final Logger LOG = LoggerFactory.getLogger(BaiduDetect.class);
    protected static final String apiUrl = "https://aip.baidubce.com/rest/2.0/face/v3/detect";

    protected static final String access_token = "24.910bbae670d6d1bc631194edfad57c25.2592000.1544710989.282335-14777381";
    protected static String path = "E:\\task\\百度图片抓取\\down\\";
    protected static String detectV3Path = "E:\\task\\百度图片抓取\\detect_v3.txt";
    protected static String detectV3HumanPath = "E:\\task\\百度图片抓取\\detect_v3_human.txt";

    public static void main(String[] args) throws InterruptedException, IOException {
        LOG.info("begin {}", path);
        File dir = new File(path);
        int size = Objects.requireNonNull(dir.listFiles()).length;
        File detectFile = new File(detectV3Path);
        File detectHumanFile = new File(detectV3HumanPath);
        if (!detectFile.exists()) {
            detectFile.createNewFile();
        }
        List<String> lines = IOUtils.readLines(new FileInputStream(detectHumanFile));
        int start = 3461;
        if (CollectionUtils.isNotEmpty(lines)) {
            String line = lines.get(lines.size() - 1);
            String name = line.substring(0, line.indexOf(".jpg"));
            start = Integer.parseInt(name);
        }
        for (int i = start + 1; i < size; i++) {
            File img = new File(path + i + ".jpg");
            if (!img.exists()) {
                continue;
            }
            String base64 = Base64Util.fileToBase64(img);
            base64 = URLEncoder.encode(base64, "UTF-8");
            String data = detect(base64);
            LOG.info("{} result: {}", img.getName(), data);
            FileWriter fileWriter = null;
            try {
                if (data.contains("SUCCESS") && !data.contains("cartoon")) {
                    fileWriter = new FileWriter(detectHumanFile, true);
                    IOUtils.write(img.getName() + "\t", fileWriter);
                    JSONArray faceList = JSON.parseObject(data).getJSONObject("result").getJSONArray("face_list");
                    JSONObject faceType = faceList.getJSONObject(0).getJSONObject("face_type");
                    String type = faceType.getString("type");
                    Double pro = faceType.getDouble("probability");
                    for (int f = 0; f < faceList.size(); f++) {
                        if (f > 0) {
                            IOUtils.write("\n" + img.getName() + "\t", fileWriter);
                        }
                        JSONObject face = faceList.getJSONObject(f);
                        String face_token = face.getString("face_token");
                        double face_probability = face.getDouble("face_probability");
                        IOUtils.write(face_token + "\t" + face_probability + "\t" + type + "\t" + pro
                                + "\t" + data +"\t", fileWriter);
                    }
                    IOUtils.write("\n", fileWriter);
                } else if (data.contains("qps")){
                    LOG.info("{} result: {}", img.getName(), data);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fileWriter != null) {
                    try {
                        fileWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            Thread.currentThread().sleep(500);
        }
    }

    public static String detect(String imageBase64) {
        List<Param> params = new ArrayList<>(3);
        params.add(new Param("image", imageBase64));
        params.add(new Param("image_type", "BASE64"));
        params.add(new Param("face_field", "face_type"));
//        params.add(new Param("image", "313c1fdc1c322c582d930d99e5acfe18"));
//        params.add(new Param("image_type", "FACE_TOKEN"));
        String contentType = "application/json";
        String url = apiUrl + "?access_token=" + access_token;
        return HttpUtil.doPost(url, params, contentType);
    }
}
