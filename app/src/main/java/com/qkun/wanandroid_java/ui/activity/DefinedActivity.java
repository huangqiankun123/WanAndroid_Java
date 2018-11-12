package com.qkun.wanandroid_java.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.qkun.wanandroid_java.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DefinedActivity extends AppCompatActivity {

    @BindView(R.id.btn_01)
    Button btn01;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_defined);
        ButterKnife.bind(this);
    }
}
