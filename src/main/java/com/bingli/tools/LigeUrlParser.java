package com.bingli.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class LigeUrlParser {
    public static void main(String[] args) throws Exception {
       download();
    }

    private static void download() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        List<String> ss = read();
        for (String url : ss) {
            System.out.println(url);
            if (url.contains("https://www.youtube.com/watch?v=")) {
                System.out.println(url);
                int start = url.indexOf("v=");
                String code = url.substring(start + 2, url.length());
                if (!isDownloaded(code)) {
                    executor.submit(new DownloadThread(url));
                }
            }
        }
        synchronized (LigeUrlParser.class) {
            try {
                LigeUrlParser.class.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void bp() throws IOException {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        File in = new File("src/main/resources/lige.html");
        Document doc = Jsoup.parse(in, "utf-8");
        Elements es = doc.select("a").select("#thumbnail");
        for (int i = 0; i < es.size(); i++) {
            String url = es.get(i).attr("href");
            if (url.contains("/watch?v=")) {
                url = "https://www.youtube.com" + url;
                System.out.println(url);
                int start = url.indexOf("v=");
                String code = url.substring(start + 2, url.length());
                if (!isDownloaded(code)) {
                    //System.out.println(url);
                    //executor.submit(new DownloadThread(es.get(i).attr("href")));
                }
            }
        }
        synchronized (in) {
            try {
                in.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean isDownloaded(String url) {
        File dir = new File(System.getProperty("user.dir"));
        File[] files = dir.listFiles(new FilenameFilter() {
            
            @Override
            public boolean accept(File dir, String name) {
                if(name.endsWith(".mp4") || name.endsWith(".webm")) {
                    return true;
                }
                return false;
            }
        });
        for (File file : files) {
            if (file.getName().contains(url)) {
                return true;
            }
        }
        return false;
    }
    
    private static List<String> read() throws Exception{
        List<String> ss = new ArrayList<>();
        BufferedReader r = new BufferedReader(new FileReader(LigeUrlParser.class.getClassLoader().getResource("lige.txt").getFile()));
        while (r.ready()) {
            ss.add(r.readLine());
        }
        return ss;
    }
}
