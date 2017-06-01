package com.dreoapps.screenlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by avlad on 01.06.2017.
 */

public class ScreenLayout extends FrameLayout {
    private FrameLayout eventContainer;
    private FrameLayout contentContainer;

    private int errorViewLayoutId;
    private int progressViewLayoutId;

    private View errorView;
    private View progressView;



    public ScreenLayout(Context context) {
        super(context);
        setupView(context);
    }

    public ScreenLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context,attrs);
        setupView(context);
    }


    private void setupView(Context context){

        eventContainer = new FrameLayout(context);
        contentContainer = new FrameLayout(context);

        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);


        this.addView(contentContainer,0,layoutParams);
        this.addView(eventContainer,1,layoutParams);


        if(errorViewLayoutId != -1) {
            errorView = LayoutInflater
                    .from(context)
                    .inflate(errorViewLayoutId, null);
        }

        if(progressViewLayoutId != -1) {
            progressView = LayoutInflater
                    .from(context)
                    .inflate(progressViewLayoutId, null);
        }
    }


    private void initView(Context context, AttributeSet attrs){
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ScreenLayout,
                0, 0);

        try {
            errorViewLayoutId = a.getResourceId(R.styleable.ScreenLayout_errorViewLayout,-1);
            progressViewLayoutId = a.getResourceId(R.styleable.ScreenLayout_progressViewLayout,-1);
        } finally {
            a.recycle();
        }
    }


    public void showProgressEvent(){
        if(progressView != null){
            clearEvents();
            addViewToEventContainer(progressView);
        }
    }

    public void showErrorEvent(){
        if(errorView != null){
            clearEvents();
            addViewToEventContainer(errorView);
        }
    }

    public void clearEvents(){
        if(eventContainer != null){
            eventContainer.removeAllViews();
        }
    }

    private void addViewToEventContainer(View view){
        if(eventContainer != null){
            eventContainer.addView(view);
        }
    }


    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if(child != contentContainer && child != eventContainer){
            contentContainer.addView(child);
        }else{
            super.addView(child, index, params);
        }
    }

    public View getErrorView() {
        return errorView;
    }

    public View getProgressView() {
        return progressView;
    }
}
