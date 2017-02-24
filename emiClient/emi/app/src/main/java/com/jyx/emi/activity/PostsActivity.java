package com.jyx.emi.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jyx.emi.R;
import com.jyx.emi.bean.Posts;
import com.jyx.emi.bean.PostsAc;
import com.jyx.emi.bean.PostsAcInfo;
import com.jyx.emi.global.GlobalContants;
import com.jyx.emi.utils.HttpUtils;
import com.jyx.emi.utils.TransferDataUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PostsActivity extends Activity implements View.OnClickListener {

    private Posts posts;
    private RecyclerView recyclerView;
    private ArrayList<PostsAc> lsPostsac;
    private FrameLayout frameLayout;
    private String name;
    private FrameLayout frameHuifu;
    private TextView tvDialog;
    private EditText etDialog;

    private TextView tvUp;

    private boolean isHuifu;
    private List<PostsAcInfo> lsPOstsAcInfo;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 10:

                    initView();

                    break;
                case 20:
                    Toast.makeText(getApplicationContext(), "删除成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PostsActivity.this, CommActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case 21:
                    Toast.makeText(getApplicationContext(), "删除失败", Toast.LENGTH_SHORT).show();
                    break;
                case 30:
                    frameLayout.setVisibility(View.VISIBLE);
                    frameHuifu.setVisibility(View.GONE);
                    etDialog.setText("");
                    HttpUtils.doPostAsyn(GlobalContants.PostsAc_List, "action=select&sid=" + posts.getId() + "&name=" + posts.getName(), new HttpUtils.RequestCallBack() {
                        @Override
                        public void onSuccess(String result) {
                            lsPostsac = new Gson().fromJson(result, new TypeToken<List<PostsAc>>() {
                            }.getType());
                            handler.sendEmptyMessage(10);
                        }
                    });
                    break;
                case 31:
                    Toast.makeText(getApplicationContext(), "回复失败", Toast.LENGTH_SHORT).show();
                    break;
                case 40:
                    if(lsPostsac != null){

                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        RecyclerAcAdapter acAdapter = new RecyclerAcAdapter(getApplicationContext(), lsPostsac, posts,handler,lsPOstsAcInfo);
                        recyclerView.setAdapter(acAdapter);
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_posts);

        init();

        initData();

//        init();
//
//        initView();


    }


    private void initView() {

        if (name.equals(posts.getName())) {

            tvUp.setText("删除");
            tvUp.setVisibility(View.VISIBLE);
            tvUp.setOnClickListener(this);
        }




            if (lsPostsac != null) {
                for (int i = 0; i < lsPostsac.size(); i++) {
                    HttpUtils.doPostAsyn(GlobalContants.PostsAc_List, "action=selectIN&user=" + lsPostsac.get(i).getUser() + "&floor=" + lsPostsac.get(i).getFloor() + "&name=" + lsPostsac.get(i).getName() + "&sid=" + lsPostsac.get(i).getSid(), new HttpUtils.RequestCallBack() {
                        @Override
                        public void onSuccess(String result) {
                            lsPOstsAcInfo = new Gson().fromJson(result, new TypeToken<List<PostsAcInfo>>() {
                            }.getType());
                            handler.sendEmptyMessage(40);
                        }
                    });
                }
            }



    }

    private void init() {

        posts = new Posts();

        Intent intent = getIntent();
        posts = (Posts) intent.getSerializableExtra("posts");

        String defaultValue = getResources().getString(R.string.action_settings);
        name = TransferDataUtils.getString("name", defaultValue, this);

        isHuifu = false;


        //初始化控件
        frameHuifu = (FrameLayout) findViewById(R.id.frame_huifu);
        etDialog = (EditText) findViewById(R.id.et_dialog);
        tvDialog = (TextView) findViewById(R.id.tv_dialog);
        tvDialog.setOnClickListener(this);


        tvUp = (TextView) findViewById(R.id.tv_up);
        frameLayout = (FrameLayout) findViewById(R.id.rv_frame_add);
        frameLayout.setOnClickListener(this);

        ((TextView) findViewById(R.id.tv_title)).setText(posts.getTitle());
        recyclerView = (RecyclerView) findViewById(R.id.ac_posts);
    }


    private void initData() {


        /*StringBuilder stringBuilder = new StringBuilder();
        lsPostsac = new ArrayList<PostsAc>();
        try{
            AssetManager assetManager = this.getAssets();
            BufferedReader br = new BufferedReader(new InputStreamReader(assetManager.open("postsac.json")));
            String line;
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line);
            }

            String jsonStr= stringBuilder.toString();
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONArray jsonArray = jsonObject.getJSONArray("postsac");

            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jObject = (JSONObject) jsonArray.get(i);
                PostsAc postsac = new PostsAc();
                postsac.setUser(jObject.getString("user"));
                postsac.setBody(jObject.getString("body"));
                postsac.setFloor(jObject.getString("floor"));
                postsac.setTime(jObject.getString("time"));

                lsPostsac.add(postsac);
            }

        }catch (Exception e){
            e.printStackTrace();
        }*/
        HttpUtils.doPostAsyn(GlobalContants.PostsAc_List, "action=select&sid=" + posts.getId() + "&name=" + posts.getName(), new HttpUtils.RequestCallBack() {
            @Override
            public void onSuccess(String result) {
                lsPostsac = new Gson().fromJson(result, new TypeToken<List<PostsAc>>() {
                }.getType());
                handler.sendEmptyMessage(10);
            }
        });

    }

    private void show() {
        frameHuifu.setVisibility(View.VISIBLE);
        frameLayout.setVisibility(View.GONE);
    }

    /*public  boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = { 0, 0 };
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                *//*InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }*//*

                frameLayout.setVisibility(View.VISIBLE);
                frameHuifu.setVisibility(View.GONE);
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }*/

    // 点击空白区域 自动隐藏软键盘
    public boolean onTouchEvent(MotionEvent event) {
        if(null != this.getCurrentFocus()){
            /**
             * 点击空白位置 隐藏
             */
            frameLayout.setVisibility(View.VISIBLE);
            frameHuifu.setVisibility(View.GONE);
            return true;
        }
        return super .onTouchEvent(event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rv_frame_add:
                show();
                break;
            case R.id.tv_up:
                HttpUtils.doPostAsyn(GlobalContants.Posts_List, "action=delete&sid=" + posts.getId() + "&name=" + name + "&title=" + posts.getTitle(), new HttpUtils.RequestCallBack() {
                    @Override
                    public void onSuccess(String result) {
                        if (result.equals("true")) {
                            handler.sendEmptyMessage(20);
                        } else {
                            handler.sendEmptyMessage(21);
                        }
                    }
                });
                break;
            case R.id.tv_dialog:

//                action=insertAll&user=吃屎狗&body=哈哈哈&floor=第2楼&time=1111-11-11-11:11:11&name=世纪联华&sid=1
                SimpleDateFormat formatTime = new SimpleDateFormat("yy-MM-dd-HH:mm:ss");//yyyy-MM-dd
                String time = formatTime.format(new Date());
                String body = etDialog.getText().toString();
                if (body.equals("")){
                    Toast.makeText(getApplicationContext(), "无法发送空", Toast.LENGTH_SHORT).show();
                }else {
                    HttpUtils.doPostAsyn(GlobalContants.PostsAc_List,
                            "action=insertAll&user=" + name + "&body=" + body + "&floor=第" + (lsPostsac.size() + 1)+ "楼&time=" + time + "&name=" + posts.getName() + "&sid=" + posts.getId()
                            , new HttpUtils.RequestCallBack() {
                                @Override
                                public void onSuccess(String result) {
                                    if (result.equals("true")) {
                                        handler.sendEmptyMessage(30);
                                    } else {
                                        handler.sendEmptyMessage(31);
                                    }
                                }
                            });
                }
                break;
            default:
                break;
        }
    }


}

class RecyclerAcAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<PostsAc> lsPostsac;
    private Posts lsPosts;
    private Context context;
    private Handler handler;
    private List<PostsAcInfo> lsPOstsAcInfo;


    public enum ITEM_TYPE {
        ITEM_1, ITEM_2
    }

    public RecyclerAcAdapter(Context context, List<PostsAc> lsPostsac, Posts lsPosts,Handler handler,List<PostsAcInfo> lsPOstsAcInfo) {
        this.context = context;
        this.lsPostsac = lsPostsac;
        this.lsPosts = lsPosts;
        this.handler = handler;
        this.lsPOstsAcInfo = lsPOstsAcInfo;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == ITEM_TYPE.ITEM_1.ordinal()) {
            View view = LayoutInflater.from(context).inflate(R.layout.recycler_posts_activity, parent, false);
            return new PostsViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.recycler_posts_ac_la, parent, false);
            return new AcPostsViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof PostsViewHolder) {
            ((PostsViewHolder) holder).tvTitle.setText("  " + lsPosts.getTitle());
            ((PostsViewHolder) holder).tvBody.setText("  " + lsPosts.getBody());
            ((PostsViewHolder) holder).tvPrice.setText("  " + lsPosts.getPrice());
            ((PostsViewHolder) holder).tvAddress.setText("  " + lsPosts.getAddress());
            ((PostsViewHolder) holder).tvTime.setText("  " + lsPosts.getTime());
        } else if (holder instanceof AcPostsViewHolder) {
            ((AcPostsViewHolder) holder).tvUser.setText(lsPostsac.get(position - 1).getUser());
            ((AcPostsViewHolder) holder).tvBody.setText(lsPostsac.get(position - 1).getBody());
            ((AcPostsViewHolder) holder).tvFloor.setText(lsPostsac.get(position - 1).getFloor());
            ((AcPostsViewHolder) holder).tvTime.setText(lsPostsac.get(position - 1).getTime());
            ((AcPostsViewHolder) holder).tvHuifu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,position + "" ,Toast.LENGTH_SHORT).show();
                }
            });

            if(lsPOstsAcInfo != null){
                ((AcPostsViewHolder) holder).llRecycler.setVisibility(View.VISIBLE);
            }

        }
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? ITEM_TYPE.ITEM_1.ordinal() : ITEM_TYPE.ITEM_2.ordinal();
    }

    @Override
    public int getItemCount() {

        return lsPostsac != null ? lsPostsac.size() + 1 : 1;
    }

    public class PostsViewHolder extends RecyclerView.ViewHolder {

        public TextView tvTitle;
        public TextView tvBody;
        public TextView tvPrice;
        public TextView tvAddress;
        public TextView tvTime;


        public PostsViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.lltv_title);
            tvBody = (TextView) itemView.findViewById(R.id.tv_body);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_price);
            tvAddress = (TextView) itemView.findViewById(R.id.tv_address);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
        }
    }
//    action=selectIN&user=垃圾狗&floor=第1楼&name=垃圾狗&sid=27
    public class AcPostsViewHolder extends RecyclerView.ViewHolder {

        public TextView tvUser;
        public TextView tvBody;
        public TextView tvFloor;
        public TextView tvTime;
        public TextView tvHuifu;

        private LinearLayout llRecycler;
        private RecyclerView recyclerView;

        public AcPostsViewHolder(View itemView) {
            super(itemView);
            tvUser = (TextView) itemView.findViewById(R.id.actv_user);
            tvBody = (TextView) itemView.findViewById(R.id.actv_body);
            tvFloor = (TextView) itemView.findViewById(R.id.actv_floor);
            tvTime = (TextView) itemView.findViewById(R.id.actv_time);
            tvHuifu = (TextView) itemView.findViewById(R.id.rl_tv_huifu);

            llRecycler = (LinearLayout) itemView.findViewById(R.id.ll_huifu);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.ac_recycler_huifu);
        }
    }
}