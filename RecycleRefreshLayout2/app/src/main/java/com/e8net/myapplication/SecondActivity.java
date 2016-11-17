package com.e8net.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.e8net.myapplication.anim.AnimTools;

public class SecondActivity extends Activity {
    LinearLayout one, two, three;
    Button four;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        one = (LinearLayout) findViewById(R.id.one);
        two = (LinearLayout) findViewById(R.id.two);
        three = (LinearLayout) findViewById(R.id.three);
        four = (Button) findViewById(R.id.four);
        four.postDelayed(new Runnable() {
            @Override
            public void run() {
                AnimTools.openAnimator(new View[]{one, two, three}, four);
            }
        }, 300);

    }


    public void start(View v) {
        AnimTools.closeAnimator(new View[]{one, two, three}, four, new AnimTools.AnimatorEnd() {
            @Override
            public void end() {
                SecondActivity.this.finish();
                overridePendingTransition(0, 0);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Log.i("---","----------------back");
        AnimTools.closeAnimator(new View[]{one, two, three}, four, new AnimTools.AnimatorEnd() {
            @Override
            public void end() {
                SecondActivity.this.finish();
                overridePendingTransition(0, 0);
            }
        });
    }

}
