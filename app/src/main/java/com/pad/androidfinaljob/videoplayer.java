package com.pad.androidfinaljob;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PointF;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class videoplayer extends AppCompatActivity {
    private VideoView videoView;
    private ImageView iv;
    private ValueAnimator valueAnimator;
    private int width,height;
    private RelativeLayout rlLike;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videoplayer);

        String videoUrl2 = "http://jzvd.nathen.cn/video/1137e480-170bac9c523-0007-1823-c86-de200.mp4" ;
        Uri uri = Uri.parse( videoUrl2 );
        videoView = (VideoView)this.findViewById(R.id.videoView );
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.start();

        rlLike=(RelativeLayout) findViewById(R.id.rl_like);
        iv=(ImageView) findViewById(R.id.iv_easy_like);
        iv.getBackground().setAlpha(0);

        //点击暂停
        videoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(videoView.isPlaying()){
                    videoView.pause();
                }else{
                    videoView.start();
                }
                return false;
            }
        });

        //双击点赞
        rlLike.setOnTouchListener(new OnDoubleClickListener(new OnDoubleClickListener.DoubleClickCallback() {
            @Override
            public void onDoubleClick() {
                move();
            }
        }));




    }
    @SuppressLint("NewApi")
    private void move() {
        iv.setVisibility(View.VISIBLE);
        iv.getBackground().setAlpha(255);
        width = iv.getRight();
        height = iv.getBottom();
        valueAnimator = ValueAnimator.ofObject(new BezierEvaluator(),
                new PointF(iv.getLeft(), iv.getBottom() - iv.getHeight()),
                new PointF(width, 0));
        valueAnimator.setDuration(2000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) animation.getAnimatedValue();
                iv.setX(pointF.x);
                iv.setY(pointF.y);
                if ((Math.round(pointF.y)) < 150) {
                    iv.getBackground().setAlpha(Math.round(pointF.y));
                }

            }
        });
        valueAnimator.setTarget(iv);
        valueAnimator.setRepeatCount(0);
        valueAnimator.start();
    }

    @SuppressLint("NewApi")
    class BezierEvaluator implements TypeEvaluator<PointF> {

        @Override
        public PointF evaluate(float fraction, PointF startValue,
                               PointF endValue) {
            float oneMinusT = 1.0f - fraction;
            PointF point = new PointF();
            PointF point0 = (PointF) startValue;
            PointF point1 = new PointF();
            point1.set(width, 0);

            PointF point2 = new PointF();
            point2.set(0, height);

            PointF point3 = (PointF) endValue;

            point.x = oneMinusT * oneMinusT * oneMinusT * (point0.x) + 4
                    * oneMinusT * oneMinusT * fraction * (point0.x) + 2
                    * oneMinusT * fraction * fraction * (point0.x) + fraction
                    * fraction * fraction * (point0.x);

            point.y = oneMinusT * oneMinusT * oneMinusT * (point0.y)
                    + oneMinusT * oneMinusT * fraction * (point0.y) + 2
                    * oneMinusT * fraction * fraction * (point2.y) + fraction
                    * fraction * fraction * (point3.y);

            return point;
        }
    }


}