package com.zeroapp.feedreader.views.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.zeroapp.feedreader.R;
import com.zeroapp.feedreader.data.RssFeedItemData;

import java.util.ArrayList;

/**
 * Created by heghine on 3/27/17.
 */

public class RssFeedAdapter extends RecyclerView.Adapter<RssFeedAdapter.RssFeedAdapterViewHolder> {

    private ArrayList<RssFeedItemData> rssFeedItems;

    private final RssFeedItemOnClickHandler rssFeedItemOnClickHandler;

    public RssFeedAdapter(ArrayList<RssFeedItemData> rssFeedItems, RssFeedItemOnClickHandler rssFeedItemOnClickHandler) {
        this.rssFeedItems = rssFeedItems;
        this.rssFeedItemOnClickHandler = rssFeedItemOnClickHandler;
    }

    @Override
    public RssFeedAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_rss_feed, parent, false);

        return new RssFeedAdapterViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return rssFeedItems.size();
    }


    @Override
    public void onBindViewHolder(RssFeedAdapterViewHolder holder, int position) {
        RssFeedItemData rssFeedItem = rssFeedItems.get(position);

        holder.rssFeedTitleTxt.setText(rssFeedItem.getTitle());
        if (rssFeedItem.isExpanded()) {
            holder.rssFeedDescWebView.setVisibility(View.VISIBLE);
            holder.rssFeedDescWebView.loadData(rssFeedItem.getDescription(), "text/html", null);
        } else {
            holder.rssFeedDescWebView.setVisibility(View.GONE);
        }
    }

    public class RssFeedAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView rssFeedTitleTxt;
        public WebView rssFeedDescWebView;

        public RssFeedAdapterViewHolder(View itemView) {
            super(itemView);

            rssFeedTitleTxt = (TextView) itemView.findViewById(R.id.txt_rss_feed_title);
            rssFeedDescWebView = (WebView) itemView.findViewById(R.id.webview_rss_feed_description);
            rssFeedDescWebView.getSettings().setBuiltInZoomControls(true);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            rssFeedItemOnClickHandler.onClick(adapterPosition, rssFeedItems.get(adapterPosition));
        }
    }

    public interface RssFeedItemOnClickHandler {
        void onClick(int position, RssFeedItemData rssFeedItem);
    }
}
