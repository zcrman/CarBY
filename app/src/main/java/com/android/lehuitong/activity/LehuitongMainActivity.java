package com.android.lehuitong.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.BeeFramework.view.MyListView;
import com.BeeFramework.view.WebImageView;
import com.android.lehuitong.LehuitongApp;
import com.android.lehuitong.adapter.HomeDataAdapter;
import com.android.lehuitong.adapter.HomePicPagerAdapter;
import com.android.lehuitong.model.HomeModel;
import com.android.lehuitong.protocol.PLAYER;
import com.android.lehuitong.protocol.ProtocolConst;
import com.external.androidquery.callback.AjaxStatus;
import com.external.maxwin.view.XListView;
import com.external.viewpagerindicator.PageIndicator;
import com.funmi.lehuitong.R;
import com.umeng.update.UmengUpdateAgent;

import  cn.bmob.v3.Bmob;

public class LehuitongMainActivity extends BaseActivity implements BusinessResponse, XListView.IXListViewListener {

	private LinearLayout header;
	private MyListView homeList;
	private TextView topTitle;
	private ImageView topBack;
	private PageIndicator mIndicator;
	private View mTouchTarget;
	private ViewPager bannerViewPager;
	private HomePicPagerAdapter bannerPageAdapter;
	private List<View> bannerListView;
	private HomeModel dataModel;
	private SharedPreferences shared;
	private SharedPreferences.Editor editor;
	private HomeDataAdapter homeDataAdapter;
	private final int AUTO_MSG = 1;
	private final int HANDLE_MSG = AUTO_MSG + 1;
	private static final int PHOTO_CHANGE_TIME = 5000;// 定时变量
	private int index = 0;
	private long mExitTime;
	private int oldCurrent;
	int i = 0;
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case AUTO_MSG:
				if (i == bannerListView.size()) {
					i = 0;
				}
				Log.i("33333", ""+i);
				bannerViewPager.setCurrentItem(i++);// 收到消息后设置当前要显示的图片
				mHandler.sendEmptyMessageDelayed(AUTO_MSG, PHOTO_CHANGE_TIME);
				break;
			case HANDLE_MSG:
				mHandler.removeMessages(AUTO_MSG);
				// mHandler.sendEmptyMessage(AUTO_MSG);
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lehuitong_main);
		LehuitongApp.getInstance().addActivity(this);
		/**友盟更新代码*/
		UmengUpdateAgent.update(this);
		UmengUpdateAgent.setUpdateOnlyWifi(false);

		Bmob.initialize(this,"fffedad91c674667a835eabb758ba1d8");//注册bmob

		initView();
	}

	private void initView() {

		topTitle = (TextView) findViewById(R.id.title_text);
		topBack = (ImageView) findViewById(R.id.title_back);
		homeList = (MyListView) findViewById(R.id.home_listview);
		topTitle.setText("乐汇通");
		topBack.setVisibility(View.GONE);
		header = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.home_header, null);
		bannerViewPager = (ViewPager) header.findViewById(R.id.banner_viewpager);
		mIndicator = (PageIndicator) header.findViewById(R.id.indicator);
		homeList.bannerView = header;
		homeList.setPullLoadEnable(false);
		homeList.addHeaderView(header);
		homeList.setPullRefreshEnable(false);
		homeList.setAdapter(null);
		homeList.setPullLoadEnable(false);
		homeList.setPullRefreshEnable(true);
		homeList.setXListViewListener(this, 0);
		homeList.setRefreshTime();

		if (dataModel == null) {
			dataModel = new HomeModel(this);
			dataModel.addResponseListener(this);
			dataModel.fetchHotSelling();

		}
		LayoutParams params1 = bannerViewPager.getLayoutParams();
		params1.width = getDisplayMetricsWidth();
		params1.height = (int) (params1.width * 1.0 / 2.0);

		bannerViewPager.setLayoutParams(params1);

		bannerListView = new ArrayList<View>();

		bannerPageAdapter = new HomePicPagerAdapter(bannerListView);

		bannerViewPager.setAdapter(bannerPageAdapter);
		bannerViewPager.setCurrentItem(0);

