package org.airms.ubearcn;

import java.io.File;

import cn.hutool.http.HttpUtil;

public class Download {
    public static void main(String[] args) {
        Download download = new Download();
        download.download();
    }

    public void download() {
//        view-source:www.ubearcn.com/bc/20200814g/bmwe/01.jpg
        String title = "减肥对决";
        Integer number = 32;
        String baseUrl = "http://www.ubearcn.com/bc/20200814g/bmwe/";
        String targetDir = "/Users/rubenchen/mi/temp/";

        for (int i = 1; i < number; i++) {
            String numberStr = String.format("%02d", i);
            File targetFile = new File(targetDir + "/" + numberStr + ".jpg");
            String url = baseUrl + numberStr + ".jpg";

            HttpUtil.downloadFile(url, targetFile);
//            System.out.println();
        }
    }
}
