package com.zqf.talkpoint.view.fragment;

import com.zqf.talkpoint.model.GankResults;
import com.zqf.talkpoint.view.activity.WebActivity;
import com.zqf.talkpoint.view.adapter.NewAdapter;

import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;
import cn.droidlover.xrecyclerview.XRecyclerView;

/**
 * Author：zqf
 * Time：2018/9/30 15:32
 * desc：
 */
public class NewFragment extends BasePagerFragment {
    NewAdapter adapter;

    @Override
    public SimpleRecAdapter getAdapter() {
        if (adapter == null) {
            adapter = new NewAdapter(context);
            adapter.setRecItemClick(new RecyclerItemCallback<GankResults.Item, NewAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, GankResults.Item model, int tag, NewAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    switch (tag) {
                        case NewAdapter.TAG_VIEW:
                            WebActivity.launch(context, model.getUrl(), model.getDesc());
                            break;
                    }
                }
            });
        }
        return adapter;
    }

    @Override
    public void setLayoutManager(XRecyclerView recyclerView) {
        recyclerView.verticalLayoutManager(context);
    }

    @Override
    public String getType() {
        return "all";
    }

    public static NewFragment newInstance() {
        return new NewFragment();
    }
}
