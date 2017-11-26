package com.xyb.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.xyb.like.BeatNumber;
import com.xyb.like.ThumbView;


public class MainActivity extends AppCompatActivity {
    ThumbView mThumbView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mThumbView= (ThumbView) findViewById(R.id.thumbview);
    }

    public void add(View view) {
        BeatNumber likeView = (BeatNumber) findViewById(R.id.like_view);
        likeView.setStatus(BeatNumber.ADD);
    }

    public void reduce(View view) {
        BeatNumber likeView = (BeatNumber) findViewById(R.id.like_view);
        likeView.setStatus(BeatNumber.REDUCCE);
    }

    public void like(View view) {
        mThumbView.setStutas(ThumbView.TO_THUMB);
    }

    public void notLike(View view) {
        mThumbView.setStutas(ThumbView.TO_NOT_THUMB);
    }
}
