package com.xyb.like;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import static com.xyb.like.ThumbView.TO_NOT_THUMB;

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

        init(context, attrs);

    }

    private void init(Context context, AttributeSet attrs) {

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LikeView,0,0);
        int textSize=typedArray.getInt(R.styleable.LikeView_l_number_text_size,TO_NOT_THUMB);
        mStatus=typedArray.getInt(R.styleable.LikeView_l_status,0);
        mLikeNum=typedArray.getInt(R.styleable.LikeView_l_number,0);
        typedArray.recycle();

        beatNumber = new BeatNumber(getContext());
        beatNumber.setTextSize(textSize);
        thumbView = new ThumbView(getContext());
        addView(thumbView);
        addView(beatNumber);

        if (mStatus == LIKE) {
            thumbView.setStutas(ThumbView.TO_THUMB);
        } else {
            thumbView.setStutas(TO_NOT_THUMB);

        }

        beatNumber.setLikeNumer(mLikeNum);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mStatus == LIKE) {
                    mStatus = NOT_LIKE;
                    thumbView.setStutas(TO_NOT_THUMB);
                    beatNumber.setStatus(BeatNumber.REDUCCE);
                } else {
                    mStatus = LIKE;
                    thumbView.setStutas(ThumbView.TO_THUMB);
                    beatNumber.setStatus(BeatNumber.ADD);
                }
            }
        });
    }


}
