package com.bingli.performance;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

public class TestHttp {

    private static int invokeCount = 0;

    private static final boolean keepAlive = Boolean.parseBoolean(System.getProperty("http.keepAlive", "true"));

    public static void main(String[] args) {
        String errorLog = "error.log";
        String testurl = "http://localhost:8080/WebContainerTest/";
        if (args.length >= 2) {
                testurl = args[0];
                errorLog = args[1];
        } else {
            System.err.println("Usage: ");
            System.err.println("java -jar test.jar com.bingli.performance.TestHttp [testurl 'http://localhost:8080/WebContainerTest/'] [error log path 'error.log']");
            System.err.println("example: ");
            System.err.println("java -jar test.jar com.bingli.performance.TestHttp http://localhost:8080/WebContainerTest/ /home/bingli/error.log ");
            System.exit(1);
        }
        System.out.println("test url: " + testurl);
        System.out.println("error log: " + new File(errorLog).getAbsolutePath());
        test(testurl, errorLog);
    }

    private static void test( String testurl, String errorLog) {
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        String url = testurl;
        byte[] buffer = new byte[1024];
        try {
            while (true) {
                httpURLConnection = createHttpConnection(url);
                int count = 0;
                int reponseCode = httpURLConnection.getResponseCode();
                if (reponseCode != 200) {
                    storeMsg(errorLog, "response code :" + reponseCode + "\n");
                    inputStream = httpURLConnection.getInputStream();
                    while (-1 != (count = inputStream.read(buffer))) {
                        if (count == 0) {
                            Thread.sleep(100l);
                        }
                        String error = new String(buffer, 0 , count);
                        storeMsg(errorLog, error);
                    }
                    storeMsg(errorLog, "\n");
                    System.err.println("response code " + reponseCode +" is not 200. see " + errorLog + " for detail!");
                    break;
                }
                inputStream = httpURLConnection.getInputStream();
                while (-1 != (count = inputStream.read(buffer))) {
                    if (count != 0) {
                        continue;
                    }
                    Thread.sleep(100);
                }
                close(inputStream);
                invokeCount++;
            }
        } catch (Exception e) {
            System.out.println("invoke count: "  + invokeCount );
            e.printStackTrace();
            try {
                storeMsg(errorLog, e.toString() + ": " + e.getMessage() + "\ninvoke count: " + invokeCount);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                // ignore
            }
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
    }

    private static void storeMsg(String errorLog, String s) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(errorLog, "rw");
        randomAccessFile.seek(randomAccessFile.length());
        randomAccessFile.write(s.getBytes());
        randomAccessFile.close();
    }

    private static void close(Closeable inputStream) throws IOException {
        if (inputStream != null) {
            inputStream.close();
        }
    }

    private static HttpURLConnection createHttpConnection(String url) throws IOException {
        URL testUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) testUrl.openConnection();
        connection.setConnectTimeout(60000);
        connection.setReadTimeout(60000);
        connection.setUseCaches(false);
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:84.0) Gecko/20100101 Firefox/84.0");
        // keep-alive
        if (!keepAlive) {
            connection.setRequestProperty("Connection", "close");
        }
        connection.connect();
        return connection;
    }
}
