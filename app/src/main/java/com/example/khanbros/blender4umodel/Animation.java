package com.example.khanbros.blender4umodel;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Animation {

    public  static void animate(RecyclerView.ViewHolder holder,boolean goesdown){
        AnimatorSet animatorSet=new AnimatorSet();
        ObjectAnimator objectAnimator= ObjectAnimator.ofFloat(holder.itemView,"translationY",goesdown==true?200:-200,0);
        objectAnimator.setDuration(1000);
        animatorSet.playTogether(objectAnimator);
        animatorSet.start();
    }
    public static void like(Context context, final  ImageView imageView){

        android.view.animation.Animation animation= AnimationUtils.loadAnimation(context,R.anim.inter);
        imageView.startAnimation(animation);


     /*   final PropertyValuesHolder propertyValuesHolder=PropertyValuesHolder.ofFloat(View.SCALE_X,2);
        final PropertyValuesHolder propertyValuesHolder2=PropertyValuesHolder.ofFloat(View.SCALE_Y,2);
        final ObjectAnimator objectAnimator=ObjectAnimator.ofPropertyValuesHolder(imageView,propertyValuesHolder,propertyValuesHolder2);

        objectAnimator.setDuration(1000);
        AnimatorSet animatorSet=new AnimatorSet();

        animatorSet.playTogether(objectAnimator);
        animatorSet.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final PropertyValuesHolder propertyValuesHolde=PropertyValuesHolder.ofFloat(View.SCALE_X,1);
                final PropertyValuesHolder propertyValuesHolde2=PropertyValuesHolder.ofFloat(View.SCALE_Y,1);
                final ObjectAnimator objectAnimator2=ObjectAnimator.ofPropertyValuesHolder(imageView,propertyValuesHolde,propertyValuesHolde2);

                objectAnimator2.setDuration(1000);
                AnimatorSet animatorSe=new AnimatorSet();

                animatorSe.playTogether(objectAnimator2);
                animatorSe.start();
            }
        },1000) ;
    */}
    }

