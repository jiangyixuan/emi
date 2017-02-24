package com.jyx.emi.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jyx.emi.R;
import com.jyx.emi.global.GlobalContants;
import com.jyx.emi.utils.HttpUtils;
import com.jyx.emi.utils.TransferDataUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PostsInfoActivity extends Activity implements View.OnClickListener {

    private TextView tvUp;
    private EditText etTitle;
    private EditText etPrice;
    private EditText etBody;
    private TextView tvLocation;

    private String title;
    private String price;
    private String body;
    private String time;
    private String loacation;
    private String name;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:

                    Toast.makeText(getApplicationContext(), "发布成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PostsInfoActivity.this,CommActivity.class);
                    startActivity(intent);
                    finish();

                    break;
                case 1:

                    Toast.makeText(getApplicationContext(), "发布失败", Toast.LENGTH_SHORT).show();

                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts_info);

        init();
    }

    private void init() {
        ((TextView)findViewById(R.id.tv_title)).setText("发布消息");

        tvUp = (TextView) findViewById(R.id.tv_up);
        etTitle = (EditText) findViewById(R.id.ll_et_title);
        etPrice = (EditText) findViewById(R.id.ll_et_price);
        etBody = (EditText) findViewById(R.id.et_body);
        tvLocation = (TextView) findViewById(R.id.tv_city);

        tvUp.setOnClickListener(this);
        tvUp.setVisibility(View.VISIBLE);
        tvLocation.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_up:
                title = etTitle.getText().toString();
                price = etPrice.getText().toString();
                body = etBody.getText().toString();
                if(tvLocation.getText().toString().equals("点击定位...")){
                    loacation = "江西师范大学先骕楼";
                }else {
                    loacation = tvLocation.getText().toString();
                }
                SimpleDateFormat formatTime = new SimpleDateFormat("yy-MM-dd-HH:mm:ss");//yyyy-MM-dd
                time = formatTime.format(new Date());

                String defaultValue = getResources().getString(R.string.action_settings);
                name = TransferDataUtils.getString("name", defaultValue, this);


                if(title.equals("") || price.equals("") || body.equals("")){
                    Toast.makeText(this,"填写完整数据",Toast.LENGTH_SHORT).show();
                    return;
                }else {

                    HttpUtils.doPostAsyn(GlobalContants.Posts_List, "action=insert&name=" + name + "&title=" + title + "&body=" + body + "&time=" + time + "&price=" + price + "&address=" + loacation, new HttpUtils.RequestCallBack() {
                        @Override
                        public void onSuccess(String result) {
                            if (result.equals("true")) {
                                handler.sendEmptyMessage(0);
                            } else {
                                handler.sendEmptyMessage(1);
                            }
                        }
                    });
                }
                break;
            case R.id.tv_city:
                tvLocation.setText("江西师范大学先骕楼");
                break;
            default:
                break;
        }
    }
}
