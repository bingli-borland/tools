package com.bingli.tools;

import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RenameTool {

    public static void main(String[] args) {
        File[] files = getFiles();
        System.out.println(files.length);
        for (File file : files) {
            //System.out.println(file.getName());
            Pattern p = Pattern.compile("(Leetcode\\s)(\\d+)(\\s.*)");
            Matcher m = p.matcher(file.getName());
            if (m.matches()) {
                System.out.println(m.group(1) + " - " + m.group(2) + m.group(3));
                File f = new File("E:\\project\\tools", m.group(1) + " - " + m.group(2) + m.group(3));
                file.renameTo(f);
            }
        }
    }
    
    private static File[] getFiles() {
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
        return files;
    }
}
