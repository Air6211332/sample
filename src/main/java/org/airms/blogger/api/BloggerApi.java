package org.airms.blogger.api;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import org.airms.blogger.api.respons.BlogInfoResponse;
import org.airms.blogger.api.respons.PostsResponse;

import java.util.HashMap;
import java.util.Map;

public class BloggerApi {
    private String key;

    public BloggerApi(String key) {
        this.key = key;
    }

    public BlogInfoResponse bloggerInfoById(String bloggerId) {
        String url = "https://www.googleapis.com/blogger/v3/blogs/" + bloggerId;
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("key", key);
        String s = HttpUtil.get(url, paramMap);
        BlogInfoResponse blogInfoRespons = JSON.parseObject(s, BlogInfoResponse.class);
        return blogInfoRespons;
    }

    public BlogInfoResponse bloggerInfoByUrl(String bloggerUrl) {
        String url = "https://www.googleapis.com/blogger/v3/byurl";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("key", key);
        paramMap.put("url", bloggerUrl);
        String s = HttpUtil.get(url, paramMap);
        System.out.println(s);
        BlogInfoResponse blogInfoRespons = JSON.parseObject(s, BlogInfoResponse.class);
        return blogInfoRespons;
    }

    public PostsResponse bloggerPosts(String bloggerId, String pageToken) {
        String url = "https://www.googleapis.com/blogger/v3/blogs/" + bloggerId + "/posts";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("key", key);
        if (pageToken != null) {
            paramMap.put("pageToken", pageToken);
        }
        String s = HttpUtil.get(url, paramMap);
        System.out.println(s);
        PostsResponse response = JSON.parseObject(s, PostsResponse.class);
        return response;
    }
}
