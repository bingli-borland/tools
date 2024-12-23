package com.bingli.tools;

public class JarBean {

    private String groupId;
    private String artifactId;
    private String version;
    private String lauguage;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getLauguage() {
        return lauguage;
    }

    public void setLauguage(String lauguage) {
        this.lauguage = lauguage;
    }

    @Override
    public String toString() {
        return "JarBean{" +
                "groupId='" + groupId + '\'' +
                ", artifactId='" + artifactId + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
