package com.jyx.emi.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jyx.emi.R;
import com.jyx.emi.bean.User;
import com.jyx.emi.db.CollectHelper;
import com.jyx.emi.global.GlobalContants;
import com.jyx.emi.loader.ImageLoader;
import com.jyx.emi.utils.PreferencesUtils;
import com.jyx.emi.utils.TransferDataUtils;
import com.jyx.emi.view.SelectPicPopupWindow;

import org.w3c.dom.Text;

import java.io.File;

/**
 * Created by Administrator on 2016/5/31.
 */
public class PersonalActivity extends Activity implements View.OnClickListener {

    /* 头像文件 */
    private static final String IMAGE_FILE_NAME = "temp_head_image.jpg";
    //自定义的弹出框类
    private SelectPicPopupWindow menuWindow;
    private ImageView iv_back;
    private TextView tv_title;
    private LinearLayout ll_head_img;
    private ImageView iv_head;
    private LinearLayout ll_name;
    private LinearLayout ll_pwd;
    private LinearLayout ll_age;
    private LinearLayout ll_address;
    private TextView tv_logout;

    private ImageLoader imageLoader;

    private String name,pwd,age,address,headPortraitUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        init();
        listener();
//        initView();

    }

    @Override
    protected void onStart() {
        super.onStart();
        initView();//返回时更新数据
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(getApplication(), "取消", Toast.LENGTH_LONG).show();
            return;
        }
        switch (requestCode) {

            case 1:
                cropRawPhoto(data.getData());
                break;
            case 2:
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    File tempFile = new File(
                            Environment.getExternalStorageDirectory(),
                            IMAGE_FILE_NAME);
                    cropRawPhoto(Uri.fromFile(tempFile));
                } else {
                    Toast.makeText(getApplication(), "没有SDCard!", Toast.LENGTH_LONG)
                            .show();
                }
                break;
            case 3:
                if (data != null) {
                    //提取保存裁剪之后的图片数据，并设置头像部分的View
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        Bitmap photo = extras.getParcelable("data");
                        iv_head.setImageBitmap(photo);
                        //保存至服务器


                    }
                }
                break;
            default:break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initData() {
        String defaultValue=getResources().getString(R.string.action_settings);

        name = TransferDataUtils.getString("name", defaultValue, this);
        pwd = TransferDataUtils.getString("pwd", defaultValue, this);
        age = TransferDataUtils.getString("age", defaultValue, this);
        if(age.equals("0")) {
            age=defaultValue;
        }
        address = TransferDataUtils.getString("address", defaultValue, this);
        headPortraitUrl = TransferDataUtils.getString("headPortraitUrl", "TransferDataUtils.getString(\"headPortraitUrl\", \"http://ww2.sinaimg.cn/crop.0.0.1080.1080.1024/d773ebfajw8eum57eobkwj20u00u075w.jpg", this);

    }

    private void initView() {
        initData();

        tv_title.setText("个人中心");

        ((TextView) ll_name.getChildAt(1)).setText(name);
        ((TextView) ll_age.getChildAt(1)).setText(age);
        ((TextView) ll_address.getChildAt(1)).setText(address);
        imageLoader.bindBitmap(headPortraitUrl, iv_head, 50, 50);
    }

    private void listener() {

        iv_back.setOnClickListener(this);
        ll_head_img.setOnClickListener(this);
        ll_name.setOnClickListener(this);
        ll_pwd.setOnClickListener(this);
        ll_age.setOnClickListener(this);
        ll_address.setOnClickListener(this);

        tv_logout.setOnClickListener(this);
    }

    private void init() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        ll_head_img = (LinearLayout) findViewById(R.id.ll_head_img);
        iv_head = (ImageView) findViewById(R.id.iv_head);
        ll_name = (LinearLayout) findViewById(R.id.ll_name);
        ll_pwd = (LinearLayout) findViewById(R.id.ll_pwd);
        ll_age = (LinearLayout) findViewById(R.id.ll_age);
        ll_address = (LinearLayout) findViewById(R.id.ll_address);
        tv_logout = (TextView) findViewById(R.id.tv_logout);

        imageLoader = ImageLoader.build(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(PersonalActivity.this,UpdateUserActivity.class);
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.ll_head_img:
                //实例化SelectPicPopupWindow
                menuWindow = new SelectPicPopupWindow(PersonalActivity.this, itemsOnClick);
                //显示窗口
                menuWindow.showAtLocation(PersonalActivity.this.findViewById(R.id.tv_logout),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
                break;
            case R.id.ll_name:
                intent.putExtra("updateType", "name");
                intent.putExtra("value",name);
                startActivity(intent);
                break;
            case R.id.ll_pwd:
                intent.putExtra("updateType","pwd");
                intent.putExtra("value",pwd);
                startActivity(intent);
                break;
            case R.id.ll_age:
                intent.putExtra("updateType","age");
                intent.putExtra("value",age);
                startActivity(intent);
                break;
            case R.id.ll_address:
                intent.putExtra("updateType","address");
                intent.putExtra("value",address);
                startActivity(intent);
                break;
            case R.id.tv_logout://注销登录

                createDialog().show();
                //清空保存配置数据
                TransferDataUtils.remove("name",this);
                TransferDataUtils.remove("pwd",this);
                TransferDataUtils.remove("age",this);
                TransferDataUtils.remove("address",this);
                TransferDataUtils.remove("headPortraitUrl",this);
                TransferDataUtils.remove("userId",this);
                break;
            default:
                break;
        }
    }
    /**
     * 裁剪原始的图片
     */
    public void cropRawPhoto(Uri uri) {


        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }

    /**
     * 确认取消对话框
     */
    //定义对话框
    private AlertDialog createDialog() {
        AlertDialog.Builder ad = new AlertDialog.Builder(PersonalActivity.this);
//        ad.setTitle("你确定要退出吗");
        ad.setMessage("你确定要退出吗");
        ad.setNegativeButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                PreferencesUtils.setBoolean("isLogin", false, PersonalActivity.this);
                finish();
                startActivity(new Intent(PersonalActivity.this, MainActivity.class));
                Toast.makeText(PersonalActivity.this, R.string.logout_succ, Toast.LENGTH_SHORT).show();
            }
        });
        ad.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return ad.create();
    }

    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {

        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
                case R.id.btn_take_photo://拍照
                    Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                            .fromFile(new File(Environment
                                    .getExternalStorageDirectory(), IMAGE_FILE_NAME)));

                    startActivityForResult(intentFromCapture, 1);
                    break;
                case R.id.btn_pick_photo://从相册选择
                    Intent intentFromGallery = new Intent(Intent.ACTION_PICK, null);
                    intentFromGallery.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(intentFromGallery, 2);
                    break;
                default:
                    break;
            }

        }

    };

}
