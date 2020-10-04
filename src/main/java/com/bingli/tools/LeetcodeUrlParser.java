package com.bingli.tools;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class LeetcodeUrlParser {
    public static void main(String[] args) throws IOException {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        File in = new File("src/main/resources/leetcode.html");
        Document doc = Jsoup.parse(in, "utf-8");
        Elements es = doc.select("a").select("#thumbnail");
        for (int i = 0; i < es.size(); i++) {
            String url = es.get(i).attr("href");
            if (url.contains("https://www.youtube.com/watch?v=")) {
                int start = url.indexOf("v=");
                String code = url.substring(start + 2, url.length());
                if (!isDownloaded(code)) {
                    System.out.println(url);
                    executor.submit(new DownloadThread(es.get(i).attr("href")));
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
}
