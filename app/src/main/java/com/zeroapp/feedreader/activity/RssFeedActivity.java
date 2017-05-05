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

        // TODO this part is for pagination, however there are some issues
//        rssFeedRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//
//                if (dy > 0) { //check for scroll down
//                    int visibleItemCount = linearLayoutManager.getChildCount();
//                    int totalItemCount = linearLayoutManager.getItemCount();
//                    int pastVisibleItems = linearLayoutManager.findFirstVisibleItemPosition();
//
//                    if (RssFeedManager.getInstance().loading) {
//                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
//                            RssFeedManager.getInstance().loading = false;
//                            int lastIndexBeforeInsert = RssFeedManager.getInstance().rssFeedItemsToShow.size() - 1;
//                            RssFeedManager.getInstance().rssFeedItemsToShow.addAll(RssFeedManager.getInstance().getNextPaginationItemsToShow());
//                            int lastIndexAfterInsert = RssFeedManager.getInstance().rssFeedItemsToShow.size() - 1;
//                            rssFeedAdapter.notifyItemRangeInserted(lastIndexBeforeInsert, lastIndexAfterInsert);
//                        }
//                    }
//                }
//            }
//        });

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
