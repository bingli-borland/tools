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
import java.util.concurrent.atomic.AtomicReference;

public class GoflywayTool {
    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect(args[0]).get();
        Element content = doc.getElementById("post-72");
        Elements ps = content.getElementsByClass("wp-block-table");
        AtomicReference<String> ip = new AtomicReference<>();
        AtomicReference<String> port = new AtomicReference<>();
        AtomicReference<String> pass = new AtomicReference<>();
        ps.stream().forEach( e -> {
            Elements trs = e.getElementsByTag("tr");
            trs.stream().forEach(tr -> {
                if(tr.getElementsByTag("td").size() == 0) {
                    return;
                }
                if(tr.getElementsByTag("td").get(0).text().trim().contains("IP")){
                    ip.set(tr.getElementsByTag("td").get(1).text().trim());
                }
                if(tr.getElementsByTag("td").get(0).text().trim().contains("端口")){
                    port.set(tr.getElementsByTag("td").get(1).text().trim());
                }
                if(tr.getElementsByTag("td").get(0).text().trim().contains("密码")){
                    pass.set(tr.getElementsByTag("td").get(1).text().trim());
                }
            });

        });

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
