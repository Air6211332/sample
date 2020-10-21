package org.airms.blogger;

import org.airms.blogger.api.BloggerApi;
import org.airms.blogger.api.respons.PostsResponse;

import java.util.Properties;

public class BloggerDownload {
    public static void main(String[] args) {
        Properties props = System.getProperties();
        props.setProperty("proxySet", "true");
        props.setProperty("socksProxyHost", "127.0.0.1");
        props.setProperty("socksProxyPort", "7891");

        BloggerApi api = new BloggerApi("AIzaSyByCJ305oKQ7DMge6CZyOuMc-lyiIyLHZ4");
//        BlogInfoResponse blogInfoRespons = api.bloggerInfoById(2399953);

        //http://yuluji.blogspot.com/2020/10/0.html
//        905255491621018105
//        BlogInfoResponse blogInfoResponse = api.bloggerInfoByUrl("http://yuluji.blogspot.com");
        PostsResponse response = api.bloggerPosts("905255491621018105", null);



        System.out.println();
    }
}
