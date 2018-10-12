package com.zqf.talkpoint.view.activity;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.zqf.talkpoint.R;
import com.zqf.talkpoint.view.fragment.GirlFragment;
import com.zqf.talkpoint.view.fragment.NewFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.XFragmentAdapter;
import cn.droidlover.xdroidmvp.mvp.XActivity;


/**
 * Author：zqf
 * Time：2018/9/29 17:27
 * desc：
 */
public class MainActivity extends XActivity {

    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.vp)
    ViewPager viewPager;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    @BindView(R.id.main_content)
    ConstraintLayout mainContent;
    private NewFragment newFragment;
    List<Fragment> fragmentList = new ArrayList<>();
    String[] titles = {"首页", "干货", "妹子"};
    XFragmentAdapter adapter;

    @Override
    public void initData(Bundle savedInstanceState) {
        fragmentList.clear();
        fragmentList.add(NewFragment.newInstance());
//        fragmentList.add(GanhuoFragment.newInstance());
        fragmentList.add(GirlFragment.newInstance());
        if (adapter == null) {
            adapter = new XFragmentAdapter(getSupportFragmentManager(), fragmentList, titles);
        }
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);

        tabs.setupWithViewPager(viewPager);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public Object newP() {
        return null;
    }
}
