package com.e8net.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.e8net.myapplication.anim.AnimTools;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RefreshLayout refreshLayout;

    List<String> list = new ArrayList<>();
    BaseRecycleAdapter baseRecycleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        refreshLayout = (RefreshLayout) findViewById(R.id.refreshr);


        addList();

        TextView textView = new TextView(this);
        textView.setBackgroundColor(0xff67aabc);
        textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200));
        textView.setText("              I`m headView");


        baseRecycleAdapter = new BaseRecycleAdapter(this, R.layout.recycle_item, list);
        baseRecycleAdapter.addHeadView(textView);
        refreshLayout.setAdapter(baseRecycleAdapter);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "headView click", Toast.LENGTH_SHORT).show();
            }
        });

        baseRecycleAdapter.setOnItemClickListner(new BaseRecycleAdapter.OnItemClickListner() {
            @Override
            public void onItemClickListner(View v, int position) {
                Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });


        refreshLayout.setOnRefreshListner(new RefreshLayout.RefreshListner() {
            @Override
            public void refresh() {
                refreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        list.clear();
                        addList();
                        baseRecycleAdapter.notifyDataSetChanged();
                        refreshLayout.setRefreshComplete();
                    }
                }, 2000);
            }

            @Override
            public void loadMore() {
                baseRecycleAdapter.getFootView().setVisibility(View.VISIBLE);
                refreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        addList();
                        baseRecycleAdapter.notifyDataSetChanged();
                        baseRecycleAdapter.getFootView().setVisibility(View.GONE);
                    }
                }, 2000);
            }
        });
        baseRecycleAdapter.setCallBack(new BaseRecycleAdapter.CallBack() {
            @Override
            public <T> void convert(BaseViewHolder holder, T bean, final int position) {
                holder.setText(R.id.txt, position + "明天会更好", new BaseViewHolder.OnClickListener() {
                    @Override
                    public void onClickListner(View v) {
                        Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }


    public void addList() {
        for (int i = 0; i < 5; i++) {
            list.add("" + i);
        }
    }

}
