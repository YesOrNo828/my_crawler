package com.my.common;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by yexianxun@corp.netease.com on 2018/7/24.
 */
public class FileUtil {
    private static final Logger LOG = LoggerFactory.getLogger(FileUtil.class);
    /**
     * 读取系统中fileName的文件内容
     * @param fileName 读取的文件名
     * @return 文件内容
     */
    public static List<String> readLines(String fileName) {
        if (StringUtils.isBlank(fileName)) {
            return Collections.emptyList();
        }
        List<String> lines = new ArrayList<>();
        try {
            File file = new File(FileUtil.class.getClassLoader().getResource(fileName).toURI());
            return IOUtils.readLines(new FileInputStream(file));
        } catch (Exception e) {
            InputStream is = FileUtil.class.getClassLoader().getResourceAsStream(fileName);
            if (is != null) {
                BufferedReader br = null;
                try {
                    br = new BufferedReader(new InputStreamReader(is));
                    String line;
                    while ((line = br.readLine()) != null) {
                        lines.add(line);
                    }
                    return lines;
                } catch (Exception e1) {
                } finally {
                    IOUtils.closeQuietly(br);
                    IOUtils.closeQuietly(is);
                }
            }
        }
        return Collections.emptyList();
    }

    public static void main(String[] args) {
        System.out.println("sssss");
        LOG.info("ss {}", "ff");
    }
}
