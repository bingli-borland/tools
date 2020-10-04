package com.bingli.https.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

public class HttpsTest {
    // 客户端证书
    public static String KEY_STORE_FILE = "E:\\project\\TOMCAT_7_0_83\\output\\build\\conf\\client.p12";
    // 客户端证书密码
    public static String KEY_STORE_PASS = "changeit";
    // 客户端证书信任库
    public static String TRUST_STORE_FILE = "E:\\project\\TOMCAT_7_0_83\\output\\build\\conf\\cacerts.jks";
    // 客户端证书信任库密码
    public static String TRUST_STORE_PASS = "changeit";

    private static SSLContext sslContext;

    /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     * 
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            // 如果connection是HttpsConnection，则设置SSLContext
            if (connection instanceof HttpsURLConnection) {
                ((HttpsURLConnection) connection).setSSLSocketFactory(getSSLContext().getSocketFactory());
            }

            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应

            if (connection.getResponseCode() == 200) {
                in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            } else {
                in = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }

        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    public static SSLContext getSSLContext() {
        long time1 = System.currentTimeMillis();
        if (sslContext == null) {
            try {
                KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
                kmf.init(getkeyStore(), KEY_STORE_PASS.toCharArray());
                KeyManager[] keyManagers = kmf.getKeyManagers();

                TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
                trustManagerFactory.init(getTrustStore());
                TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();

                sslContext = SSLContext.getInstance("TLS");
                sslContext.init(keyManagers, trustManagers, new SecureRandom());
                HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        // 校验证书上的网址和访问的是否一致
                        return true;
                    }
                });
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (UnrecoverableKeyException e) {
                e.printStackTrace();
            } catch (KeyStoreException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }
        }
        long time2 = System.currentTimeMillis();
        System.out.println("SSLContext 初始化时间：" + (time2 - time1));
        return sslContext;
    }

    /**
     * 获取证书KeyStore
     * 
     * @return
     */
    public static KeyStore getkeyStore() {
        KeyStore keySotre = null;
        try {
            // client.p12证书类型是PKCS12
            keySotre = KeyStore.getInstance("PKCS12");
            FileInputStream fis = new FileInputStream(new File(KEY_STORE_FILE));
            keySotre.load(fis, KEY_STORE_PASS.toCharArray());
            fis.close();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return keySotre;
    }

    /**
     * 获取信任库TrustStore
     * 
     * @return
     * @throws IOException
     */
    public static KeyStore getTrustStore() throws IOException {
        KeyStore trustKeyStore = null;
        FileInputStream fis = null;
        try {
            // cacert.jks的证书类型是JKS
            trustKeyStore = KeyStore.getInstance("JKS");
            fis = new FileInputStream(new File(TRUST_STORE_FILE));
            trustKeyStore.load(fis, TRUST_STORE_PASS.toCharArray());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            fis.close();
        }
        return trustKeyStore;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        // 设置TLS版本
        System.setProperty("https.protocols", "TLSv1.2");
        String result = sendGet("https://www.bingli.com:8443/examples/servlets/servlet/RequestParamExample", "a=x1");
        System.out.println(result);
    }
}
