package org.airms.wechat_video;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.http.HttpUtil;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.util.ArrayList;
import java.util.List;

public class WechatVideoDownload {
    private static List<String> videoUrls = new ArrayList<>();

    public static void main(String[] args) throws ParserConfigurationException, InterruptedException {
        indexPage("https://mp.weixin.qq.com/s?__biz=MzIxNzYyMDY1MA==&mid=100007348&idx=4&sn=ab60a0907ad4cc302d44aaf0d84bcbda&scene=19#wechat_redirect");
//        infoPage("https://mp.weixin.qq.com/s?__biz=MzIxNzYyMDY1MA==&mid=2247494015&idx=2&sn=744f5481823ab7e67d26b8d17cb91dfd&chksm=97f5a0baa08229ac5c03faffea26a2831f802b8e8529ca46d761b8de53eafa05705c5c1f32cf&scene=21#wechat_redirect");
    }

    private static void indexPage(String url) throws ParserConfigurationException, InterruptedException {
        System.out.println("页面开始解析,url:\t" + url);
        String resp = HttpUtil.get(url);
        TagNode tagNode = new HtmlCleaner().clean(resp);
        Document html = new DomSerializer(new CleanerProperties()).createDOM(tagNode);
        NodeList nodeListByXpath = getNodeListByXpath("//a[@tab=\"innerlink\"]", html);
        int size = nodeListByXpath.getLength();
        List<String> todoList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Node item = nodeListByXpath.item(i);
            String href = item.getAttributes().getNamedItem("href").getNodeValue();
            if (href != null && href.contains("//mp.weixin.qq.com/s")) {
                todoList.add(href);

            }
        }

        int lun = 1;
        while (true) {
            if (todoList.size() == 0) {
                break;
            }
            List<String> _todoList = new ArrayList<>();
            for (String href : todoList) {
                System.out.println("页面开始解析,url:\t" + href);
                long time = RandomUtil.randomInt(40000) + 10000L;
                Thread.sleep(time);
                boolean moreTry = infoPage(href);
                if (moreTry) {
                    _todoList.add(href);
                }
            }
            todoList = _todoList;

            System.out.println("第" + lun + "轮结束，剩余" + _todoList.size() + "个");
            lun++;
        }

        System.out.println("页面解析完毕");
        System.out.println();

        System.out.println("开始打印视频链接\n");
        for (String videoUrl : videoUrls) {
            System.out.println(videoUrl);
        }
    }

    private static boolean infoPage(String url) throws ParserConfigurationException {
        String resp = HttpUtil.get(url);
        if (resp.contains("你暂无权限查看此页面内容。")) {
            System.err.println("登录失效,稍后尝试:\t" + url);
            return true;
        }
        TagNode tagNode = new HtmlCleaner().clean(resp);
        Document html = new DomSerializer(new CleanerProperties()).createDOM(tagNode);
        NodeList nodeListByXpath = getNodeListByXpath("//iframe", html);
        int size = nodeListByXpath.getLength();
        for (int i = 0; i < size; i++) {
            Node item = nodeListByXpath.item(i);
            //http://mpvideo.qpic.cn/0bf2zyaaeaaataad52zjqzpvbtwdalhaaaqa.f10004.mp4?dis_k=7f494c01fe90a9c75a6ea2be7e30eb55&dis_t=1599617375
            String href = item.getAttributes().getNamedItem("data-src").getNodeValue();
            String vid = ReUtil.getGroup1("vid=(\\w+)", href);
//            String video = "http://v.qq.com/iframe/player.html?vid="+vid;
            String video = "http://mp.weixin.qq.com/mp/readtemplate?t=pages/video_player_tmpl&auto=0&vid=" + vid;
            videoUrls.add(video);
            System.out.println(video);
        }
        System.out.println("页面解析完毕");
        System.out.println();
        return false;
    }

    /**
     * Extract value by xPath from HTML.
     */
    private static NodeList getNodeListByXpath(String xPath, Node node) {
        NodeList value = null;
        try {
            XPath xpath = XPathFactory.newInstance().newXPath();
            value = (NodeList) xpath.evaluate(xPath, node, XPathConstants.NODESET);
        } catch (Exception e) {
            System.err.println("Extract value error. " + e.getMessage());
            e.printStackTrace();
        }
        return value;
    }
}
