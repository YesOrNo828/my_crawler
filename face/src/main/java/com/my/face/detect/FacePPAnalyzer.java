package com.my.face.detect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.util.List;

/**
 * Created by yexianxun@corp.netease.com on 2018/11/15.
 */
public class FacePPAnalyzer {

    protected static final String dir = "E:\\task\\百度图片抓取\\";

    public static void main(String[] args) throws IOException {
        File in = new File(dir + "detect_v3_facepp_skin.txt");
        File out = new File(dir + "detect_v3_facepp_skin_v2.txt");
        FileWriter fileWriter = null;
        List<String> outLines = IOUtils.readLines(new FileInputStream(in));
        for (int i = 0; i < outLines.size(); i++) {
            String line = outLines.get(i);
            String[] lineArr = line.split("\t");
            String imgName = lineArr[0];
            Integer nameNum = Integer.valueOf(imgName.substring(0, imgName.indexOf(".jpg")));
            if (nameNum == 7597) {
                System.out.println(7597);
            }
            if (nameNum >= 7732) {
                try {
                    fileWriter = new FileWriter(out, true);
                    IOUtils.write(line + "\n", fileWriter);
                } finally {
                    if (fileWriter != null) {
                        fileWriter.close();
                    }
                }
                continue;
            }
            String baidu_face_token = lineArr[1];
            String baidu_face_pro = lineArr[2];
            String baidu_face_type = lineArr[3];
            String baidu_face_type_pro = lineArr[4];
            String baidu_data = lineArr[11];
            String baidu_data1 = lineArr.length > 12 ? lineArr[12] : "";
            String fpp_data = lineArr.length > 13 ? lineArr[13] : "";
            try {
                if (StringUtils.isNotBlank(fpp_data) && fpp_data.contains("face_token")) {
                    fileWriter = new FileWriter(out, true);
                    JSONArray faces = JSON.parseObject(fpp_data).getJSONArray("faces");
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
                        Double acne = skin.getDouble("acne");
                        Double dark_circle = skin.getDouble("dark_circle");
                        Double health = skin.getDouble("health");
                        Double stain = skin.getDouble("stain");
                        String image_id = JSON.parseObject(fpp_data).getString("image_id");
                        IOUtils.write(baidu_face_token + "\t" + baidu_face_pro + "\t" + baidu_face_type + "\t"
                                + baidu_face_type_pro + "\t" + facepp_token + "\t" + acne + "\t" + dark_circle + "\t"
                                + health + "\t" + stain + "\t" + image_id + "\t" + baidu_data + "\t"
                                + baidu_data1 + "\t" + fpp_data+"\n", fileWriter);
                    }
                }
            } finally {
                if (fileWriter != null) {
                    fileWriter.close();
                }
            }

        }

    }
}
