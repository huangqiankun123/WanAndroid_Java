package com.qkun.wanandroid_java.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by QKun on 2018/11/1.
 */
public class FlowLayout extends ViewGroup {

    public FlowLayout(Context context) {
        super(context);
    }


    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    /**
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取测量模式和大小
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        //最终需要测量的ViewGroup的宽高
        int measuredWith = 0;
        int measuredHeight = 0;
        //行 宽高
        int lineWidth = 0;
        int lienHeight = 0;

        //根据模式来判断大小
        if (widthMode == MeasureSpec.EXACTLY && heightMode == MeasureSpec.EXACTLY) {
            measuredWith = widthSize;
            measuredHeight = heightSize;
        } else {
            int childViewWidth = 0;
            int childViewHeight = 0;
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childView = getChildAt(i);
                //测量出子view 只有测量出getMeasureWidth 才会有值
                measureChild(childView, widthMeasureSpec, heightMeasureSpec);

                MarginLayoutParams layoutParams = (MarginLayoutParams) getLayoutParams();
                childViewWidth = childView.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
                childViewHeight = childView.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;

                //判断是否换行

                //不换行
                //行宽 直接累加即可
                lineWidth += childViewWidth;
                //行高  最大的child
                lienHeight = Math.max(childViewHeight,lienHeight);
            }
        }

        //最终调用
        setMeasuredDimension(measuredWith, measuredHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
