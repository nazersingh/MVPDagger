package com.nazer.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.nazer.R;
import com.nazer.ui.adapter.StartupViewPagerAdapter;
import com.nazer.ui.fragments.AppliedJobsFragment;

import java.util.ArrayList;

public class JobActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private StartupViewPagerAdapter mViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job);
        init();

    }
    private void init(){
        mTabLayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.pager);
        ArrayList<Fragment> fragments=new ArrayList<>();
        fragments.add(new AppliedJobsFragment());
        fragments.add(new AppliedJobsFragment());
        fragments.add(new AppliedJobsFragment());
        mViewPagerAdapter=new StartupViewPagerAdapter(getSupportFragmentManager(),fragments);
        mViewPager.setAdapter(mViewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
       //
        // mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        show();
    }

    private void show() {
        for (int i = 0; i <=2; i++) {
            View view = getTabView(i);
            mTabLayout.getTabAt(i).setCustomView(view);
        }
    }

    public View getTabView(int pos) {

        View tabview=LayoutInflater.from(this).inflate(R.layout.layout_tab, null);
        TextView tabtitle = tabview.findViewById(R.id.text_tab_title);
        TextView tvcount=tabview.findViewById(R.id.text_badge_count);

        switch (pos) {
            case 0:
                tabtitle.setText("UPCOMING");
                tvcount.setVisibility(View.INVISIBLE);
                tabtitle.setTextColor(getResources().getColor(R.color.white));
                return tabview;

            case 1:
                tabtitle.setText("APPLIED");
                tvcount.setText("10");
                return tabview;

            case 2:
                tabtitle.setText("COMPLETED");
                tvcount.setVisibility(View.INVISIBLE);
                return tabview;
        }

        return tabview;
    }
}
