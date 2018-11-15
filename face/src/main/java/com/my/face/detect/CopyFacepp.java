package com.my.face.detect;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by yexianxun@corp.netease.com on 2018/11/15.
 */
public class CopyFacepp {


    protected static final String dirPath = "E:\\task\\百度图片抓取\\";
    public static void main(String[] args) throws IOException {
//        String n1 = "face0.5human0.1dark_circle60";
        String n1 = "face0.8human0.8dark_circle80";
        File file = new File(dirPath + n1 + ".txt");
        File dir = new File(dirPath + n1);
        if (!dir.exists()) {
            dir.mkdir();
        }
        List<String> lines = IOUtils.readLines(new FileInputStream(file));
        for (String line : lines) {
            File src = new File(dirPath + "down\\" + line.trim());
            File target = new File(dir.getPath() +"\\" + line);
            if (target.exists()) {
                continue;
            }
            FileUtils.copyFile(src, target);
            System.out.println(target.getName());
        }
    }
}
