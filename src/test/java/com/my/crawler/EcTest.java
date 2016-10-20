package com.my.crawler;

import com.my.esindex.MonitorEcCount;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.my.esindex.MonitorEcCount.getSpecialTime;

/**
 * Created by yexianxun on 2016/8/12.
 */
public class EcTest {

    public static void main(String[] args) throws IOException {
//        rate();
//        List<Date> dates = getSpecialTime(new Integer[]{0, 9, 12, 15, 18, 21});
//        dates.stream().forEach(date -> System.out.println(date));
//        ,xuanlubin@adtime.com,caoquanlong@adtime.com,fumingjiang@adtime.com,xuliya@adtime.com,chenshuiping@zxuner.com,zhujianquan@adtime.com,wangqin@zxuner.com,wangyanjun@adtime.com
    }

    private static void rate() throws IOException {
        int i = 0, start = 91;
        File file = new File("d:\\tmp\\ec\\allRate.txt");
        int count = 0;
        String gomeurl = "http://item.gome.com.cn/${pid}-${skuId}.html";
        String suningUrl = "http://product.suning.com/${shopId}/${pid}.html";
        String jdUrl = "http://item.jd.com/${pid}.html";
        while (i < 59) {
            List<String> list = FileUtils.readLines(new File("C:\\Users\\yexianxun\\Desktop\\评论\\" + (start + i) + ".txt"));
            list.stream().forEach(str -> {
                try {
                    String[] arr = str.split("\\t");
                    String media = arr[0];
                    String url = "";
                    String pid = arr[4], shopId = arr[3], skuId = arr[5];
                    switch (media) {
                        case "gome":
                            url = gomeurl.replace("${pid}", pid).replace("${skuId}", skuId);
                            break;
                        case "suning":
                            url = suningUrl.replace("${shopId}", shopId).replace("${pid}", pid);
                            break;
                        case "jd":
                            url = jdUrl.replace("${pid}", pid);
                    }
                    str += "\t" + url + "\n";
                    if (!"0".equals(arr[2])) {
                        FileUtils.writeStringToFile(file, str, true);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            System.out.println((start + i) + " write:" + list.size());
            count += list.size();
            i++;
        }
        System.out.println("all write:" + count);

    }

    private static void shop() throws IOException {
        int i = 0, start = 91;
        File file = new File("d:\\tmp\\ec\\allShop.txt");
        int count = 0;
        while (i < 59) {
            List<String> list = FileUtils.readLines(new File("C:\\Users\\yexianxun\\Desktop\\店铺\\" + (start + i) + ".txt"));
            list.stream().forEach(str -> {
                try {
                    str += "\n";
                    FileUtils.writeStringToFile(file, str, true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            System.out.println((start + i) + " write:" + list.size());
            count += list.size();
            i++;
        }
        System.out.println("all write:" + count);

    }
}
