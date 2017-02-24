package com.jyx.emi.view;

import android.util.Log;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jyx.emi.R;

/**
 * 自定义listview,支持下拉刷新，上拉加载
 */
public class RefreshListView extends ListView implements OnScrollListener{

	private View headerView;//headerView
	private View footerView;
	private ImageView iv_arrow;
	private ProgressBar pb_rotate;
	private TextView tv_state;

	private int headerViewHeight;//
	private int footerViewHeight;
	private int dowY;

	private final int PULL_REFRESH=0;//下拉刷新状态
	private final int RELEASE_REFRESH=1;//松开刷新状态
	private final int REFRESHING=2;//正在刷新状态
	private int currentState=PULL_REFRESH;

	private RotateAnimation upAnimation,downAnimation;

	private boolean isLoadingMore=false;//当前是否处于加载更多

	public RefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}

	public RefreshListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub

		init();
	}

	private void init()
	{
		setOnScrollListener(this);
		initHeaderView();
		initRotate();
		initFootView();
	}


	/**
	 * 初始化HeaderView
	 */
	private void initHeaderView() {

		headerView = View.inflate(getContext(), R.layout.layout_header, null);
		iv_arrow = (ImageView) headerView.findViewById(R.id.iv_arrow);
		pb_rotate=(ProgressBar) headerView.findViewById(R.id.pb_rotate);
		tv_state=(TextView) headerView.findViewById(R.id.tv_state);
		headerView.measure(0, 0);//主动通知去测量该view
		headerViewHeight = headerView.getMeasuredHeight();
		headerView.setPadding(0, -headerViewHeight, 0, 0);

		addHeaderView(headerView);


	}
	/**
	 * 初始化旋转动画
	 */
	private void initRotate() {
		upAnimation=new RotateAnimation(0, -180 ,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF,0.5f);
		upAnimation.setDuration(300);
		upAnimation.setFillAfter(true);

		downAnimation=new RotateAnimation(-180, 0 ,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF,0.5f);
		downAnimation.setDuration(300);
		downAnimation.setFillAfter(true);


	}

	private void initFootView() {
		footerView = View.inflate(getContext(), R.layout.layout_footer, null);
		footerView.measure(0, 0);//主动通知去测量该view
		footerViewHeight = footerView.getMeasuredHeight();
		footerView.setPadding(0, -footerViewHeight, 0, 0);
		addFooterView(footerView);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			dowY=(int) ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:

			if(currentState==REFRESHING)
			{
				break;
			}
			int deltaY=(int) ev.getY() -dowY;

			int paddingTop=-headerViewHeight+deltaY;

			if(paddingTop> -headerViewHeight && getFirstVisiblePosition()==0)
			{
				headerView.setPadding(0, paddingTop, 0, 0);

				if(paddingTop>=0 && currentState==PULL_REFRESH)
				{
					//松开刷新状态
					currentState=RELEASE_REFRESH;
					refreshHeaderView();

				}else if(paddingTop<0 && currentState==RELEASE_REFRESH){
					//进入下拉刷新状态
					currentState=PULL_REFRESH;
					refreshHeaderView();
				}
				return true;//拦截TouchMove，不让listview处理该move时间
			}
			break;

		case MotionEvent.ACTION_UP:

			if(currentState==PULL_REFRESH)
			{
				//隐藏headerView
				headerView.setPadding(0, -headerViewHeight, 0, 0);
			}else if(currentState==RELEASE_REFRESH)
			{
				//
				headerView.setPadding(0, 0, 0, 0);
				currentState=REFRESHING;
				refreshHeaderView();

				if(listener!=null)
				{
					listener.onPullRefresh();
				}

			}
			break;

		default:
			break;
		}
		return super.onTouchEvent(ev);
	}
	/**
	 * 根据currentState来更新headerView
	 */
	private void refreshHeaderView()
	{
		switch (currentState) {
		case PULL_REFRESH:
			tv_state.setText("下拉刷新...");
			iv_arrow.startAnimation(downAnimation);
			break;
		case RELEASE_REFRESH:
			tv_state.setText("放开刷新...");
			iv_arrow.startAnimation(upAnimation);
			break;
		case REFRESHING:
			iv_arrow.clearAnimation();//因为向上旋转动画没有执行完
			iv_arrow.setVisibility(View.INVISIBLE);
			pb_rotate.setVisibility(View.VISIBLE);
			tv_state.setText("正在载入...");
			break;

		default:
			break;
		}
	}
	/**
	 * 完成刷新操作，重置状态;在获取完数据并更新adapter之后，去在UI线程调用该方法
	 */
	public void completeRefresh()
	{
		if(isLoadingMore)
		{
			footerView.setPadding(0, -footerViewHeight, 0, 0);
			isLoadingMore=false;
		}
		else {
			//重置footerView状态
			headerView.setPadding(0, -headerViewHeight, 0, 0);
			currentState=PULL_REFRESH;
			pb_rotate.setVisibility(View.INVISIBLE);
			iv_arrow.setVisibility(View.VISIBLE);
			tv_state.setText("下拉刷新");
		}

	}

	private OnRefreshListener listener;

	public void setOnRefreshListener(OnRefreshListener listener){
		this.listener=listener;
	}

	public interface OnRefreshListener{
		void onPullRefresh();
		void onLoadingMore();
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		if(scrollState==OnScrollListener.SCROLL_STATE_IDLE
				&&getLastVisiblePosition()==(getCount()-1))
		{
			isLoadingMore=true;
			footerView.setPadding(0, 0, 0, 0);//显示出footerView
			setSelection(getCount());//让listView最后一条显示出来

 			if(listener!=null)
			{
				listener.onLoadingMore();
			}

		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub

	}

}
