package com.jyx.emi.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jyx.emi.R;
import com.jyx.emi.bean.User;
import com.jyx.emi.global.GlobalContants;
import com.jyx.emi.utils.HttpUtils;
import com.jyx.emi.utils.PreferencesUtils;
import com.jyx.emi.utils.TransferDataUtils;
import com.jyx.emi.view.ClearEditText;

/**
 * Created by Administrator on 2016/5/31.
 */
public class LoginActivity extends Activity implements View.OnClickListener {

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0://登陆成功
                    PreferencesUtils.setBoolean("isLogin",true,LoginActivity.this);
                    Toast.makeText(LoginActivity.this,R.string.login_succ,Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                    break;
                case 1://登陆失败
                    Toast.makeText(LoginActivity.this,R.string.login_error,Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    private ClearEditText et_tel;
    private ClearEditText et_pwd;
    private Button bt_login;
    private TextView tv_registered;
    private TextView tv_forgot_pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        listener();

    }

    private void listener() {
        bt_login.setOnClickListener(this);
        tv_registered.setOnClickListener(this);
        tv_forgot_pwd.setOnClickListener(this);
    }

    private void init() {
        // TODO Auto-generated method stub
        et_tel = (ClearEditText) findViewById(R.id.et_tel);
        et_pwd = (ClearEditText) findViewById(R.id.et_pwd);
        bt_login = (Button) findViewById(R.id.bt_login);
        tv_registered = (TextView) findViewById(R.id.tv_registered);
        tv_forgot_pwd = (TextView) findViewById(R.id.tv_forgot_pwd);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_login://登陆操作
                String telOrName = et_tel.getText().toString().trim();
                String pwd = et_pwd.getText().toString().trim();
                if (TextUtils.isEmpty(telOrName) || TextUtils.isEmpty(pwd)) {
                    Toast.makeText(getApplicationContext(), R.string.tel_pwd_null, Toast.LENGTH_SHORT).show();
                } else {
                    checkUser(telOrName, pwd);
                }
                break;
            case R.id.tv_registered://注册
                startActivity(new Intent(LoginActivity.this,RegistActivity.class));
                break;
            case R.id.tv_forgot_pwd://忘记密码

                break;

            default:
                break;
        }
    }

    /**
     * 登陆
     */
    private void checkUser(final String telOrName, String pwd) {

        HttpUtils.doPostAsyn(GlobalContants.LOGINCL_URL, "telOrName="+telOrName +"&"+"pwd="+pwd, new HttpUtils.RequestCallBack() {
            @Override
            public void onSuccess(String result) {

                if (result.equals("true")) {
                    //登陆成功
                    handler.sendEmptyMessage(0);
                    HttpUtils.doPostAsyn(GlobalContants.USERSELECT_URL, "tel=" + telOrName + "&flag=" + "select",
                            new HttpUtils.RequestCallBack() {
                                @Override
                                public void onSuccess(String result) {
                                    User user = new Gson().fromJson(result, User.class);
                                    if(user!=null){
                                        TransferDataUtils.setString("userId",user.getId()+"",LoginActivity.this);
                                        TransferDataUtils.setString("name",user.getName(),LoginActivity.this);
                                        TransferDataUtils.setString("pwd",user.getPwd(),LoginActivity.this);
                                        TransferDataUtils.setString("age",user.getAge()+"",LoginActivity.this);
                                        TransferDataUtils.setString("address",user.getAddress(),LoginActivity.this);
                                        TransferDataUtils.setString("headPortraitUrl",user.getHeadPortraitUrl(),LoginActivity.this);
                                    }else{
                                        Toast.makeText(LoginActivity.this,R.string.net_error,Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    //登陆失败
                    handler.sendEmptyMessage(1);
                }
            }
        });
    }

}
