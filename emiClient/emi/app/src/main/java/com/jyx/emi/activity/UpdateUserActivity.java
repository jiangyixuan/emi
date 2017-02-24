package com.jyx.emi.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jyx.emi.R;
import com.jyx.emi.global.GlobalContants;
import com.jyx.emi.utils.HttpUtils;
import com.jyx.emi.utils.MyUtils;
import com.jyx.emi.utils.TransferDataUtils;

/**
 * Created by Administrator on 2016/6/13.
 */
public class UpdateUserActivity extends Activity implements View.OnClickListener{

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case 0://修改成功
                    Toast.makeText(UpdateUserActivity.this,R.string.update_succ,Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case -1://修改失败
                    Toast.makeText(UpdateUserActivity.this,R.string.update_error,Toast.LENGTH_SHORT).show();
                    et_content.setText("");
                    break;
                default:break;
            }
        }
    };

    private Intent intent;
    private ImageView iv_back;
    private TextView tv_title;
    private TextView tv_edit;
    private EditText et_content;

    private String updateType,value;
    private long clickTime= 0 ;//点击的修改时间


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);
        init();
        initData();

    }

    private void initData() {

        String typeTmp="";

        tv_edit.setText("保存");
        tv_edit.setTextColor(Color.WHITE);
        if(updateType.equals("name")){
            typeTmp="昵称";

        }else if(updateType.equals("pwd")){
            typeTmp="密码";
            et_content.setInputType(0x81);//设置为密码框
        }else if(updateType.equals("age")) {
            typeTmp="年龄";
            et_content.setInputType(0x02);
        } else if (updateType.equals("address")){
            typeTmp="地址";
        }
        tv_title.setText("更改" + typeTmp);
        et_content.setHint("输入" + typeTmp);

        if(!value.equals(getResources().getString(R.string.action_settings))){

            et_content.setText(value + "");
            et_content.setSelection(value.length());//editText光标移至末尾
        }

    }

    private void init() {

        intent = getIntent();

        updateType = intent.getStringExtra("updateType");
        value = intent.getStringExtra("value");

        iv_back= (ImageView) findViewById(R.id.iv_back);
        tv_title= (TextView) findViewById(R.id.tv_title);

        tv_edit= (TextView) findViewById(R.id.tv_edit);
        et_content= (EditText) findViewById(R.id.et_content);

        iv_back.setOnClickListener(this);
        tv_edit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_edit://保存

                if(System.currentTimeMillis()-clickTime>2000){//假如两次修改点击时间超过两秒
                    //关闭输入法
                    new MyUtils().closeInputMethod(UpdateUserActivity.this,et_content);

                    String contentStr = et_content.getText().toString();
                    if(TextUtils.isEmpty(contentStr)){
                        Toast.makeText(UpdateUserActivity.this,R.string.input_null,Toast.LENGTH_SHORT).show();
                    } else if(contentStr.equals(value))
                    {
                        Toast.makeText(UpdateUserActivity.this,R.string.not_update,Toast.LENGTH_SHORT).show();
                    } else {
                        String userId = TransferDataUtils.getString("userId",null,UpdateUserActivity.this);
                        updateUser(userId,updateType,contentStr);
                    }
                    clickTime = System.currentTimeMillis();
                }else{
                    Toast.makeText(UpdateUserActivity.this,R.string.operation_frequent,Toast.LENGTH_SHORT).show();
                }

                break;

        }
    }

    public void updateUser(String userId, final String updateType, final String value)
    {
        HttpUtils.doPostAsyn(GlobalContants.USER_UPDATE_URL, "userId="+userId+"&updateType=" + updateType+"&value="+value, new HttpUtils.RequestCallBack() {
            @Override
            public void onSuccess(String result) {

                if(result.equals("true")){
                    switch (updateType) {
                        case "name":
                            TransferDataUtils.setString("name",value,UpdateUserActivity.this);
                            break;
                        case "age":
                            TransferDataUtils.setString("age",value,UpdateUserActivity.this);
                            break;
                        case "address":
                            TransferDataUtils.setString("address",value,UpdateUserActivity.this);
                            break;
                        case "pwd":
                            TransferDataUtils.setString("pwd",value,UpdateUserActivity.this);
                            break;
                        default:break;
                    }

                    handler.sendEmptyMessage(0);
                }else {
                    handler.sendEmptyMessage(-1);
                }

            }
        });

    }


}
