/*
 * Copyright 2012-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bingli.boot;

import static com.bingli.tools.ParserTool.parse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bingli.tools.ArticleBean;
import com.bingli.tools.ParseData;
import com.bingli.tools.ParseOption;
import com.bingli.tv.TVUrlParser;

@Controller
public class WelcomeController {

    @Value("${application.message:Hello World}")
    private String message = "Hello World";

    @GetMapping("/")
    public String welcome(Map<String, Object> model) {
        model.put("time", new Date());
        model.put("message", this.message);
        return "welcome";
    }

    @GetMapping("/ToUpdate")
    public String toUpdate(Map<String, Object> model) {
        return "update";
    }

    @PostMapping("/postData")
    public String postData(Map<String, Object> model, HttpServletRequest request) throws IOException {
        String content = request.getParameter("content");
        String path = ParseData.class.getResource("/data.txt").getPath();
        String tvPath = new File(path).getParent() + "/tv.html";
        writeStringToFile(content, new File(tvPath));
        return "update";
    }

    @PostMapping("/tv")
    public String tv(Map<String, Object> model, HttpServletRequest request) throws IOException {
        String type = request.getParameter("type");
        String name = request.getParameter("name");
        System.out.println("type=" + type);
        System.out.println("name=" + name);
        if (type == null || type.isEmpty()) {
            String datafile = ParseData.class.getResource("/tv.html").getPath();
            model.put("beans", TVUrlParser.parseHTML(datafile));
        } else {
            if (name == null || name.isEmpty()) {
                name = "https://www.360kan.com/tv/PbhrbH7lRzbqN3.html";
            }
            if (type.equals("json")) {
                model.put("beans", TVUrlParser.parseJson(name));
            } else {
                model.put("beans", TVUrlParser.parseURL(name));
            }
        }
        return "tv";
    }

    @GetMapping("/ParseData1")
    public String parseData(Map<String, Object> model) throws IOException {
        List<ArticleBean> rs = new ArrayList<ArticleBean>();
        BufferedReader br =new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("/data.txt"),"UTF-8"));
        String line = br.readLine();
        while (line != null) {
            parse(line, rs);
            line = br.readLine();
        }
        Collections.sort(rs);
        model.put("beans", rs);
        return "index";
    }

    @GetMapping("/ParseOption")
    public String parseOption(Map<String, Object> model) throws IOException {
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
            //System.out.println(b);
        }
        //Collections.sort(rs);
        model.put("beans", rs);
        return "index";
    }

    @GetMapping("/ParseGsq")
    public String ParseGsq(Map<String, Object> model) throws IOException {
        List<ArticleBean> rs = new ArrayList<ArticleBean>();
        String datafile = ParseOption.class.getResource("/gsq.html").getPath();
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
            //System.out.println(b);
        }
        //Collections.sort(rs);
        model.put("beans", rs);
        return "index";
    }
    @RequestMapping("/foo")
    public String foo(Map<String, Object> model) {
        throw new RuntimeException("Foo");
    }

    public void writeStringToFile(String s, File f) throws IOException {
        Writer writer = null;
        try {
            writer = new PrintWriter(f);
            writer.write(s);
        } finally {
            if (writer != null) {
                try {
                    writer.flush();
                    writer.close();
                } catch (Exception e) {
                }
                f.setReadable(true);
                f.setWritable(true);
            }
        }
    }
}
