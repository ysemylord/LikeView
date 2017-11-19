package com.xyb.myapplication.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.xyb.myapplication.R;

import static android.R.attr.max;
import static android.content.ContentValues.TAG;

/**
 * Created by xuyabo on 2017/10/28.
 */

public class ThumbView extends View{
    String TAG="ThumbView";
    private Bitmap mThumbBitmap;
    private Bitmap mNotThumbBitmap;
    private Bitmap mShinerBitmap;

    private int mThumbWidth;
    private int mThumbHegiht;
    private int mShinnerWidth;
    private int mShinnerHegiht;

    public final static int NOT_THUMB=0;
    public final static  int THUMB=1;
    private int status=NOT_THUMB;

    private final int THUMB_START_ANGLE =130;
    private final int THUMB_FINAL_ANGLE =360;
    private final int SWEEP_MAX =THUMB_FINAL_ANGLE-THUMB_START_ANGLE;
    private final int SWEEP_MIN =0;

    private int mSweepedAngle=0;

    private Path mShinnerPath;
    public ThumbView(Context context) {
        this(context,null);
    }

    public ThumbView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ThumbView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        mNotThumbBitmap= BitmapFactory.decodeResource(getResources(), R.drawable.ic_messages_like_unselected);
        mThumbBitmap= BitmapFactory.decodeResource(getResources(), R.drawable.ic_messages_like_selected);
        mShinerBitmap= BitmapFactory.decodeResource(getResources(), R.drawable.ic_messages_like_selected_shining);

        mThumbWidth=mThumbBitmap.getWidth();
        mThumbHegiht=mThumbBitmap.getHeight();
        mShinnerWidth=mShinerBitmap.getWidth();
        mShinnerHegiht=mShinerBitmap.getHeight();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(20,0);

        if(status==NOT_THUMB){
            canvas.save();
            mShinnerPath=new Path();
            RectF shinnerRectF = new RectF(0f, 0f, mShinnerWidth - dpToPx(5), mShinnerHegiht + dpToPx(10));
            mShinnerPath.addArc(shinnerRectF, THUMB_START_ANGLE,mSweepedAngle);
            canvas.clipPath(mShinnerPath);
            Paint paint = new Paint();
            int alpha = (int) ((mSweepedAngle * 1f) / SWEEP_MAX * 255);
            Log.i(TAG, "mSweepedAngle:"+mSweepedAngle);
            Log.i(TAG, "alpha:"+alpha);
            paint.setAlpha(Math.max(alpha,0));//alpha不小于0
            canvas.drawBitmap(mShinerBitmap, 0, 0, paint);
            canvas.restore();

            canvas.drawBitmap(mNotThumbBitmap, 0 - dpToPx(2), mShinerBitmap.getHeight() - dpToPx(8), new Paint());

            if(mSweepedAngle>SWEEP_MIN){
                mSweepedAngle-=10;
                invalidate();
            }

        }else if(status==THUMB) {
            canvas.save();
            mShinnerPath=new Path();
            RectF shinnerRectF = new RectF(0f, 0f, mShinnerWidth + dpToPx(2), mShinnerHegiht + dpToPx(10));

            mShinnerPath.addArc(shinnerRectF, THUMB_START_ANGLE,mSweepedAngle);
            canvas.clipPath(mShinnerPath);
            Paint paint = new Paint();
            int alpha = (int) ((mSweepedAngle * 1f) / SWEEP_MAX * 255);
            Log.i(TAG, "mSweepedAngle:"+mSweepedAngle);
            Log.i(TAG, "alpha:"+alpha);
            paint.setAlpha(Math.min(alpha,255));//alpha不能超过255
            canvas.drawBitmap(mShinerBitmap, 0, 0, paint);
            canvas.restore();

            canvas.drawBitmap(mThumbBitmap, 0 - dpToPx(2), mShinerBitmap.getHeight() - dpToPx(8), new Paint());

            if(mSweepedAngle<SWEEP_MAX) {
                mSweepedAngle+=10;
                invalidate();
            }


        }
        canvas.restore();
    }

    private int dpToPx(int dp){
       return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,getResources().getDisplayMetrics());
    }

    public void setStutas(int status){
        this.status=status;
        invalidate();
    }
}
