package com.qkun.wanandroid_java.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.util.ActivityUtils;
import com.qkun.wanandroid_java.R;
import com.qkun.wanandroid_java.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DefinedActivity extends BaseActivity {

    @BindView(R.id.btn_01)
    Button btn01;
    @BindView(R.id.btn_02)
    Button mBtn02;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_defined;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initView() {

    }

    @OnClick({R.id.btn_02})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_02:
                ActivityUtils.startActivity(ShaderActivity.class);
                break;
        }
    }
}
