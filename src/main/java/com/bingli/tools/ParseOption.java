package com.bingli.tools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/**
 * Servlet for upload file
 */
@WebServlet(name = "ParseOption", urlPatterns = "/ParseOption")
public class ParseOption extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<ArticleBean> rs = new ArrayList<ArticleBean>();
        String datafile = ParseOption.class.getResource("/qq.html").getPath();
        File in = new File(datafile);
        Document doc = Jsoup.parse(in, "utf-8");
        Elements es = doc.select("div").select(".js_card");
        for (int i = 0; i < es.size(); i++) {
            String datetime = es.get(i).select(".weui_msg_card_hd").first().text();
            Element e = es.get(i).select(".weui_media_title").first();
            if (e == null) {
                
                continue;
            }
            String url = e.attr("hrefs");
            String title = e.text();
            ArticleBean b = new ArticleBean();
            b.setContent_url(url);
            b.setDatetime(datetime);
            b.setTitle(title);
            rs.add(b);
            System.out.println(b);
        }
        //Collections.sort(rs);
        req.getSession().setAttribute("beans", rs);
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
    
}
