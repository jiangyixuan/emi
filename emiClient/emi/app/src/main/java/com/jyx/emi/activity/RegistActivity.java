package com.jyx.emi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jyx.emi.R;
import com.jyx.emi.global.GlobalContants;
import com.jyx.emi.utils.HttpUtils;
import com.jyx.emi.view.ClearEditText;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by Administrator on 2016/6/7.
 */
public class RegistActivity extends Activity implements View.OnClickListener {

    private static final int REGIST_SUCC=10;
    private static final int REGIST_ERROR=11;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case REGIST_SUCC://注册成功);
                    Toast.makeText(RegistActivity.this,R.string.regist_succ,Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case REGIST_ERROR://注册失败
                    Toast.makeText(RegistActivity.this,R.string.tel_is_regist,Toast.LENGTH_SHORT).show();
                    et_pwd_one.setText("");
                    et_pwd_two.setText("");
                    et_tel.setText("");
                    et_valicode.setText("");
                    break;
                case SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE://提交验证码成功

                    regist(telStr, pwd_two);//注册
                    break;
                case SMSSDK.EVENT_GET_VERIFICATION_CODE://获取验证码成功
                    Toast.makeText(RegistActivity.this, R.string.sms_send_succ, Toast.LENGTH_SHORT).show();
                    break;
                default:break;
            }
        }
    };
    private ClearEditText et_tel, et_valicode;
    private TextView tv_ovtaincode;
    private Button bt_regist;
    private String telStr, valStr, pwdStr, pwd_two;
    private ClearEditText et_pwd_one, et_pwd_two;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);
        init();
        SMSSDK.initSDK(this, GlobalContants.SMSAPPKEY, GlobalContants.SMSAPPSECRET);

        SMSSDK.registerEventHandler(new SMSListener()); //注册短信回调
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }

    private void init() {
        et_tel = (ClearEditText) findViewById(R.id.et_tel);
        et_valicode = (ClearEditText) findViewById(R.id.et_valicode);
        tv_ovtaincode = (TextView) findViewById(R.id.tv_ovtaincode);
        bt_regist = (Button) findViewById(R.id.bt_regist);
        et_pwd_one = (ClearEditText) findViewById(R.id.et_pwd_one);
        et_pwd_two = (ClearEditText) findViewById(R.id.et_pwd_two);

        tv_ovtaincode.setOnClickListener(this);
        bt_regist.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_ovtaincode:
                telStr = et_tel.getText().toString().trim();
                //获取验证码
                if (!TextUtils.isEmpty(telStr)) {
                    pwdStr = et_pwd_one.getText().toString().trim();
                    SMSSDK.getVerificationCode("86", telStr);
                } else {
                    Toast.makeText(getApplicationContext(), R.string.tel_not_null, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bt_regist:

                telStr = et_tel.getText().toString().trim();
                valStr = et_valicode.getText().toString().trim();
                pwdStr = et_pwd_one.getText().toString().trim();
                pwd_two = et_pwd_two.getText().toString().trim();
                if (TextUtils.isEmpty(telStr)) {
                    Toast.makeText(getApplicationContext(), R.string.tel_not_null, Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(valStr)) {
                    Toast.makeText(getApplicationContext(), R.string.codes_not_null, Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(pwdStr) || !pwdStr.equals(pwd_two)) {
                    Toast.makeText(getApplicationContext(), R.string.pwd_input_error, Toast.LENGTH_SHORT).show();
                } else {
                    SMSSDK.submitVerificationCode("86", telStr, valStr);
                }
                break;

        }
    }

    /**
     * 注册账号，将手机号和密码保存至服务器
     */
    private void regist(String tel, String pwd) {
        HttpUtils.doPostAsyn(GlobalContants.USER_INSERT_URL, "tel=" + tel + "&pwd=" + pwd, new HttpUtils.RequestCallBack() {
            @Override
            public void onSuccess(String result) {

                Log.i("asdads",result);

                if (result.equals("true")) {
                    handler.sendEmptyMessage(REGIST_SUCC);
                } else {
                    handler.sendEmptyMessage(REGIST_ERROR);
                }

            }

        });
    }

    class SMSListener extends EventHandler
    {
        @Override
        public void afterEvent(int event, int result, Object data) {
            if (result == SMSSDK.RESULT_COMPLETE) {
                //回调完成
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    //提交验证码成功
                    handler.sendEmptyMessage(SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE);
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    //获取验证码成功
                    handler.sendEmptyMessage(SMSSDK.EVENT_GET_VERIFICATION_CODE);
                }
            } else {
                ((Throwable) data).printStackTrace();
            }
        }
    }

}
