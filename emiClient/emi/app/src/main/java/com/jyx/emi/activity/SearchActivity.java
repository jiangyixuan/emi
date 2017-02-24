package com.jyx.emi.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jyx.emi.Adapter.SearchCommAdapter;
import com.jyx.emi.Adapter.SearchHistoryAdapter;
import com.jyx.emi.R;
import com.jyx.emi.bean.SearchComm;
import com.jyx.emi.db.SearchHistoryHelper;
import com.jyx.emi.global.GlobalContants;
import com.jyx.emi.utils.HttpUtils;
import com.jyx.emi.utils.MyUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/6/17.
 */
public class SearchActivity extends Activity implements View.OnClickListener {

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    mProgressDialog.dismiss();
                    if(searchCommList!=null) {
                        if(searchCommList.size()==0) {
                            Toast.makeText(SearchActivity.this,R.string.not_serach_result,Toast.LENGTH_SHORT).show();
                            return;
                        }
                        rv_store.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
                        rv_store.setAdapter(searchCommAdapter);
                        recyclerViewClick();
                    }
                    else{
                        Toast.makeText(SearchActivity.this,R.string.net_error,Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:break;
            }
        }
    };

    private EditText et_search;
    private TextView tv_search;
    private RecyclerView rv_store;
    private ProgressDialog mProgressDialog;
    private List<SearchComm> searchCommList;
    private SearchCommAdapter searchCommAdapter;
    private ListView lv_search_history;
    private TextView tv_clean_history;
    private SearchHistoryAdapter historyAdapter;
    private List<String> historyStrings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        init();
        listener();
        initData();
        initView();
    }

    private void initView() {
        if(historyStrings.size()==0) {
            findViewById(R.id.ll_history).setVisibility(View.GONE);
        }else {
            lv_search_history.setAdapter(historyAdapter);
        }
    }

    private void initData() {

        historyStrings = new SearchHistoryHelper(SearchActivity.this).getSearchHistory();
        historyAdapter=new SearchHistoryAdapter(SearchActivity.this,historyStrings);

    }

    private void init() {
        // TODO Auto-generated method stub
        ((TextView) findViewById(R.id.tv_title)).setText("搜索");
        et_search= (EditText) findViewById(R.id.et_search);
        tv_search= (TextView) findViewById(R.id.tv_search);
        rv_store= (RecyclerView) findViewById(R.id.rv_store);
        lv_search_history= (ListView) findViewById(R.id.lv_search_history);
        tv_clean_history= (TextView) findViewById(R.id.tv_clean_history);

    }

    private void listener()
    {
        findViewById(R.id.iv_back).setOnClickListener(this);
        tv_search.setOnClickListener(this);
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    //执行搜索
                    search(et_search.getText().toString().trim());
                    return true;
                }
                return false;
            }
        });
        et_search.addTextChangedListener(new SearchEditChangedListener());
        tv_clean_history.setOnClickListener(this);
        lv_search_history.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                search(historyStrings.get(position));
            }
        });

    }

    private void recyclerViewClick()
    {
        searchCommAdapter.setOnItemClickListener(new SearchCommAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {

                Intent intent = new Intent(SearchActivity.this, CommDetailActivity.class);

                intent.putExtra("commId", searchCommList.get(postion).getCommId() + "");
                intent.putExtra("commName", searchCommList.get(postion).getCommName());
                intent.putExtra("nowPrice", searchCommList.get(postion).getNowPrice());
                intent.putExtra("oldPrice",  searchCommList.get(postion).getNowPrice());
                startActivity(intent);

            }
        });
    }

    private void search(String keyword)
    {
        new SearchHistoryHelper(SearchActivity.this).insertSearch(keyword);
        findViewById(R.id.ll_history).setVisibility(View.GONE);
        //隐藏软键盘
        new MyUtils().closeInputMethod(SearchActivity.this, et_search);
        mProgressDialog = ProgressDialog.show(this, null, "正在加载...");
        tv_search.setVisibility(View.GONE);
        HttpUtils.doPostAsyn(GlobalContants.SERCH_COMM_URL, "keyword="+keyword, new HttpUtils.RequestCallBack() {
            @Override
            public void onSuccess(String result) {
                searchCommList=new Gson().fromJson(result,new TypeToken<List<SearchComm>>(){}.getType());
                searchCommAdapter=new SearchCommAdapter(SearchActivity.this,searchCommList);
                handler.sendEmptyMessage(0);
            }

        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back://返回
                finish();
                break;
            case R.id.tv_search://搜索
                search(et_search.getText().toString().trim());
                break;
            case R.id.tv_clean_history://清空搜索历史
                findViewById(R.id.ll_history).setVisibility(View.GONE);
                new SearchHistoryHelper(SearchActivity.this).deleteAllSearch();
                break;
            default:
                break;
        }
    }

    class SearchEditChangedListener implements TextWatcher{
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if(TextUtils.isEmpty(s.toString())){
                tv_search.setVisibility(View.GONE);
            }
            else{
                tv_search.setVisibility(View.VISIBLE);
            }
        }
    }

}
