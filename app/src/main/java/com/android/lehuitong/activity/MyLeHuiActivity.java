package com.android.lehuitong.activity;

import org.json.JSONException;
import org.json.JSONObject;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.android.lehuitong.LehuitongApp;
import com.android.lehuitong.model.GetRankProgressUtil;
import com.android.lehuitong.model.UserInfoModel;
import com.android.lehuitong.protocol.ProtocolConst;
import com.android.lehuitong.protocol.SESSION;
import com.android.lehuitong.view.CircularImage;
import com.external.androidquery.callback.AjaxStatus;
import com.funmi.lehuitong.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import android.R.integer;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

/** 我的乐汇页面 */
public class MyLeHuiActivity extends BaseActivity implements OnClickListener, BusinessResponse {

	private LinearLayout myself_data;
	private RelativeLayout passwordmanager;
	private RelativeLayout my_order;
	private RelativeLayout addressmanager;
	private RelativeLayout my_coupons;
	private RelativeLayout my_integral;
	private RelativeLayout about_us;
	private RelativeLayout feedback;
	private RelativeLayout my_cart;
	private Button log_out_button;
	private ImageView title_back;
	private TextView title_text;
	private Intent intent;
	private TextView phone_number;
	private TextView rank_name;
	private TextView rank_points;
	private CircularImage cover_user_photo;
	private ImageView rankImage;
	private ProgressBar rankProgressBar;

	private DisplayImageOptions option;
	private ImageLoader loader;

	private int minPoints;
	private int maxPoints;
	private int rankPoints;

