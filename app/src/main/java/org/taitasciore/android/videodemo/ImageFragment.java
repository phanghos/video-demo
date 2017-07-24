package org.taitasciore.android.videodemo;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by roberto on 21/07/17.
 */

public class ImageFragment extends Fragment {

    private String mUrl;

    private SimpleDraweeView mDraweeView;

    public static ImageFragment newInstance(String url) {
        ImageFragment f = new ImageFragment();
        Bundle extras = new Bundle();
        extras.putString("url", url);
        f.setArguments(extras);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_image, container, false);
        mDraweeView = (SimpleDraweeView) v.findViewById(R.id.drawee_view);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUrl = getArguments().getString("url");
        mDraweeView.setImageURI(Uri.parse((mUrl)));
    }
}
