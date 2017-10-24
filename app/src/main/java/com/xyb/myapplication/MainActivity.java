package com.xyb.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.xyb.myapplication.ui.LikeView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void add(View view) {
        LikeView likeView= (LikeView) findViewById(R.id.like_view);
        likeView.setStatus(LikeView.ADD);
    }

    public void reduce(View view) {
        LikeView likeView= (LikeView) findViewById(R.id.like_view);
        likeView.setStatus(LikeView.REDUCCE);
    }
}
