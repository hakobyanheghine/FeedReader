package com.zeroapp.feedreader.networking;

import com.crazyhitty.chdev.ks.rssmanager.RssReader;

/**
 * Created by heghine on 5/2/17.
 */

public class NetworkService {

    private static RssReader rssReader;

    public static void init(RssReader.RssCallback callback) {
        rssReader = new RssReader(callback);
    }

    /**
     * loads rss feed with the given url
     * and returns data to pre-initialized callback
     */
    public static void getFeed(String url) {
        if (rssReader != null) {
            rssReader.loadFeeds(url);
        }
    }
}
