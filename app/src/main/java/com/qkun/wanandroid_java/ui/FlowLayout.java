package com.qkun.wanandroid_java.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by QKun on 2018/11/1.
 */
public class FlowLayout extends ViewGroup {

    /**
     * 包含多少个行  每个行中有多少个view
     */
    private List<List<View>> mViewLinesList = new ArrayList<>();

    /**
     * 用来保存行高列表
     */
    private List<Integer> mHeightLists = new ArrayList<>();


    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
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
            int childViewWidth;
            int childViewHeight;
            //每行中有多少个view 列表
            List<View> viewList = new ArrayList<>();
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childView = getChildAt(i);
                //测量出子view 只有测量出getMeasureWidth 才会有值
                measureChild(childView, widthMeasureSpec, heightMeasureSpec);

                MarginLayoutParams layoutParams = (MarginLayoutParams) childView.getLayoutParams();
                childViewWidth = childView.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
                childViewHeight = childView.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;

                //判断是否换行 当前行宽添加新一个childview时发现大于的建议值
                if (lineWidth + childViewWidth > widthSize) {
                    /**1、记录当前行的信息 **/
                    //因为在循环中  获取最大的行宽作为最终测量的值
                    measuredWith = Math.max(lineWidth, measuredWith);
                    measuredHeight += lienHeight;

                    //当行中   将当行的viewList 添加到总mViewLineList,将行高添加到总行高集合中
                    mViewLinesList.add(viewList);
                    mHeightLists.add(lienHeight);

                    /**2、记录新一行的信息**/
                    lineWidth = childViewWidth;
                    lienHeight = childViewHeight;

                    //新一行 创建一个新的集合 将下一行 childview 添加进去
                    viewList = new ArrayList<>();
                    viewList.add(childView);

                } else {
                    //行宽 直接累加即可
                    lineWidth += childViewWidth;
                    //行高  最大的childView  与当前的行高比较 因为行高lienHeight 一直是获取的再次之前的最高childView的高度
                    lienHeight = Math.max(childViewHeight, lienHeight);

                    // 2、添加至当前行的viewList中
                    viewList.add(childView);
                }

                //如果正好是最后一行需要换行
                if (i == childCount - 1) {
                    measuredWith = Math.max(lineWidth, measuredHeight);
                    measuredHeight += lienHeight;

                    mViewLinesList.add(viewList);
                    mHeightLists.add(lienHeight);
                }

            }
        }
        //最终调用
        setMeasuredDimension(measuredWith, measuredHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // 确定 left top right bottom
        int left, top, right, bottom;
        //每次确定子view 当前left top
        int curLeft = 0;
        int curTop = 0;

        for (int i = 0; i < mViewLinesList.size(); i++) {
            //第一次循环 获取 行集合
            List<View> viewList = mViewLinesList.get(i);
            for (int j = 0; j < viewList.size(); j++) {
                //第二次 循环 获取 每行中子view
                View childView = viewList.get(j);
                MarginLayoutParams layoutParams = (MarginLayoutParams) childView.getLayoutParams();

                left = curLeft + layoutParams.leftMargin;
                top = curTop + layoutParams.topMargin;
                right = left + childView.getMeasuredWidth();
                bottom = top + childView.getMeasuredHeight();
                childView.layout(left, top, right, bottom);
                curLeft += childView.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
            }
            curLeft = 0;
            curTop += mHeightLists.get(i);
        }
        mViewLinesList.clear();
        mHeightLists.clear();
    }
}
