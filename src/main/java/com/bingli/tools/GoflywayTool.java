package com.bingli.tools;

import io.undertow.util.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class GoflywayTool {
    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect(args[0]).get();
        Element content = doc.getElementById("post-72");
        Elements ps = content.getElementsByClass("wp-block-code");
        String ip = null;
        String port = null;
        String pass = null;
        Element code = ps.get(0).getElementsByTag("code").get(0);
        String[] ss = code.text().split("\n");
        for (String str: ss) {
            String text = str;
            if (text != null && text.contains("IP：")) {
                ip = text.substring(text.indexOf("IP：") + 3);
            }
            if (text != null && text.contains("端口：")) {
                port = text.substring(text.indexOf("端口：") + 3);
            }
            if (text != null && text.contains("密码：")) {
                pass = text.substring(text.indexOf("密码：") + 3);
            }
        }
        String config = FileUtils.readFile(new FileInputStream(new File("D:\\soft\\Goflyway\\config.ini")));
        Properties props = new Properties();
        props.load(new FileInputStream(new File("D:\\soft\\Goflyway\\config.ini")));
        String old = (String) props.get("Node1");
        String newString = ip + " " + port + " " + pass + " 1 ";
        if (old == null || !old.equals(newString)) {
            config = config.replace(old, newString);
            System.out.println(config);
            FileCopyUtils.copy(config, new FileWriter("D:\\soft\\Goflyway\\config.ini"));
        }
        System.out.println("ok");
    }

}
