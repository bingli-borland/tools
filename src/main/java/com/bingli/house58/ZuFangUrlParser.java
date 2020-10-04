package com.bingli.house58;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ZuFangUrlParser {
    private static List<String> pages = new ArrayList<String>();
    static {
        pages.add("http://su.58.com/baiyangwan/zufang/b3j2/");
        pages.add("http://su.58.com/baiyangwan/zufang/b3j2/pn2");
        pages.add("http://su.58.com/baiyangwan/zufang/b3j2/pn3");
        pages.add("http://su.58.com/baiyangwan/zufang/b3j2/pn4");
    }

    public static void main(String[] args) {
        insert();
        select();
    }

    private static void insert() {
        clean();
        for (String addr : pages) {
            try {
                parser(addr);
            } catch (IOException e) {
            }
        }
    }

    private static void select() {
        JdbcUtils jdbcUtils = new JdbcUtils();
        jdbcUtils.getConnection();
        String sql = "select * from house where addr like ?";
        List<Object> params = new ArrayList<Object>();
        params.add("%和美%");
        List<Fang> fangs = null;
        try {
            fangs = jdbcUtils.findMoreRefResult(sql, params, Fang.class);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        for (Fang fang : fangs) {
            System.out.println(fang);
        }
    }

    private static void clean() {
        JdbcUtils jdbcUtils = new JdbcUtils();
        jdbcUtils.getConnection();
        String sql = "delete from house";
        List<Object> params = new ArrayList<Object>();
        try {
            boolean flag = jdbcUtils.updateByPreparedStatement(sql, params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void parser(String address) throws IOException {
        JdbcUtils jdbcUtils = new JdbcUtils();
        jdbcUtils.getConnection();
        Document doc = Jsoup.connect(address).get();
        Elements es = doc.select("ul").select(".listUl").select("li");
        for (int i = 0; i < es.size(); i++) {
            Element e = es.get(i).select("div").select(".des").select("a").first();
            String url = e.attr("href");
            String sql = "insert into house (url, descriptor, room, addr, jjr, sendtime, money, tel) values (?, ?, ?, ?, ?, ?, ?, ?)";
            List<Object> params = new ArrayList<Object>();
            params.add(url);
            params.add(e.text());
            params.add(es.get(i).select("p").select(".room").text());
            params.add(es.get(i).select("p").select(".add").text());
            params.add(es.get(i).select("div").select(".jjr").text());
            params.add(es.get(i).select("div").select(".sendTime").text());
            params.add(es.get(i).select("div").select(".money").text());
            params.add("");
            System.out.println(params);
            try {
                boolean flag = jdbcUtils.updateByPreparedStatement(sql, params);
                // System.out.println(flag);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

}
