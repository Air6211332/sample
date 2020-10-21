package org.airms.kn.cnitpm;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;

public class Test {
    public static void main(String[] args) {
//        String url = "http://1251520898.vod2.myqcloud.com/dc1d8ff3vodtranscq1251520898/1d2326ac5285890800774178017/drm/master_playlist.m3u8?t=5f32f185&us=7282966&sign=62fa74b05568eae9456c3cff4642dbf2";
        String url = "https://playvideo.qcloud.com/getplayinfo/v2/1251520898/5285890800774178017?sign=5848c65bfe3a74e19fdc0cad3e61f928&t=5f32f185&us=7282966";
        HttpRequest get = HttpUtil.createGet(url);
        get.header("referer","https://cloud.tencent.com/edu/learning/course-2216-31477");
        HttpResponse response = get.execute();
        String body = response.body();
        System.out.println(body);
    }
}
