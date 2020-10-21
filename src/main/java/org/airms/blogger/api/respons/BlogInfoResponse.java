package org.airms.blogger.api.respons;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class BlogInfoResponse {

    /**
     * kind : blogger#blog
     * id : 2399953
     * name : Official Blogger Blog
     * description : The latest tips and news from the Blogger team
     * published : 2007-04-23T15:17:29-07:00
     * updated : 2020-09-01T02:41:00-07:00
     * url : http://blogger.googleblog.com/
     * selfLink : https://www.googleapis.com/blogger/v3/blogs/2399953
     * posts : {"totalItems":541,"selfLink":"https://www.googleapis.com/blogger/v3/blogs/2399953/posts"}
     * pages : {"totalItems":2,"selfLink":"https://www.googleapis.com/blogger/v3/blogs/2399953/pages"}
     * locale : {"language":"en","country":"","variant":""}
     */

    private String kind;
    private String id;
    private String name;
    private String description;
    private String published;
    private String updated;
    private String url;
    private String selfLink;
    private PostsBean posts;
    private PostsBean pages;
    private LocaleBean locale;

    @NoArgsConstructor
    @Data
    public static class PostsBean {
        /**
         * totalItems : 541
         * selfLink : https://www.googleapis.com/blogger/v3/blogs/2399953/posts
         */

        private int totalItems;
        private String selfLink;
    }

    @NoArgsConstructor
    @Data
    public static class LocaleBean {
        /**
         * language : en
         * country : 
         * variant : 
         */

        private String language;
        private String country;
        private String variant;
    }
}
