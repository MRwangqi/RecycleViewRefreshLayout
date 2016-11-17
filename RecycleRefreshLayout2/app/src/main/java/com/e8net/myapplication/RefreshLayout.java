package com.e8net.myapplication;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by wangqi on 2016/11/8.
 */
public class RefreshLayout extends LinearLayout {

    RefreshRecycleView refreshRecycleView;
    SwipeRefreshLayout swipeRefreshLayout;
    View v;

    public RefreshLayout(Context context) {
        super(context);
        initView(context);
    }


    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public RefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }


    private void initView(final Context context) {
        v = LayoutInflater.from(context).inflate(R.layout.refresh_view, this, false);
        refreshRecycleView = (RefreshRecycleView) v.findViewById(R.id.refresh_recycler);

        refreshRecycleView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.refresh_swip);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        this.addView(v, params);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onRefreshListner.refresh();
            }
        });
        //加载更多
        refreshRecycleView.setLoadMoreListner(new RefreshRecycleView.LoadMoreListner() {
            @Override
            public void onLoad() {
                if (loadFlag)
                    onRefreshListner.loadMore();
            }
        });
    }

    boolean loadFlag = true;

    public void setLoadingMoreEnabled(boolean flag) {
        refreshRecycleView.setLoadingMoreEnabled(flag);
        this.loadFlag = flag;
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        refreshRecycleView.setLayoutManager(layoutManager);
    }

    public void setAdapter(RecyclerView.Adapter<BaseViewHolder> adapter) {
        refreshRecycleView.setAdapter(adapter);
    }


    public void setRefreshComplete() {
        swipeRefreshLayout.setRefreshing(false);
    }


    private RefreshListner onRefreshListner;

    public void setOnRefreshListner(RefreshListner onRefreshListner) {
        this.onRefreshListner = onRefreshListner;
    }


    public interface RefreshListner {
        void refresh();

        void loadMore();
    }
}
