package org.taitasciore.android.videodemo;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.simplelist.MaterialSimpleListAdapter;
import com.afollestad.materialdialogs.simplelist.MaterialSimpleListItem;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private int mSelectedImpl;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_main);

        mViewPager = (ViewPager) findViewById(R.id.pager);

        showSelectImplementationDialog();
    }

    private void showSelectImplementationDialog() {
        final MaterialSimpleListAdapter adapter = new MaterialSimpleListAdapter(new MaterialSimpleListAdapter.Callback() {
            @Override
            public void onMaterialListItemSelected(MaterialDialog dialog, int index, MaterialSimpleListItem item) {
                mSelectedImpl = index;
                setupViewPager(mViewPager);
                dialog.dismiss();
            }
        });

        adapter.add(new MaterialSimpleListItem.Builder(this)
                .content("WebView")
                .backgroundColor(Color.WHITE)
                .build());

        adapter.add(new MaterialSimpleListItem.Builder(this)
                .content("YTPlayer")
                .backgroundColor(Color.WHITE)
                .build());

        new MaterialDialog.Builder(this)
                .title("Select implementation")
                .adapter(adapter, null)
                .cancelable(false)
                .canceledOnTouchOutside(false)
                .show();
    }

    private void setupViewPager(ViewPager pager) {
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(ImageFragment.newInstance("http://via.placeholder.com/420x640"));
        adapter.addFragment(ImageFragment.newInstance("http://via.placeholder.com/640x480"));
        adapter.addFragment(ImageFragment.newInstance("http://via.placeholder.com/1024x768"));
        adapter.addFragment(VideoFragment.newInstance(mSelectedImpl));
        pager.setAdapter(adapter);
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> mFragmentList = new ArrayList<>();

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment f) {
            mFragmentList.add(f);
        }
    }
}
