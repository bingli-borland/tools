package com.bingli.tv;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class TVUrlParser {
    public static void main(String[] args) throws IOException {
//        String datafile = ParseData.class.getResource("/tv.html").getPath();
//        parseHTML(datafile);
        parseJson("https://www.360kan.com/cover/switchsite?site=youku&id=PbhrbH7lRzbqN3&category=2");
    }

    public static List<TVBean> parseHTML(String s) throws IOException {
    	File in = new File(s);
        Document doc = Jsoup.parse(in, "utf-8");
        List<TVBean> beans = new ArrayList<TVBean>();
        String prefix = "http://jiexi.92fz.cn/player/vip.php?url=";
        Elements es = doc.select("a[data-daochu]");
        for (int i = 0; i < es.size(); i++) {
            String num = es.get(i).attr("data-num");
            String url = es.get(i).attr("href");
            url = prefix + url;
            TVBean b = new TVBean();
            b.setName(num);
            b.setUrl(url);
            beans.add(b);
            System.out.println(num);
            System.out.println(url);
        }
        return beans;
    }

    public static List<TVBean> parseJson(String s) throws IOException {
        Response res = Jsoup.connect(s)
                .header("Accept", "*/*")
                .header("Accept-Encoding", "gzip, deflate")
                .header("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
                .header("Content-Type", "application/json;charset=UTF-8")
                .header("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:48.0) Gecko/20100101 Firefox/48.0")
                .timeout(10000).ignoreContentType(true).execute();//.get();
        String body = res.body();
        JSONObject json = JSON.parseObject(body);
        String data = json.getString("data");
        Document doc = Jsoup.parse(data);
        List<TVBean> beans = new ArrayList<TVBean>();
        String prefix = "http://jiexi.92fz.cn/player/vip.php?url=";
        Elements es = doc.select("a[data-daochu]");
        for (int i = 0; i < es.size(); i++) {
            String num = es.get(i).attr("data-num");
            String url = es.get(i).attr("href");
            url = prefix + url;
            TVBean b = new TVBean();
            b.setName(num);
            b.setUrl(url);
            beans.add(b);
            System.out.println(num);
            System.out.println(url);
        }
        return beans;
    }

    public static List<TVBean> parseURL(String s) throws IOException {
        List<TVBean> beans = new ArrayList<TVBean>();
        String prefix = "http://jiexi.92fz.cn/player/vip.php?url=";
        Document doc = Jsoup.connect(s).get();
        Elements es = doc.select("a[data-daochu]");
        for (int i = 0; i < es.size(); i++) {
            String num = es.get(i).attr("data-num");
            String url = es.get(i).attr("href");
            url = prefix + url;
            TVBean b = new TVBean();
            b.setName(num);
            b.setUrl(url);
            beans.add(b);
            System.out.println(num);
            System.out.println(url);
        }
        return beans;
    }

}
