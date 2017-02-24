package com.jyx.emi.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jyx.emi.Adapter.RecyclerPostsAdapter;
import com.jyx.emi.R;
import com.jyx.emi.activity.PostsActivity;
import com.jyx.emi.activity.PostsInfoActivity;
import com.jyx.emi.bean.Posts;
import com.jyx.emi.global.GlobalContants;
import com.jyx.emi.utils.HttpUtils;

import java.util.ArrayList;
import java.util.List;

public class PostsFragment extends Fragment implements View.OnClickListener{

    private View view;
    private RecyclerView recyclerView;
    private ArrayList<Posts> lsPosts;
    private Context context;
    private FrameLayout frameLayout;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    init();
                    break;
                default:
                    break;
            }
        }
    };

    public PostsFragment(Context context){
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_posts,null);

        initData();

//        init();



        return view;
    }

    private void initData() {
       /* StringBuilder stringBuilder = new StringBuilder();
        lsPosts = new ArrayList<Posts>();
        try{
            AssetManager assetManager = context.getAssets();
            BufferedReader br = new BufferedReader(new InputStreamReader(assetManager.open("posts.json")));
            String line;
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line);
            }

            String jsonStr= stringBuilder.toString();
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONArray jsonArray = jsonObject.getJSONArray("posts");

            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jObject = (JSONObject) jsonArray.get(i);
                Posts posts = new Posts();
                posts.setUser(jObject.getString("user"));
                posts.setTitle(jObject.getString("title"));
                posts.setBody(jObject.getString("body"));
                posts.setTime(jObject.getString("time"));
                posts.setPrice(jObject.getString("price"));
                posts.setAddress(jObject.getString("address"));

                lsPosts.add(posts);
            }

        }catch (Exception e){
            e.printStackTrace();
        }*/

//        action=insert&name=世纪联华&title=鸿星尔克&body=鸿星尔克专场&time=2016-11-09-16:52:23&price=3939&address=江西师范大学

        HttpUtils.doPostAsyn(GlobalContants.Posts_List, "action=select",new HttpUtils.RequestCallBack() {
            @Override
            public void onSuccess(String result) {
                lsPosts = new Gson().fromJson(result,new TypeToken<List<Posts>>(){}.getType());
                handler.sendEmptyMessage(0);
            }
        });

    }

    private void init() {

        frameLayout = (FrameLayout) view.findViewById(R.id.rv_frame_add);
        frameLayout.setOnClickListener(this);

        recyclerView = (RecyclerView) view.findViewById(R.id.rv_posts);

        if (lsPosts != null){

            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            RecyclerPostsAdapter rpAdapter = new RecyclerPostsAdapter(context, lsPosts);
            recyclerView.setAdapter(rpAdapter);
            rpAdapter.setOnItemClickListener(new RecyclerPostsAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Posts posts1 = lsPosts.get(position);

                    Intent intent = new Intent();
                    intent.setClass(context, PostsActivity.class);
                    intent.putExtra("posts", posts1);
                    startActivity(intent);
                }
            });
            //设置Item增加、移除动画
//            recyclerView.setItemAnimator(new DefaultItemAnimator());

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rv_frame_add:
                Intent intent = new Intent();
                intent.setClass(context, PostsInfoActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}



