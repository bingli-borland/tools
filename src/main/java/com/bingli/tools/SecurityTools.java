package com.bingli.tools;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SecurityTools {

    private static Logger logger = Logger.getLogger(SecurityTools.class.getName());
    static {
        logger.setLevel(Level.OFF);
    }

    public static void main(String[] args) throws IOException {
        File bws = new File("E:\\java\\BWS1106\\console");
        File bwsHtml = new File("C:/Users/bes/Downloads/BES-webserver漏洞组件清单-real.html");
        File bes = new File("D:\\appserver\\bescluster6210");
        File besHtml = new File("C:/Users/bes/Downloads/BES-AppServer-Enterprise-9.5.5.6210-KYLIN10-X64漏洞组件清单-real.html");
        printResult(bws, bwsHtml);
        printResult(bes, besHtml);
    }

    private static void printResult(File dir, File html) throws IOException {
        Map<String, JarBean> product = new HashMap<>();
        parse(product, dir);
        Map<String, VulnerabilityBean> vulnerabilityBeanMap = parseHtml(html);
        // print all javascript
        System.out.println("######################################################################################all javascript vulnerability############################################################");
        for (Map.Entry<String, VulnerabilityBean> entry : vulnerabilityBeanMap.entrySet()) {
            VulnerabilityBean bean = entry.getValue();
            if(bean.getLauguage().equalsIgnoreCase("JavaScript") && !bean.getLevel().equalsIgnoreCase("无漏洞")){
                System.out.println(bean);
            }
        }

        // need upgrade
        System.out.println("######################################################################################all need upgrade vulnerability############################################################");
        for (Map.Entry<String, JarBean> jarEntry : product.entrySet()) {
            String groupid = jarEntry.getValue().getGroupId();
            String artifactId = jarEntry.getValue().getArtifactId();
            String version = jarEntry.getValue().getVersion();
            for (Map.Entry<String, VulnerabilityBean> entry : vulnerabilityBeanMap.entrySet()) {
                VulnerabilityBean vulnerabilityBean = entry.getValue();
                if(vulnerabilityBean.getLauguage().equalsIgnoreCase("JavaScript")
                        || vulnerabilityBean.getLevel().equalsIgnoreCase("无漏洞")
                        || vulnerabilityBean.getDirectDependency().equalsIgnoreCase("间接依赖")){
                    continue;
                }
                String vGroupId = vulnerabilityBean.getGroupId();
                String vArtifactId = vulnerabilityBean.getArtifactId();
                String vVersion = vulnerabilityBean.getVersion();
                if (groupid.equalsIgnoreCase(vGroupId) && artifactId.equalsIgnoreCase(vArtifactId) && version.equalsIgnoreCase(vVersion)) {
                    System.out.println(jarEntry.getKey() + " : " + vulnerabilityBean);
                }
            }
        }
    }

    private static Map<String, VulnerabilityBean> parseHtml(File html) throws IOException {
        Map<String, VulnerabilityBean> vulnerabilityBeanMap = new HashMap<>();
        Document doc = Jsoup.parse(html, "utf-8");
        Elements es = doc.select("div").select("#listOfDependencies").select("table").select(".el-table__body").first().select("tbody").select("tr");
        for (int i = 0; i < es.size(); i++) {
            Elements tds = es.get(i).select("td");
            VulnerabilityBean bean = new VulnerabilityBean();
            for (int j = 0; j < tds.size(); j++) {
                switch (j) {
                    case 0:
                        break;
                    case 1:
                        Elements spans = tds.get(j).select("span");
                        for (int k = 0; k < spans.size(); k++) {
                            switch (k) {
                                case 0:
                                    String groupId = spans.get(k).text();
                                    if (groupId != null && groupId.endsWith(":")) {
                                        bean.setGroupId(groupId.substring(0, groupId.length() - 1));
                                    } else {
                                        bean.setArtifactId(groupId);
                                    }
                                    break;
                                case 1:
                                    String artifactId = spans.get(k).text();
                                    if (artifactId != null && artifactId.startsWith("@")) {
                                        bean.setVersion(artifactId.substring(1));
                                    } else {
                                        bean.setArtifactId(artifactId);
                                    }
                                    break;
                                case 2:
                                    String version = spans.get(k).text();
                                    if (version != null && version.startsWith("@")) {
                                        bean.setVersion(version.substring(1));
                                    }
                                    break;
                                default:
                                    break;
                            }
                        }
                        break;
                    case 2:
                        String language = tds.get(j).select("div").select(".cell").text();
                        bean.setLauguage(language);
                        break;
                    case 3:
                        String level = tds.get(j).select("span").text();
                        bean.setLevel(level);
                        break;
                    case 4:
                        break;
                    case 5:
                        String direct = tds.get(j).select("div").select(".cell").text();
                        bean.setDirectDependency(direct);
                        break;
                    default:
                        break;
                }
            }
            logger.info(bean.toString());
            vulnerabilityBeanMap.put(bean.toString(), bean);
        }
        return vulnerabilityBeanMap;
    }

    private static void parse(Map<String, JarBean> result, File dir) throws IOException {
        File[] files = dir.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                parse(result, f);
            } else if (f.getName().endsWith(".jar")) {
                JarBean bean = new JarBean();
                bean.setLauguage("Java");
                MavenXpp3Reader mavenXpp3Reader = new MavenXpp3Reader();
                JarFile jarFile = null;
                InputStream in = null;
                try {
                    jarFile = new JarFile(f);
                    boolean find = false;
                    Enumeration<JarEntry> entrys = jarFile.entries();
                    while (entrys.hasMoreElements()) {
                        JarEntry jar = entrys.nextElement();
                        String name = jar.getName();
                        if (name.endsWith("pom.xml")) {
                            find = true;
                            logger.info("Entry file: " + name);
                            // jar内的文件只能通过流处理
                            in = jarFile.getInputStream(jarFile.getJarEntry(name));
                            Model model = mavenXpp3Reader.read(in);
                            bean.setGroupId(model.getGroupId() == null ? model.getParent().getGroupId() : model.getGroupId());
                            bean.setArtifactId(model.getArtifactId() == null ? model.getParent().getArtifactId() : model.getArtifactId());
                            bean.setVersion(model.getVersion() == null ? model.getParent().getVersion() : model.getVersion());
                            logger.info(bean.toString());
                            result.put(f.getName(), bean);
                            break;
                        }
                    }
                    if (!find) {
                        logger.info("No pom in " + f.getAbsolutePath());
                    }
                } catch (Exception e) {
                    logger.info(e.getMessage());
                } finally {
                    if (in != null) {
                        in.close();
                    }
                    if (jarFile != null) {
                        jarFile.close();
                    }
                }

            }
        }
    }
}
