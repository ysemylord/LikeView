package com.xyb.myapplication.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created  on 2017/10/23.
 *
 * @author xyb
 */

public class LikeView extends View {
    private static final String TAG = "LikeView";
    private int likeNumer = 98;
    private TextPaint originTextPaint;
    private TextPaint inTextPaint;
    private TextPaint outTextPaint;
    private int status = 0;
    public final static int NORMAL = 0;
    public final static int ADD = 1;
    public final static int REDUCCE = 2;

    private int TEXT_INIT_Y = 50;

    int textY = TEXT_INIT_Y;

    public LikeView(Context context) {
        super(context);
    }

    public LikeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        originTextPaint = new TextPaint();
        originTextPaint.setTextSize(34f);

        inTextPaint = new TextPaint();
        inTextPaint.setTextSize(34f);

        outTextPaint = new TextPaint();
        outTextPaint.setTextSize(34f);
    }

    public LikeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        String likeNumStr = likeNumer + "";
        int startX = 0;
        if (status == NORMAL) {
            canvas.drawText(likeNumStr, startX, TEXT_INIT_Y, inTextPaint);
        } else if (status == ADD || status == REDUCCE) {

            int lastNumer;
            String lastNumStr;
            if (status == ADD) {
                lastNumer = likeNumer - 1;
            } else {
                lastNumer = likeNumer + 1;
            }
            lastNumStr = lastNumer + "";

            int changePosition = 0;//改变了的文字文字位子
            for (int i = 0; i < likeNumStr.length(); i++) {
                String t1 = likeNumStr.charAt(i) + "";
                String t2 = lastNumStr.charAt(i) + "";
                if (!t1.equals(t2)) {
                    changePosition = i;
                    break;
                }
            }


            //先画出没有改变的文字
            String notChangeText = likeNumStr.substring(0, changePosition) + "";
            canvas.drawText(notChangeText, startX, TEXT_INIT_Y, originTextPaint);
            Log.i(TAG, "onDraw:notChangeText " + notChangeText);
            startX = (int) (startX + inTextPaint.measureText(notChangeText, 0, notChangeText.length()));

            //有跳动效果的文字
            String outText = lastNumStr.substring(changePosition, lastNumStr.length()) + "";
            String intText = likeNumStr.substring(changePosition, likeNumStr.length()) + "";

            Rect rect = new Rect();
            inTextPaint.getTextBounds(likeNumStr, 0, likeNumStr.length(), rect);
            int textHeight = rect.height();

            if (status == ADD) {
                float alpha = 1 - (TEXT_INIT_Y - textY) * 1f / textHeight;//根据outText的偏移距离计算出outText文字颜色的透明度
                int outTextColor = Color.argb((int) (255 * alpha), 0, 0, 0);
                outTextPaint.setColor(outTextColor);

                canvas.drawText(outText, startX, textY, outTextPaint);
                canvas.drawText(intText, startX, textY + textHeight, inTextPaint);

                textY--;
                if (TEXT_INIT_Y - textY < textHeight) {
                    Log.i(TAG, "onDraw:inTextPaint intText " + intText);
                    Log.i(TAG, "onDraw:inTextPaint y " + (textY + textHeight));
                    invalidate();
                } else {
                    textY = TEXT_INIT_Y;
                }

                Log.i(TAG, "onDraw: " + outText);

            } else if (status == REDUCCE) {

                float alpha = 1 - (textY - TEXT_INIT_Y) * 1f / textHeight;//根据outText的偏移距离计算出outText文字颜色的透明度
                int outTextColor = Color.argb((int) (255 * alpha), 0, 0, 0);
                outTextPaint.setColor(outTextColor);

                canvas.drawText(outText, startX, textY, outTextPaint);
                canvas.drawText(intText, startX, textY - textHeight, inTextPaint);

                textY++;
                if (textY - TEXT_INIT_Y < textHeight) {
                    invalidate();
                } else {
                    textY = TEXT_INIT_Y;
                }
                Log.i(TAG, "onDraw: " + outText);

            }

        }
    }

    public void setStatus(int status) {
        this.status = status;
        if (status == ADD) {
            likeNumer++;
        } else if (status == REDUCCE) {
            likeNumer--;
        }
        invalidate();
    }


}
