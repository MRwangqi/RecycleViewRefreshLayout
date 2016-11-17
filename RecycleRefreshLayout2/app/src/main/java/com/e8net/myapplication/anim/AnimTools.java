package com.e8net.myapplication.anim;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.OvershootInterpolator;

/**
 * Created by Administrator on 2016/11/16.
 */
public class AnimTools {

    static int sizeX[] = new int[]{-300, 0, 300};
    static int sizeY[] = new int[]{-200, -200, -200};

    public static void openAnimator(View arr[], View result) {
        AnimatorSet set;

        ObjectAnimator obj = ObjectAnimator.ofFloat(result, "rotation", 0f, 45f);
        obj.setDuration(300);
        obj.start();


        for (int i = 0; i < arr.length; i++) {
            ObjectAnimator obj1 = ObjectAnimator.ofFloat(arr[i], "translationX", 0f, sizeX[i]);
            ObjectAnimator obj2 = ObjectAnimator.ofFloat(arr[i], "translationY", 0f, sizeY[i]);
            set = new AnimatorSet();
            set.setStartDelay(i * 100);
            set.play(obj1).with(obj2);
            set.setInterpolator(new OvershootInterpolator());
            set.setDuration(500);
            set.start();
        }
    }


    public static void closeAnimator(View arr[], View result, final AnimatorEnd animatorEnd) {
        ObjectAnimator obj = ObjectAnimator.ofFloat(result, "rotation", 45f, 0f);
        obj.setDuration(300);
        obj.start();

        AnimatorSet set = null;
        for (int i = 0; i < arr.length; i++) {
            ObjectAnimator obj1 = ObjectAnimator.ofFloat(arr[i], "translationX", sizeX[i], 0f);
            ObjectAnimator obj2 = ObjectAnimator.ofFloat(arr[i], "translationY", sizeY[i], 0f);
            set = new AnimatorSet();
            set.setStartDelay(i * 100);
            set.play(obj1).with(obj2);
            set.setInterpolator(new AccelerateDecelerateInterpolator());
            set.setDuration(500);
            set.start();
        }


        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animatorEnd.end();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    public interface AnimatorEnd {
        void end();
    }
}
