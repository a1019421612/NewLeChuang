package com.hbdiye.newlechuangsmart.bean;

import com.chad.library.adapter.base.entity.SectionEntity;

import java.util.List;

public class SecneSectionBean extends SectionEntity<DeviceList> {
    private boolean ishead;
    private String title;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SecneSectionBean(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public boolean isIshead() {
        return ishead;
    }

    public void setIshead(boolean ishead) {
        this.ishead = ishead;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public SecneSectionBean(DeviceList content) {
        super(content);
    }
}
