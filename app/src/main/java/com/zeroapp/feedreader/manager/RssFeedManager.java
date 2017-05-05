package com.zeroapp.feedreader.manager;

import com.crazyhitty.chdev.ks.rssmanager.Channel;
import com.crazyhitty.chdev.ks.rssmanager.RSS;
import com.crazyhitty.chdev.ks.rssmanager.RssReader;
import com.zeroapp.feedreader.data.RssFeedData;
import com.zeroapp.feedreader.data.RssFeedItemData;
import com.zeroapp.feedreader.networking.NetworkService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heghine on 5/2/17.
 */

public class RssFeedManager {
    private static final RssFeedManager instance = new RssFeedManager();

    private RssFeedManager() {

    }

    public static RssFeedManager getInstance() {
        return instance;
    }

    /**
     * number of rss feed items to load at a time
     */
    public static final int PAGE_SIZE = 10;

    private int lastLoadedItemIndex;

    /**
     * default url from where to load rss feed
     */
    private static final String DEFAULT_URL = "http://www.ag.ru/rss/ag.xml";

    /**
     * rss feed loading listener to transfer data to activity
     */
    private RssFeedLoadingListener rssFeedLoadingListener;

    /**
     * list of items that are shown
     */
    public List<RssFeedItemData> rssFeedItemsToShow = new ArrayList<>();

    /**
     * the whole rss feed data
     */
    public RssFeedData rssFeedData = new RssFeedData();

    /**
     * callback for loading rss feed
     */
    private RssReader.RssCallback rssCallback = new RssReader.RssCallback() {
        @Override
        public void rssFeedsLoaded(List<RSS> rssList) {
            // converting data to our format
            RSS rss = rssList.get(0);
            rssFeedData.setTitle(rss.getChannel().getTitle());

            List<Channel.Item> items = rss.getChannel().getItems();
            ArrayList<RssFeedItemData> rssFeedItems = new ArrayList<>();

            for (int i = 0; i < items.size(); i++) {
                RssFeedItemData rssFeedItemData = new RssFeedItemData();
                rssFeedItemData.setTitle(items.get(i).getTitle());
                rssFeedItemData.setDescription(items.get(i).getDescription());
                rssFeedItems.add(rssFeedItemData);
            }
            rssFeedData.setItems(rssFeedItems);

            // getting items to be shown
            rssFeedItemsToShow = getNextPaginationItemsToShow();

            if (rssFeedLoadingListener != null) {
                rssFeedLoadingListener.onFeedLoadSuccess();
            }
        }

        @Override
        public void unableToReadRssFeeds(String errorMessage) {
            if (rssFeedLoadingListener != null) {
                rssFeedLoadingListener.onFeedLoadFailure();
            }
        }
    };

    public void initialize() {
        lastLoadedItemIndex = 0;
        NetworkService.init(rssCallback);
    }

    public void loadRssFeed(RssFeedLoadingListener listener) {
        rssFeedLoadingListener = listener;
        NetworkService.getFeed(DEFAULT_URL);
    }

    /**
     * calculates the rss feed items to be shown
     * depending on the last loaded item date
     * @return list of items to show
     */
    private List<RssFeedItemData> getNextPaginationItemsToShow() {
        List<RssFeedItemData> itemsToShow;
        List<RssFeedItemData> allItems = rssFeedData.getItems();

        int lastIndex = allItems.size() - 1;
        if (lastIndex > lastLoadedItemIndex + PAGE_SIZE) { // if there are still lastLoadedItemIndex + PAGE_SIZE items
            itemsToShow = allItems.subList(lastLoadedItemIndex, lastLoadedItemIndex + PAGE_SIZE);
            lastLoadedItemIndex += PAGE_SIZE;
        } else { // otherwise we reached to the end
            itemsToShow = allItems.subList(lastLoadedItemIndex, lastIndex);
            lastLoadedItemIndex = lastIndex;
        }

        return itemsToShow;
    }

    public interface RssFeedLoadingListener {
        void onFeedLoadSuccess();
        void onFeedLoadFailure();
    }
}
