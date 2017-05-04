package com.zeroapp.feedreader.data;

/**
 * Created by heghine on 5/4/17.
 */

public class RssFeedItemData {
    private String title;
    private String description;

    private boolean isExpanded;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }
}
