package com.android.lehuitong.activity;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.model.BusinessResponse;
import com.android.lehuitong.LehuitongApp;
import com.android.lehuitong.fragment.PurchaseFragment;
import com.android.lehuitong.model.ShoppingCartModel;
import com.android.lehuitong.protocol.ProtocolConst;
import com.android.lehuitong.protocol.SESSION;
import com.android.lehuitong.tab.SyncHorizontalScrollView;
import com.external.androidquery.callback.AjaxStatus;
import com.funmi.lehuitong.R;

public class MyPurchaseActivity extends FragmentActivity implements
		BusinessResponse {

	public static final String ARGUMENTS_NAME = "arg";
	private RelativeLayout rl_nav;
	private SyncHorizontalScrollView mHsv;
	private RadioGroup rg_nav_content;
	private ImageView iv_nav_indicator;
	private ImageView iv_nav_left;
	private ImageView iv_nav_right;
	private ViewPager mViewPager;
	SESSION session = SESSION.getInstance();
	private TextView title_text;
	private ImageView title_search;
	private ImageView title_back;
	private int indicatorWidth;
	public static ArrayList<String> tabTitleList = new ArrayList<String>();
	public static ArrayList<String> catIdList = new ArrayList<String>();
	private LayoutInflater mInflater;
	private TabFragmentPagerAdapter mAdapter;
	private int currentIndicatorLeft = 0;

	private TextView cartNum;
	private ShoppingCartModel cartModel;
	private SharedPreferences shared;
	private SharedPreferences.Editor editor;
	private int shoppingCartNumber = 0;
	public static MyPurchaseActivity myActivity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_purchase);
		LehuitongApp.getInstance().addActivity(this);
		findViewById();
		initView();
		setListener();
		myActivity = this;
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (session.sid!=null&&session.sid.length()>0&&session.uid!=null&&session.uid.length()>0) {
			cartModel.cartList();
			cartNum.setVisibility(View.GONE);
		}
	}

	private void setListener() {
		title_text.setText("购物");
		title_back.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				finish();
			}
		});
		title_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent2 = new Intent(MyPurchaseActivity.this,
						MyCartActivity.class);
				startActivity(intent2);

			}
		});
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {

				if (rg_nav_content != null
						&& rg_nav_content.getChildCount() > position) {
					((RadioButton) rg_nav_content.getChildAt(position))
							.performClick();
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});

		rg_nav_content
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {

						if (rg_nav_content.getChildAt(checkedId) != null) {

							TranslateAnimation animation = new TranslateAnimation(
									currentIndicatorLeft,
									((RadioButton) rg_nav_content
											.getChildAt(checkedId)).getLeft(),
									0f, 0f);
							animation.setInterpolator(new LinearInterpolator());
							animation.setDuration(100);
							animation.setFillAfter(true);

							iv_nav_indicator.startAnimation(animation);

							mViewPager.setCurrentItem(checkedId);

							currentIndicatorLeft = ((RadioButton) rg_nav_content
									.getChildAt(checkedId)).getLeft();

							mHsv.smoothScrollTo(
									(checkedId > 1 ? ((RadioButton) rg_nav_content
											.getChildAt(checkedId)).getLeft()
											: 0)
											- ((RadioButton) rg_nav_content
													.getChildAt(2)).getLeft(),
									0);
						}
					}
				});
	}

	private void initView() {
		tabTitleList = getIntent().getStringArrayListExtra("catNameList");
		catIdList = getIntent().getStringArrayListExtra("catIdList");

		title_back = (ImageView) findViewById(R.id.title_back);
		title_search = (ImageView) findViewById(R.id.title_search);
		title_text = (TextView) findViewById(R.id.title_text);

		cartNum = (TextView) findViewById(R.id.shoppingcart_num);

		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);

		indicatorWidth = dm.widthPixels / 4;

		LayoutParams cursor_Params = iv_nav_indicator.getLayoutParams();
		cursor_Params.width = indicatorWidth;
		iv_nav_indicator.setLayoutParams(cursor_Params);

		mHsv.setSomeParam(rl_nav, iv_nav_left, iv_nav_right, this);

		mInflater = (LayoutInflater) this
				.getSystemService(LAYOUT_INFLATER_SERVICE);

		initNavigationHSV();
		mAdapter = new TabFragmentPagerAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(mAdapter);

		cartModel = new ShoppingCartModel(this);
		cartModel.addResponseListener(this);
		shared = getSharedPreferences("shoppingCartNum", MODE_PRIVATE);
		editor = shared.edit();

	}

	private void initNavigationHSV() {

		rg_nav_content.removeAllViews();

		for (int i = 0; i < tabTitleList.size(); i++) {

			RadioButton rb = (RadioButton) mInflater.inflate(
					R.layout.nav_radiogroup_item, null);
			rb.setId(i);
			rb.setText(tabTitleList.get(i));
			rb.setLayoutParams(new LayoutParams(indicatorWidth,
					LayoutParams.MATCH_PARENT));

			rg_nav_content.addView(rb);
		}
	}

	private void findViewById() {

		rl_nav = (RelativeLayout) findViewById(R.id.rl_nav);

		mHsv = (SyncHorizontalScrollView) findViewById(R.id.mHsv);

		rg_nav_content = (RadioGroup) findViewById(R.id.rg_nav_content);

		iv_nav_indicator = (ImageView) findViewById(R.id.iv_nav_indicator);
		iv_nav_left = (ImageView) findViewById(R.id.iv_nav_left);
		iv_nav_right = (ImageView) findViewById(R.id.iv_nav_right);

		mViewPager = (ViewPager) findViewById(R.id.mViewPager);
		title_text = (TextView) findViewById(R.id.title_text);
		title_search = (ImageView) findViewById(R.id.title_search);
		title_back = (ImageView) findViewById(R.id.title_back);

	}

	public static class TabFragmentPagerAdapter extends FragmentPagerAdapter {

		public TabFragmentPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int arg0) {
			Fragment ft = null;
			switch (arg0) {

			default:
				ft = new PurchaseFragment();
				Bundle args = new Bundle();
				args.putString(ARGUMENTS_NAME, catIdList.get(arg0));
				ft.setArguments(args);
				break;
			}
			return ft;
		}

		@Override
		public int getCount() {

			return tabTitleList.size();
		}

	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		if (url.endsWith(ProtocolConst.CART_LIST)) {
			if (cartModel.goods_list.size() > 0) {
				shoppingCartNumber = 0;
				for (int i = 0; i < cartModel.goods_list.size(); i++) {
					shoppingCartNumber += Integer.valueOf(
							cartModel.goods_list.get(i).goods_number)
							.intValue();
				}
				cartNum.setVisibility(View.VISIBLE);
				cartNum.setText(shoppingCartNumber + "");
				editor.putInt("num", shoppingCartNumber);
				editor.commit();
			} else {
				cartNum.setVisibility(View.GONE);
				editor.putInt("num", 0);
				editor.commit();
			}
		}

	}

}
