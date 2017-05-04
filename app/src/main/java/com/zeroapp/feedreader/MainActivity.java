package com.zeroapp.feedreader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import com.zeroapp.feedreader.activity.RssFeedActivity;
import com.zeroapp.feedreader.manager.RssFeedManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.pb_loading);
        RssFeedManager.getInstance().initialize();
        RssFeedManager.getInstance().loadRssFeed(new RssFeedManager.RssFeedLoadingListener() {
            @Override
            public void onFeedLoadSuccess() {
                // hide loading and continue to showing rss feed
                progressBar.setVisibility(View.GONE);
                startRssFeedActivity();
            }

            @Override
            public void onFeedLoadFailure() {
                // show message to user about the failure
            }
        });
    }

    private void startRssFeedActivity() {
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent rssFeedActivity = new Intent(MainActivity.this, RssFeedActivity.class);
                startActivity(rssFeedActivity);
            }
        });
    }
}
