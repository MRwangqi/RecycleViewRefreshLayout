package com.e8net.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RefreshLayout refreshLayout;
    List<String> list;
    BaseRecycleAdapter myRecycleAdapter;


    public void addList() {
        for (int i = 0; i < 10; i++)
            list.add("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        refreshLayout = (RefreshLayout) findViewById(R.id.refresh_layout);
        list = new ArrayList<>();
        addList();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        refreshLayout.setLayoutManager(layoutManager);


        myRecycleAdapter = new BaseRecycleAdapter(this, R.layout.recycle_item, list);


        refreshLayout.setAdapter(myRecycleAdapter);

        refreshLayout.setOnRefreshListner(new RefreshLayout.RefreshListner() {
            @Override
            public void refresh() {
                refreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        list.clear();
                        addList();
                        myRecycleAdapter.notifyDataSetChanged();
                        refreshLayout.setRefreshComplete();
                    }
                }, 2000);
            }

            @Override
            public void loadMore() {
                refreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        addList();
                        myRecycleAdapter.notifyDataSetChanged();
                        refreshLayout.setLoadMoreComplete();
                    }
                }, 2000);
            }
        });


        myRecycleAdapter.setOnItemClickListner(new BaseRecycleAdapter.OnItemClickListner() {
            @Override
            public void onItemClickListner(View v, int position) {
                Toast.makeText(MainActivity.this, "click---" + position, Toast.LENGTH_SHORT).show();
            }
        });

        myRecycleAdapter.setOnItemLongClickListner(new BaseRecycleAdapter.OnItemLongClickListner() {
            @Override
            public void onItemLongClickListner(View v, int position) {
                Toast.makeText(MainActivity.this, "longClick---" + position, Toast.LENGTH_SHORT).show();
            }
        });

        myRecycleAdapter.setCallBack(new BaseRecycleAdapter.CallBack() {
            @Override
            public <T> void convert(BaseViewHolder holder, T bean, final int position) {
                holder.setText(R.id.txt, "明天会更好" + position, new BaseViewHolder.OnClickListener() {
                    @Override
                    public void onClickListner(View v) {
                        Toast.makeText(MainActivity.this, "明天会更好" + position, Toast.LENGTH_SHORT).show();
                    }
                });

                holder.setImageListner(R.id.img, new BaseViewHolder.OnClickListener() {
                    @Override
                    public void onClickListner(View v) {
                        Toast.makeText(MainActivity.this, "img" + position, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
