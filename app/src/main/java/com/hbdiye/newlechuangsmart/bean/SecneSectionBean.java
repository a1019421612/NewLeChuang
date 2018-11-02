package com.hbdiye.newlechuangsmart.bean;

import com.chad.library.adapter.base.entity.SectionEntity;

import java.util.List;

public class SecneSectionBean extends SectionEntity<Content> {
    private boolean ishead;
    private String title;

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

    public SecneSectionBean(Content content) {
        super(content);
    }
}