	private UserInfoModel myLeHuiModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mylehui);
		init();
		LehuitongApp.getInstance().addActivity(this); 
	}

	@Override
	protected void onResume() {
		super.onResume();
		myLeHuiModel.mylehui();
	}

	private void init() {

		loader = ImageLoader.getInstance();
		option = new DisplayImageOptions.Builder().showImageForEmptyUri(R.drawable.touxiang).showImageOnFail(R.drawable.touxiang).resetViewBeforeLoading(false).delayBeforeLoading(300)
				.cacheOnDisc(true).cacheInMemory(true).bitmapConfig(Bitmap.Config.RGB_565).build();

		cover_user_photo = (CircularImage) findViewById(R.id.my_photo);
//		cover_user_photo.setImageResource(R.drawable.avatar_houxin);
		my_order = (RelativeLayout) findViewById(R.id.my_order);
		my_cart = (RelativeLayout) findViewById(R.id.my_cart);
		addressmanager = (RelativeLayout) findViewById(R.id.addressmanager);
		myself_data = (LinearLayout) findViewById(R.id.myself_data);
		my_integral = (RelativeLayout) findViewById(R.id.my_integral);
		passwordmanager = (RelativeLayout) findViewById(R.id.passwordmanager);
		about_us = (RelativeLayout) findViewById(R.id.about_us);
		feedback = (RelativeLayout) findViewById(R.id.feedback);
		my_coupons = (RelativeLayout) findViewById(R.id.my_coupons);
		log_out_button = (Button) findViewById(R.id.log_out_button);
		title_back = (ImageView) findViewById(R.id.title_back);
		title_text = (TextView) findViewById(R.id.title_text);
		rank_name = (TextView) findViewById(R.id.rank_name);
		title_text.setText("会员");
		phone_number = (TextView) findViewById(R.id.phone_number);
		rank_points = (TextView) findViewById(R.id.rank_points);
		rankProgressBar = (ProgressBar) findViewById(R.id.progressBar1);
		rankImage=(ImageView) findViewById(R.id.rank_image);
		myLeHuiModel = new UserInfoModel(this);
		myLeHuiModel.addResponseListener(this);

		setOnClickListener();
	}

	private void setOnClickListener() {

		log_out_button.setOnClickListener(this);
		my_order.setOnClickListener(this);
		my_cart.setOnClickListener(this);
		title_back.setOnClickListener(this);
		my_coupons.setOnClickListener(this);
		feedback.setOnClickListener(this);
		addressmanager.setOnClickListener(this);
		myself_data.setOnClickListener(this);
		my_integral.setOnClickListener(this);
		passwordmanager.setOnClickListener(this);
		about_us.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.title_back:
			finish();

			break;
		case R.id.my_coupons:
			intent = new Intent(MyLeHuiActivity.this, MyCouponsActivity.class);
			startActivity(intent);
			break;
		case R.id.feedback:
			intent = new Intent(MyLeHuiActivity.this, FeedBackActivity.class);
			startActivity(intent);
			break;
		case R.id.about_us:
			intent = new Intent(MyLeHuiActivity.this, AboutUsActivity.class);
			startActivity(intent);
			break;
		case R.id.passwordmanager:
			intent = new Intent(MyLeHuiActivity.this, PasswordManagerActivity.class);
			startActivity(intent);
			break;
		case R.id.my_integral:
			intent = new Intent(MyLeHuiActivity.this, MyIntegralActivity.class);
			startActivity(intent);
			break;
		case R.id.myself_data:
			intent = new Intent(MyLeHuiActivity.this, MyselfDataActivity.class);
			startActivity(intent);
			break;
		case R.id.addressmanager:
			intent = new Intent(MyLeHuiActivity.this, AddressManagerActivity.class);
			startActivity(intent);
			break;
		case R.id.my_cart:
			intent = new Intent(MyLeHuiActivity.this, MyCartActivity.class);
			startActivity(intent);
			break;
		case R.id.my_order:
			intent = new Intent(MyLeHuiActivity.this, MyOrderActivity.class);
			startActivity(intent);
			break;
		case R.id.log_out_button:
			intent = new Intent(MyLeHuiActivity.this, LoginActivity.class);
			Bundle bundle=new Bundle();
			bundle.putString("agin_login", "1");
			intent.putExtras(bundle);
			SESSION session = SESSION.getInstance();
			session.sid="";
			session.uid="";
			startActivity(intent);
			LehuitongApp.getInstance().exit();//清除activity栈
			break;
		default:
			break;
		}
	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {
		if (url.endsWith(ProtocolConst.GET_USERINFO)) {
			getRankImage(Integer.parseInt(myLeHuiModel.user.rank_name.substring(2)));
			String name = null;
			rank_name.setText(myLeHuiModel.user.rank_name);
			if (myLeHuiModel.user.nickname.length()==4) {
				 name=myLeHuiModel.user.nickname.substring(0, 4);
			}
			 name=myLeHuiModel.user.nickname;
			if (name==null) {
				SharedPreferences shared = getSharedPreferences("user", MODE_PRIVATE);
				phone_number.setText(shared.getString("name", ""));
			}
			else if (name.equals("null")) {
				SharedPreferences shared = getSharedPreferences("user", MODE_PRIVATE);
				phone_number.setText(shared.getString("name", ""));
			} else {
				phone_number.setText(myLeHuiModel.user.nickname);
			}
			rankPoints = Integer.valueOf(myLeHuiModel.user.rank_points).intValue();
			minPoints = Integer.valueOf(myLeHuiModel.user.min_points).intValue();
			maxPoints = Integer.valueOf(myLeHuiModel.user.max_points).intValue();
			rank_points.setText((maxPoints - rankPoints) + "");
			GetRankProgressUtil.GetRankProgress(Integer.valueOf(myLeHuiModel.user.rank_name.substring(2))
					.intValue(),rankPoints, maxPoints, rankProgressBar);
			loader.displayImage(myLeHuiModel.user.head_img, cover_user_photo, option, new SimpleImageLoadingListener() {
				@Override
				public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
					((CircularImage) view).setImageBitmap(loadedImage);

				}
			});
		}

	}

	private void getRankImage(int Rank) {
		if (Rank>=9) {
			
			rankImage.setImageResource(R.drawable.zuanshi_yellow);
		}else if(Rank==8){
			rankImage.setImageResource(R.drawable.bojin);
		}else if(Rank==7){
			rankImage.setImageResource(R.drawable.huangjin);
		}else if(Rank>=4&&Rank<7){
			rankImage.setImageResource(R.drawable.baiyin);
		}else if(Rank>=1&&Rank<4){
			rankImage.setImageResource(R.drawable.qingtong);
		}
		
	}
}
