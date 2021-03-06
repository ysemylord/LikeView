package com.xyb.like;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;



/**
 * Created by xuyabo on 2017/10/28.
 * 拇指效果
 */

public class ThumbView extends View {
    String TAG = "ThumbView";
    private Bitmap mThumbBitmap;
    private Bitmap mNotThumbBitmap;
    private Bitmap mShinerBitmap;


    private int mShinnerWidth;
    private int mShinnerHegiht;

    public final static int TO_NOT_THUMB = 0;
    public final static int TO_THUMB = 1;

    private int status = TO_NOT_THUMB;

    private final int THUMB_START_ANGLE = 130;//
    private final int THUMB_FINAL_ANGLE = 360;
    private final int SWEEP_MAX = THUMB_FINAL_ANGLE - THUMB_START_ANGLE;
    private final int SWEEP_MIN = 0;

    private int mNowSweepedAngle = 0;

    private Path mShinnerPath;
    private int mThumbLeftPosition;
    private int mThumTopPosition;

    private int mShinnerLeftPosition;
    private int mShinnerTopPosition;


    public ThumbView(Context context) {
        this(context, null);
    }

    public ThumbView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ThumbView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ThumbView,0,0);
        status=typedArray.getInt(R.styleable.ThumbView_status,TO_NOT_THUMB);
        typedArray.recycle();

        mNotThumbBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_messages_like_unselected);
        mThumbBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_messages_like_selected);
        mShinerBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_messages_like_selected_shining);

        mShinnerWidth = mShinerBitmap.getWidth();
        mShinnerHegiht = mShinerBitmap.getHeight();

        mThumbLeftPosition = 0 ;//拇指图片左上角开始绘制的坐标
        mThumTopPosition = mShinerBitmap.getHeight() - dpToPx(8);//拇指图片左上角开始绘制的坐标

        mShinnerLeftPosition=dpToPx(2);//光圈图片左上角开始绘制的坐标
        mShinnerTopPosition=0;//拇指图片左上角开始绘制的坐标

        mShinnerPath = new Path();


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(
                Math.max(mThumbBitmap.getWidth(), mShinerBitmap.getWidth()) ,
                mThumbBitmap.getHeight() + mShinerBitmap.getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        float progress = (mNowSweepedAngle * 1f) / SWEEP_MAX;

        if (status == TO_NOT_THUMB) {
            canvas.save();

            //绘制光圈
            Paint paint = new Paint();
            int alpha = (int) (progress * 255);
            paint.setAlpha(Math.max(alpha, 0));//alpha不小于0
            canvas.drawBitmap(mShinerBitmap, mShinnerLeftPosition, mShinnerTopPosition, paint);
            canvas.restore();

            //绘制拇指图片
            canvas.save();
            float scale;
            if (progress < 0.5f) {
                scale = 1 - progress;
            } else {
                scale = progress;
            }
            canvas.scale(scale, scale, mNotThumbBitmap.getWidth() / 2, mNotThumbBitmap.getHeight() / 2);
            canvas.drawBitmap(mNotThumbBitmap, mThumbLeftPosition, mThumTopPosition, new Paint());
            canvas.restore();


            if (mNowSweepedAngle > SWEEP_MIN) {
                mNowSweepedAngle -= 10;
                invalidate();
            }

        } else if (status == TO_THUMB) {
            canvas.save();

            //绘制光圈
            RectF shinnerRectF = new RectF(0f, 0f, mShinnerWidth + dpToPx(2), mShinnerHegiht + dpToPx(10));
            mShinnerPath.addArc(shinnerRectF, THUMB_START_ANGLE, mNowSweepedAngle);
            canvas.clipPath(mShinnerPath);

            Paint paint = new Paint();
            int alpha = (int) (progress * 255);

            paint.setAlpha(Math.min(alpha, 255));//alpha不能超过255
            canvas.drawBitmap(mShinerBitmap, mShinnerLeftPosition, mShinnerTopPosition, paint);

            canvas.restore();

            //绘制拇指图片
            canvas.save();
            float scale;
            if (progress < 0.5f) {
                scale = 1 - progress;
            } else {
                scale = progress;
            }
            canvas.scale(scale, scale, mShinerBitmap.getWidth() / 2, mShinerBitmap.getHeight() / 2);
            canvas.drawBitmap(mThumbBitmap, mThumbLeftPosition, mThumTopPosition, new Paint());
            canvas.restore();

            if (mNowSweepedAngle < SWEEP_MAX) {
                mNowSweepedAngle += 10;
                invalidate();
            }


        }
    }

    private int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    public void setStutas(int status) {
        this.status = status;
        invalidate();
    }
}
