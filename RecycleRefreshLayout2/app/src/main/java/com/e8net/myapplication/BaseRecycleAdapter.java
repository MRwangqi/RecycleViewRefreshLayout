package com.e8net.myapplication;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.List;

/**
 * Created by wangqi on 2016/7/16.
 */
public class BaseRecycleAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private int layoutId;
    private List<? extends Object> data;
    public Context context;
    private OnItemClickListner onItemClickListner;//单击事件
    private OnItemLongClickListner onItemLongClickListner;//长按单击事件
    private boolean clickFlag = true;//单击事件和长单击事件的屏蔽标识
    private static final int TYPE_HEADVIEW = 0;
    private static final int TYPE_NORMVIEW = 1;
    private static final int TYPE_FOOTVIEW = 2;
    private View mHeadView;
    private View mFootView;

    /**
     * @param context  //上下文
     * @param layoutId //布局id
     * @param data     //数据源
     */
    public BaseRecycleAdapter(Context context, int layoutId, List<? extends Object> data) {
        this.layoutId = layoutId;
        this.data = data;
        this.context = context;
        ProgressBar pb = new ProgressBar(context);
        pb.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
        this.mFootView = pb;
        this.mFootView.setVisibility(View.GONE);
    }

    public void addHeadView(View mHeadView) {
        this.mHeadView = mHeadView;
    }

    public void addFootView(View mFootView) {
        this.mFootView = mFootView;
        this.mFootView.setVisibility(View.GONE);
    }

    public View getHeadView() {
        return mHeadView;
    }

    public View getFootView() {
        return mFootView;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && mHeadView != null) return TYPE_HEADVIEW;
        if (position == data.size() + 1 && mFootView != null) return TYPE_FOOTVIEW;
        return TYPE_NORMVIEW;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_HEADVIEW && mHeadView != null)
            return new BaseViewHolder(mHeadView, context);
        if (viewType == TYPE_FOOTVIEW && mFootView != null)
            return new BaseViewHolder(mFootView, context);

        View v = LayoutInflater.from(context).inflate(layoutId, parent, false);
        final BaseViewHolder holder = new BaseViewHolder(v, context);
        //单击事件回调
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListner == null)
                    return;
                if (clickFlag) {
                    if (mHeadView != null)
                        onItemClickListner.onItemClickListner(v, holder.getLayoutPosition() - 1);
                }
                clickFlag = true;
            }
        });
        //单击长按事件回调
        v.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemLongClickListner == null)
                    return false;
                if (mHeadView != null)
                    onItemLongClickListner.onItemLongClickListner(v, holder.getLayoutPosition() - 1);
                clickFlag = false;
                return false;
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (mCallBack != null)
            if (mHeadView == null)
                mCallBack.convert(holder, data.get(position), position);
            else {
                if (position != 0 && position <= data.size())
                    mCallBack.convert(holder, data.get(position - 1), position - 1);
            }
    }

    @Override
    public int getItemCount() {
        int count = data.size();
        if (mHeadView != null) count++;
        if (mFootView != null) count++;
        return count;
    }

//    @Override
//    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
//        super.onAttachedToRecyclerView(recyclerView);
//        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
//        Log.i("-----", "----------" + manager);
//        if (manager instanceof GridLayoutManager) {
//            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
//            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//                @Override
//                public int getSpanSize(int position) {
//                    return getItemViewType(position) == TYPE_HEADVIEW
//                            ? gridManager.getSpanCount() : 1;
//                }
//            });
//        }
//    }

    public void setOnItemClickListner(OnItemClickListner onItemClickListner) {
        this.onItemClickListner = onItemClickListner;
    }

    public void setOnItemLongClickListner(OnItemLongClickListner onItemLongClickListner) {
        this.onItemLongClickListner = onItemLongClickListner;
    }

    public interface OnItemClickListner {
        void onItemClickListner(View v, int position);
    }

    public interface OnItemLongClickListner {
        void onItemLongClickListner(View v, int position);
    }


    CallBack mCallBack;

    public void setCallBack(CallBack CallBack) {
        this.mCallBack = CallBack;
    }

    public interface CallBack {
        <T extends Object> void convert(BaseViewHolder holder, T bean, int position);
    }

}
