package org.taitasciore.android.videodemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jaedongchicken.ytplayer.YoutubePlayerView;

/**
 * Created by roberto on 21/07/17.
 */

public class VideoFragment extends Fragment {

    private String mUrl;
    private int mSelectedImpl;

    private WebView mWebView;
    private YoutubePlayerView mYouTubePlayer;

    public static VideoFragment newInstance(int selectedImpl) {
        VideoFragment f = new VideoFragment();
        Bundle extras = new Bundle();
        extras.putInt("impl", selectedImpl);
        f.setArguments(extras);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSelectedImpl = getArguments().getInt("impl");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (mYouTubePlayer != null) {
            if (isVisibleToUser) {
                mYouTubePlayer.play();
            } else {
                mYouTubePlayer.pause();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mYouTubePlayer != null) {
            mYouTubePlayer.destroy();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = null;

        switch (mSelectedImpl) {
            case 0:
                v = inflater.inflate(R.layout.fragment_video_webview, container, false);
                mWebView = (WebView) v.findViewById(R.id.webview);
                break;
            case 1:
                v = inflater.inflate(R.layout.fragment_video_ytplayer, container, false);
                mYouTubePlayer = (YoutubePlayerView) v.findViewById(R.id.ytplayer);
                break;
        }

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        switch (mSelectedImpl) {
            case 0:
                handleWebViewImpl();
                break;
            case 1:
                handleYouTubePlayerImpl();
                break;
        }
    }

    private void handleWebViewImpl() {
        mUrl = "https://www.youtube.com/embed/wMKtd1nrm1c";
        String frameVideo = "<html><body><iframe width=\"320\" height=\"250\" src=\"" + mUrl + "\" frameborder=\"0\" allowfullscreen></iframe></body></html>";

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.loadData(frameVideo, "text/html", "utf-8");
    }

    private void handleYouTubePlayerImpl() {
        mUrl = "wMKtd1nrm1c";
        mYouTubePlayer.initialize(mUrl, null);
    }
}
