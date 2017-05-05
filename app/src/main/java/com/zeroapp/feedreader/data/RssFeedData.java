package com.zeroapp.feedreader.data;

import java.util.ArrayList;

/**
 * Created by heghine on 5/3/17.
 */

public class RssFeedData {
    private String title;
    private String description;

    private ArrayList<RssFeedItemData> items;

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

    public ArrayList<RssFeedItemData> getItems() {
        return items;
    }

    public void setItems(ArrayList<RssFeedItemData> items) {
        this.items = items;
    }
}