//		bannerViewPager.setOnPageChangeListener(new OnPageChangeListener() {
//			private int mPreviousState = ViewPager.SCROLL_STATE_IDLE;
//
//			@Override
//			public void onPageScrolled(int i, float v, int i2) {
//			}
//
//			@Override
//			public void onPageSelected(int i) {
//
//			}
//
//			@Override
//			public void onPageScrollStateChanged(int state) {
//				if (mPreviousState == ViewPager.SCROLL_STATE_IDLE) {
//					if (state == ViewPager.SCROLL_STATE_DRAGGING) {
//						mTouchTarget = bannerViewPager;
//					}
//				} else {
//					if (state == ViewPager.SCROLL_STATE_IDLE || state == ViewPager.SCROLL_STATE_SETTLING) {
//						mTouchTarget = null;
//					}
//				}
//				mPreviousState = state;
//			}
//		});
		mIndicator.setViewPager(bannerViewPager);
		homeSetAdapter();
		mHandler.sendEmptyMessageDelayed(AUTO_MSG, PHOTO_CHANGE_TIME);
	}
	public void homeSetAdapter() {
		if (dataModel.homeDataCache() != null) {
			if (null == homeDataAdapter) {
				homeDataAdapter = new HomeDataAdapter(this, dataModel);
			}
			homeList.setAdapter(homeDataAdapter);
			addBannerView();
		}

	}

	// 获取屏幕宽度
	public int getDisplayMetricsWidth() {
		int i = getWindowManager().getDefaultDisplay().getWidth();
		int j = getWindowManager().getDefaultDisplay().getHeight();
		return Math.min(i, j);
	}

	public void addBannerView() {
		bannerListView.clear();
		for (int i = 0; i < dataModel.playersList.size(); i++) {
			PLAYER player = dataModel.playersList.get(i);
			WebImageView viewOne = (WebImageView) LayoutInflater.from(this).inflate(R.layout.banner_imageview, null);

			shared = getSharedPreferences("userInfo", 0);
			editor = shared.edit();
			String imageType = shared.getString("imageType", "mind");

			if (imageType.equals("high")) {
				viewOne.setImageWithURL(this, player.photo.thumb, R.drawable.default_image);
			} else if (imageType.equals("low")) {
				viewOne.setImageWithURL(this, player.photo.small, R.drawable.default_image);
			} else {
				String netType = shared.getString("netType", "wifi");
				if (netType.equals("wifi")) {
					viewOne.setImageWithURL(this, player.photo.thumb, R.drawable.default_image);
				} else {
					viewOne.setImageWithURL(this, player.photo.small, R.drawable.default_image);
				}
			}

			try {
				viewOne.setTag(player.toJson().toString());
			} catch (JSONException e) {

			}

			bannerListView.add(viewOne);

			viewOne.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					String playerJSONString = (String) v.getTag();

					try {
						JSONObject jsonObject = new JSONObject(playerJSONString);
						PLAYER player1 = PLAYER.fromJson(jsonObject);
						if (null == player1.action) {
							if (null != player1.url) {
								Intent intent = new Intent(LehuitongMainActivity.this, BannerWebActivity.class);
								intent.putExtra("url", player1.url);
								startActivity(intent);
								overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
							}
						} else {
							if (player1.action.equals("goods")) {
								Intent intent = new Intent(LehuitongMainActivity.this, MyPurchaseDetailActivity.class);
								intent.putExtra("id", player1.action_id);
								startActivity(intent);
								overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
							} else if (player1.action.equals("category")) {
								Intent intent = new Intent(LehuitongMainActivity.this, MyPurchaseDetailActivity.class);
								intent.putExtra("id", player1.action_id);
								startActivity(intent);
								overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
							} else if (null != player1.url) {
								Intent intent = new Intent(LehuitongMainActivity.this, BannerWebActivity.class);
								intent.putExtra("url", player1.url);
								startActivity(intent);
								overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
							}
						}

					} catch (JSONException e) {

					}

				}
			});

		}

		mIndicator.notifyDataSetChanged();
		bannerPageAdapter.mListViews = bannerListView;
		bannerViewPager.setAdapter(bannerPageAdapter);

	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {
		if (url.endsWith(ProtocolConst.HOME_PAGE_LOAD_DATA)) {
			homeList.stopRefresh();
			homeList.setRefreshTime();
			if (null == homeDataAdapter) {
				homeDataAdapter = new HomeDataAdapter(this, dataModel);
			}
			homeList.setAdapter(homeDataAdapter);
			addBannerView();
		}
	}

	@Override
	public void onRefresh(int id) {
		dataModel.fetchHotSelling();
	}

	@Override
	public void onLoadMore(int id) {
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 2) {

		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if ((System.currentTimeMillis() - mExitTime) > 2000) {
				Object mHelperUtils;
				Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
				mExitTime = System.currentTimeMillis();

			} else {
				LehuitongApp.getInstance().exit();
				finish();
				System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
