package com.qkun.wanandroid_java.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.qkun.wanandroid_java.R;

/**
 * Created by QKun on 2018/11/15.
 */
public class MyGradientView extends View {

    private Bitmap mBitmap;
    private Paint mPaint;
    private int mWidth;
    private int mHeight;

    public MyGradientView(Context context) {
        super(context);
    }


    public MyGradientView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mBitmap = ((BitmapDrawable) getResources().getDrawable(R.mipmap.xyjy)).getBitmap();
        mPaint = new Paint();
        mWidth = mBitmap.getWidth();
        mHeight = mBitmap.getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        BitmapShader bitmapShader = new BitmapShader(mBitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        mPaint.setShader(bitmapShader);
        mPaint.setAntiAlias(true);
        //设置像素矩阵，来调整大小，为了解决宽高不一致的问题。
        float scale = Math.max(mWidth, mHeight) / Math.min(mWidth, mHeight);
        Matrix matrix = new Matrix();
        matrix.setScale(scale, scale);
        bitmapShader.setLocalMatrix(matrix);
//        canvas.drawCircle(mHeight / 2, mHeight / 2, mHeight / 2, mPaint);
//        canvas.drawOval(new RectF(0,0,mWidth,mHeight),mPaint);
        canvas.drawRect(new RectF(0, 0, 1000, 1600), mPaint);
    }
}
