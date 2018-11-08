package com.qkun.wanandroid_java.ui.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.qkun.wanandroid_java.R;
import com.qkun.wanandroid_java.base.BaseActivity;
import com.qkun.wanandroid_java.bean.LoginBean;
import com.qkun.wanandroid_java.constant.Constant;
import com.qkun.wanandroid_java.http.cookies.CookiesManager;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Cookie;

/**
 * Created by QKun on 2018/11/5.
 */
public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {
    @BindView(R.id.et_username)
    EditText mEtUsername;
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.btn_login)
    TextView mBtnLogin;
    @BindView(R.id.tv_sign_up)
    TextView mTvSignUp;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {

    }

    @Override
    public void LoginSuccess(LoginBean bean) {
        ToastUtils.showLong("登录成功");
        SPUtils.getInstance(Constant.SHARED_NAME).put(Constant.LOGIN_KEY, true);
        SPUtils.getInstance(Constant.SHARED_NAME).put(Constant.USERNAME_KEY, bean.getUsername());
        SPUtils.getInstance(Constant.SHARED_NAME).put(Constant.PASSWORD_KEY, bean.getPassword());
        ActivityUtils.finishActivity(LoginActivity.class);

    }

    @OnClick({R.id.btn_login})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_login:
                String username = mEtUsername.getText().toString();
                String password = mEtPassword.getText().toString();
                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    ToastUtils.showShort(R.string.the_username_or_password_can_not_be_empty);
                    return;
                }
                mPresenter.Login(username, password);
                break;
            default:
                break;
        }
    }

    @Override
    public void showFailed(String message) {
        ToastUtils.showShort(message);
    }
}
