package com.bingli.tools;

import java.util.List;

public class ParserTool {

    public static void parse(String line, List<ArticleBean> rs) {
        int start = 0;
        int end = 0;
        while (true) {
            start = line.indexOf("datetime", end) + 10;
            if (start < end) {
                return;
            }
            end = line.indexOf(",", start);
            if (start == -1 || end == -1) {
                return;
            }
            String datetime = line.substring(start, end);
            start = line.indexOf("title", end) + 8;
            if (start < end) {
                return;
            }
            end = line.indexOf("\"", start);
            if (start == -1 || end == -1) {
                return;
            }
            String title = line.substring(start, end);
            start = line.indexOf("content_url", end) + 14;
            end = line.indexOf("\"", start);
            String content_url = line.substring(start, end);
            if (start == -1 || end == -1) {
                return;
            }
            ArticleBean bean = new ArticleBean();
            bean.setDatetime(datetime);
            bean.setTitle(title);
            bean.setContent_url(content_url);
            rs.add(bean);
        }
    }
}
