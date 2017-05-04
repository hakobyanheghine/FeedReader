package com.zeroapp.feedreader.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zeroapp.feedreader.R;
import com.zeroapp.feedreader.data.RssFeedItemData;
import com.zeroapp.feedreader.manager.RssFeedManager;
import com.zeroapp.feedreader.views.adapter.RssFeedAdapter;

public class RssFeedActivity extends AppCompatActivity {

    private RecyclerView rssFeedRecyclerView;
    private RssFeedAdapter rssFeedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rss_feed);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(RssFeedManager.getInstance().rssFeedData.getTitle());
        }

        rssFeedRecyclerView = (RecyclerView) findViewById(R.id.recycler_rss_feed);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rssFeedRecyclerView.setLayoutManager(linearLayoutManager);
        rssFeedRecyclerView.setHasFixedSize(true);

        rssFeedRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        rssFeedAdapter = new RssFeedAdapter(RssFeedManager.getInstance().rssFeedItemsToShow, new RssFeedAdapter.RssFeedItemOnClickHandler() {
            @Override
            public void onClick(int position, RssFeedItemData rssFeedItem) {
                rssFeedItem.setExpanded(!rssFeedItem.isExpanded());
                rssFeedAdapter.notifyItemChanged(position);
            }
        });
        rssFeedRecyclerView.setAdapter(rssFeedAdapter);

        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl_rss_feed);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // update the list
                RssFeedManager.getInstance().loadRssFeed(new RssFeedManager.RssFeedLoadingListener() {
                    @Override
                    public void onFeedLoadSuccess() {
                        rssFeedAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onFeedLoadFailure() {

                    }
                });
            }
        });
    }
}
