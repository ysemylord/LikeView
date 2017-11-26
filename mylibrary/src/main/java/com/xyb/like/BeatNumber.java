package com.xyb.like;

import android.content.Context;
import android.content.res.TypedArray;
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
 * 跳动的数字
 *
 * @author xyb
 */

public class BeatNumber extends View {
    private static final String TAG = "LikeView";
    private int likeNumer = 98;
    private TextPaint originTextPaint;
    private TextPaint inTextPaint;
    private TextPaint outTextPaint;
    private int status = 0;
    public final static int NORMAL = 0;
    public final static int ADD = 1;
    public final static int REDUCCE = 2;

    private int TEXT_INIT_Y;//数字绘制时的基线位置（http://hencoder.com/ui-1-3/）
    private int textYOffset=0;//数字变化时的基线位置

    private float textSize = 20f;

    public BeatNumber(Context context) {
        this(context, null);
    }

    public BeatNumber(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BeatNumber,0,0);
        textSize=typedArray.getDimension(R.styleable.BeatNumber_number_text_size,38f);
        typedArray.recycle();
        originTextPaint = new TextPaint();
        originTextPaint.setTextSize(textSize);
        originTextPaint.setAntiAlias(true);

        inTextPaint = new TextPaint();
        inTextPaint.setTextSize(textSize);
        inTextPaint.setAntiAlias(true);


        outTextPaint = new TextPaint();
        outTextPaint.setTextSize(textSize);
        outTextPaint.setAntiAlias(true);
    }

    public BeatNumber(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        String numString = likeNumer + "";
        Rect bound = new Rect();
        inTextPaint.getTextBounds(numString, 0, numString.length(), bound);
        //BeatNumber的宽度为文字的宽度（包含左右两边的空隙），高度为文字高度（不包含上下的空隙）+文字的起始坐标TEXT_INIT_Y

        int viewHeight=bound.height()*3;
        setMeasuredDimension((int) inTextPaint.measureText(numString),viewHeight );
        TEXT_INIT_Y=bound.height()*2;
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
                float degress = Math.abs(textYOffset) /(textHeight*1f);//偏移的完成度
                int alphaInt = (int) ((1-degress) * 255);//完成度越高，透明度越高，透明值越低
                outTextPaint.setAlpha(alphaInt);
                Log.i(TAG, "onDraw: alpah "+alphaInt);

                /**
                 *
                 textYOffset--;
                 if (TEXT_INIT_Y - textYOffset < textHeight) {
                 Log.i(TAG, "onDraw:inTextPaint intText "+ intText);
                 Log.i(TAG, "onDraw:inTextPaint y "+ (textYOffset + textHeight));
                 canvas.drawText(outText, startX, textYOffset, outTextPaint);
                 canvas.drawText(intText, startX, textYOffset + textHeight, inTextPaint);
                 invalidate();
                 } else {
                 textYOffset = TEXT_INIT_Y;
                 canvas.drawText(intText, startX, textYOffset , inTextPaint);
                 }

                 */
                //这里有个小技巧，先drawText,再改变文字文字坐标，这样到达临界时的代码和没有到达是是一样的，不然就要像上面的注释；里的代码一样了。
                canvas.drawText(outText, startX, TEXT_INIT_Y+textYOffset, outTextPaint);
                canvas.drawText(intText, startX, TEXT_INIT_Y+ textHeight+textYOffset, inTextPaint);

                textYOffset--;
                if (Math.abs(textYOffset) <= textHeight) {
                    invalidate();
                } else {
                    textYOffset = 0;
                }

                Log.i(TAG, "onDraw: " + outText);

            } else if (status == REDUCCE) {

                float degress = Math.abs(textYOffset) /(textHeight*1f);//偏移的完成度
                int alphaInt = (int) ((1-degress) * 255);//完成度越高，透明度越高，透明值越小
                outTextPaint.setAlpha(alphaInt);

                canvas.drawText(outText, startX, TEXT_INIT_Y+textYOffset, outTextPaint);
                canvas.drawText(intText, startX, TEXT_INIT_Y-textHeight+textYOffset, inTextPaint);

                textYOffset++;
                if (Math.abs(textYOffset) <= textHeight) {
                    invalidate();
                } else {
                    textYOffset = 0;
                }
                Log.i(TAG, "onDraw: " + outText);

            }


        }
    }

    public void setStatus(int status) {
        String lastNumStr = likeNumer + "";
        this.status = status;
        if (status == ADD) {
            likeNumer++;
        } else if (status == REDUCCE) {
            likeNumer--;
            if (likeNumer < 0) {//不能小于0
                return;
            }
        }
        String likeNumStr = likeNumer + "";
        if (lastNumStr.length() != likeNumStr.length()) {
            requestLayout();//重新测量
        }
        invalidate();
    }

    public void setLikeNumer(int likeNumer) {
        this.likeNumer = likeNumer;
        setStatus(NORMAL);
    }
}
