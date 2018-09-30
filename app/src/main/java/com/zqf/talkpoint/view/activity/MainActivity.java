package com.zqf.talkpoint.view.activity;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.zqf.talkpoint.R;
import com.zqf.talkpoint.view.fragment.NewFragment;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Author：zqf
 * Time：2018/9/29 17:27
 * desc：
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    @BindView(R.id.main_content)
    ConstraintLayout mainContent;
    private NewFragment newFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }
}
