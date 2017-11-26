package com.xyb.like;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by xuyabo on 2017/11/19.
 */

public class LikeView extends LinearLayout {
    private int mStatus = LIKE;
    private int mLikeNum = 98;

    public static final int LIKE = 0;
    public static final int NOT_LIKE = 1;
    private BeatNumber beatNumber;
    private ThumbView thumbView;

    public LikeView(Context context) {
        this(context, null);
    }

    public LikeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LikeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();

    }

    private void init() {
        beatNumber = new BeatNumber(getContext());
        thumbView = new ThumbView(getContext());
        addView(thumbView);
        addView(beatNumber);

        if (mStatus == LIKE) {
            thumbView.setStutas(ThumbView.THUMB);
        } else {
            thumbView.setStutas(ThumbView.NOT_THUMB);

        }

        beatNumber.setLikeNumer(mLikeNum);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mStatus == LIKE) {
                    mStatus = NOT_LIKE;
                    thumbView.setStutas(ThumbView.NOT_THUMB);
                    beatNumber.setStatus(BeatNumber.REDUCCE);
                } else {
                    mStatus = LIKE;
                    thumbView.setStutas(ThumbView.THUMB);
                    beatNumber.setStatus(BeatNumber.ADD);
                }
            }
        });
    }


}
