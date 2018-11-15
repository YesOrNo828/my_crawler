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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yexianxun@corp.netease.com on 2018/11/14.
 */
public class FacePlusPlusDetect {
    private static final Logger LOG = LoggerFactory.getLogger(FacePlusPlusDetect.class);
    protected static final String apiUrl = "https://api-cn.faceplusplus.com/facepp/v3/detect";
    protected static final String key = "o9Gya1IK095laM5GXxykVWctQyKrf06M";
    protected static final String secret = "KtCHz_QbjtWlh6NYwhe1PC7Nw8ql_6Wz";
    protected static final String dir = "E:\\task\\百度图片抓取\\";

    public static void main(String[] args) throws IOException {
        File in = new File(dir + "detect_v3_human.txt");
        File out = new File(dir + "detect_v3_facepp_skin.txt");
        if (!out.exists()) {
            out.createNewFile();
        }
        List<String> lines = IOUtils.readLines(new FileInputStream(in));
        List<String> outLines = IOUtils.readLines(new FileInputStream(out));
        FileWriter fileWriter = null;
        int outLastName = 0;
        if (CollectionUtils.isNotEmpty(outLines)) {
            String line = outLines.get(outLines.size() - 1);
            String name = line.substring(0, line.indexOf(".jpg"));
            outLastName = Integer.parseInt(name);
        }

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            String name = line.substring(0, line.indexOf(".jpg"));
            int startNow = Integer.parseInt(name);
            if (outLastName >= startNow) {
                continue;
            }
            String[] lineArr = line.split("\t");
            String imgName = lineArr[0];
            String baidu_face_token = lineArr[1];
            String baidu_face_pro = lineArr[2];
            String baidu_face_type = lineArr[3];
            String baidu_face_type_pro = lineArr[4];
            String baidu_data = lineArr[5];
            String baidu_data1 = lineArr.length > 6 ? lineArr[6] : "";
            Double face_pro = Double.valueOf(baidu_face_pro);
            Double human_pro = Double.valueOf(baidu_face_type_pro);
            if (face_pro < 0.5 || human_pro < 0.5) {
                continue;
            }
            File img = new File(dir + "down\\" + imgName);
            if (!img.exists()) {
                continue;
            }
            try {
                fileWriter = new FileWriter(out, true);
                String base64 = Base64Util.fileToBase64(img);
                String data = detect(base64);
                LOG.info("{}\t{}", imgName, data);
                if (StringUtils.isNotBlank(data) && data.contains("face_token")) {
                    JSONArray faces = JSON.parseObject(data).getJSONArray("faces");
                    IOUtils.write(imgName + "\t", fileWriter);
                    for (int f = 0; f < faces.size(); f++) {

                        JSONObject face = faces.getJSONObject(f);
                        String facepp_token = face.getString("face_token");
                        if (!face.containsKey("attributes")) {
                            continue;
                        }
                        if (f > 0) {
                            IOUtils.write(imgName + "\t", fileWriter);
                        }
                        JSONObject skin = face.getJSONObject("attributes").getJSONObject("skinstatus");
                        // {"acne":16.189,"dark_circle":7.229,"health":18.201,"stain":9.085}
                        Double acne = skin.getDouble("acne");
                        Double dark_circle = skin.getDouble("dark_circle");
                        Double health = skin.getDouble("health");
                        Double stain = skin.getDouble("stain");
                        String image_id = JSON.parseObject(data).getString("image_id");

                        IOUtils.write(baidu_face_token + "\t" + baidu_face_pro + "\t" + baidu_face_type + "\t"
                                + baidu_face_type_pro + "\t" + facepp_token + "\t" + acne + "\t" + dark_circle + "\t"
                                + health + "\t" + stain + "\t" + image_id + "\t" + baidu_data + "\t"
                                + baidu_data1 + "\t" + data, fileWriter);

                    }





                }
            } catch (IOException e) {
            } finally {
                if (fileWriter != null) {
                    fileWriter.close();
                }
            }
            try {
                Thread.currentThread().sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public static String detect(String imageBase64) {
//        List<Param> params = new ArrayList<>(3);
//        params.add(new Param("image_base64", imageBase64));
//        params.add(new Param("return_attributes", "skinstatus"));
//        params.add(new Param("api_key", key));
//        params.add(new Param("api_secret", secret));
//        String contentType = "application/x-www-form-urlencoded";
//        return HttpUtil.doPost(apiUrl, params, contentType);
        Map<String, String> params = new HashMap<>();
        params.put("image_base64", imageBase64);
        params.put("return_attributes", "skinstatus");
        params.put("api_key", key);
        params.put("api_secret", secret);
        return HttpUtil.doPost(apiUrl, params);
    }

}
