package com.e8net.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by wangqi on 2016/11/8.
 */
public class RefreshRecycleView extends RecyclerView {
    public RefreshRecycleView(Context context) {
        super(context);
    }

    public RefreshRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RefreshRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        //获取在各个不同的LayoutManager中可见的position位置
        if (state == RecyclerView.SCROLL_STATE_IDLE && loadMoreListner != null && loadingMoreEnabled) {
            LayoutManager layoutManager = getLayoutManager();
            int lastVisibleItemPosition;
            if (layoutManager instanceof GridLayoutManager) {
                lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                int[] into = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(into);
                lastVisibleItemPosition = findMax(into);
            } else {
                lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
            }
            //触发加载更多事件
            if (flag && loadingMoreEnabled && layoutManager.getChildCount() > 0 && lastVisibleItemPosition >= layoutManager.getItemCount() - 1 && layoutManager.getItemCount() >= layoutManager.getChildCount()) {
                loadMoreListner.onLoad();
            }
        }
    }

    public boolean flag;


    float startY = 0;
    float moveY = 0;

    /**
     * @param e
     * @return 处理数据不满一屏的手势判断
     */
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = e.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                moveY = e.getRawY();
                if (moveY > startY)
                    flag = false;
                else
                    flag = true;
                startY = moveY;
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onTouchEvent(e);
    }

    //设置不给上拉加载
    public void setLoadingMoreEnabled(boolean loadingMoreEnabled) {
        this.loadingMoreEnabled = loadingMoreEnabled;
    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }


    boolean loadingMoreEnabled = true;
    LoadMoreListner loadMoreListner;

    public void setLoadMoreListner(LoadMoreListner loadMoreListner) {
        this.loadMoreListner = loadMoreListner;
    }

    public interface LoadMoreListner {
        void onLoad();
    }

}
