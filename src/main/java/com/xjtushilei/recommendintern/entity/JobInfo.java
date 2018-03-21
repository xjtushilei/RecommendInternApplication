package com.xjtushilei.recommendintern.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.util.Date;

@Entity
public class JobInfo {
    @Id
    private String id;
    private String title;
    private String url;
    private Date updateTime;
    private String address;

    @Lob
    private String content;

    public JobInfo() {
    }

    public JobInfo(String id, String title, String url, Date updateTime, String address, String content) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.updateTime = updateTime;
        this.address = address;
        this.content = content;
    }

    @Override
    public String toString() {
        return "JobInfo{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", updateTime=" + updateTime +
                ", address='" + address + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
