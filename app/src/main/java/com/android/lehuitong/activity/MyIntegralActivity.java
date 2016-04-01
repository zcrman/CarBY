package com.android.lehuitong.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.android.lehuitong.adapter.MyIntegralAdapter;
import com.android.lehuitong.model.ArticleModel;
import com.android.lehuitong.model.GetRankProgressUtil;
import com.android.lehuitong.model.UserInfoModel;
import com.android.lehuitong.protocol.ARTICLE;
import com.android.lehuitong.protocol.ProtocolConst;
import com.external.androidquery.callback.AjaxStatus;
import com.funmi.lehuitong.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MyIntegralActivity extends BaseActivity implements
		OnClickListener, BusinessResponse {
	private ImageView title_back;
	private TextView title_text;
	private TextView rank_points;
	private TextView integral_level;
	private TextView hundreds;//百
	private TextView decade;//十
	private TextView units;//个
	private TextView rank_points_hundred_thousand;//十万
	private TextView rank_points_ten_hundreds;//万
	private TextView rank_points_thousand;//千
	Intent intent;
	private UserInfoModel userInfoModel;
	private ArticleModel articleModel;
	private ProgressBar rankProgressBar;
	private int minPoints;
	private int maxPoints;
	private int rankPoints;

	private ListView listView;
	private MyIntegralAdapter adapter;
	private List<ARTICLE> list = new ArrayList<ARTICLE>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_integral);
		init();
	}

	public void init() {
		rankProgressBar = (ProgressBar) findViewById(R.id.rankProgressBar);

		adapter = new MyIntegralAdapter(this);
		listView = (ListView) findViewById(R.id.item_integral);
		title_back = (ImageView) findViewById(R.id.title_back);

		title_text = (TextView) findViewById(R.id.title_text);
		rank_points = (TextView) findViewById(R.id.rank_points);
		integral_level = (TextView) findViewById(R.id.integral_level);
		hundreds = (TextView) findViewById(R.id.rank_points_hundreds);
		decade = (TextView) findViewById(R.id.rank_points_decade);
		units = (TextView) findViewById(R.id.rank_points_units);
		rank_points_thousand = (TextView) findViewById(R.id.rank_points_thousand);
		rank_points_ten_hundreds = (TextView) findViewById(R.id.rank_points_ten_hundreds);
		rank_points_hundred_thousand = (TextView) findViewById(R.id.rank_points_hundred_thousand);
		title_text.setText("我的积分");
		userInfoModel = new UserInfoModel(this);
		userInfoModel.addResponseListener(this);
		userInfoModel.mylehui();
		articleModel = new ArticleModel(this);
		articleModel.addResponseListener(this);
		articleModel.getarticle();
		setOnClickListener();

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(MyIntegralActivity.this,
						HelpActivity.class);
				intent.putExtra("content", list.get(arg2).content);
				intent.putExtra("title", list.get(arg2).title);
				startActivity(intent);
			}
		});
	}

	public void setOnClickListener() {
		title_back.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_back:
			finish();
			break;
		default:
			break;
		}

	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		if (url.endsWith(ProtocolConst.GET_USERINFO)) {

			rankPoints = Integer.valueOf(userInfoModel.user.rank_points)
					.intValue();
			minPoints = Integer.valueOf(userInfoModel.user.min_points)
					.intValue();
			maxPoints = Integer.valueOf(userInfoModel.user.max_points)
					.intValue();
			rank_points.setText((maxPoints - rankPoints) + "");
			rankProgressBar.setMax(maxPoints-minPoints);
			rankProgressBar.setProgress(rankPoints-minPoints);
//			GetRankProgressUtil.GetRankProgress(Integer.valueOf(userInfoModel.user.rank_name.substring(2))
//					.intValue(),rankPoints, maxPoints, rankProgressBar);
			integral_level.setText(userInfoModel.user.rank_name);
			String integral = userInfoModel.user.rank_points;
			if (integral.length() == 1) {
				units.setText(userInfoModel.user.rank_points);// 个位
				hundreds.setText("0");// 百位
				decade.setText("0");// 十位
			} else if (integral.length() == 2) {
				units.setText(userInfoModel.user.rank_points.substring(2, 3));
				decade.setText(userInfoModel.user.rank_points.substring(1, 2));
				hundreds.setText("0");
			} else if (integral.length() == 3) {
				hundreds.setText(userInfoModel.user.rank_points.substring(0, 1));
				decade.setText(userInfoModel.user.rank_points.substring(1, 2));
				units.setText(userInfoModel.user.rank_points.substring(2, 3));
			}else if (integral.length() == 4) {
				hundreds.setText(userInfoModel.user.rank_points.substring(1, 2));
				decade.setText(userInfoModel.user.rank_points.substring(2, 3));
				units.setText(userInfoModel.user.rank_points.substring(3, 4));
				rank_points_thousand.setText(userInfoModel.user.rank_points.substring(0, 1));
				
			}else if (integral.length() == 5) {
				hundreds.setText(userInfoModel.user.rank_points.substring(2, 3));
				decade.setText(userInfoModel.user.rank_points.substring(3, 4));
				units.setText(userInfoModel.user.rank_points.substring(4, 5));
				rank_points_thousand.setText(userInfoModel.user.rank_points.substring(1, 2));
				rank_points_ten_hundreds.setText(userInfoModel.user.rank_points.substring(0, 1));
			}else if (integral.length() == 6) {
				hundreds.setText(userInfoModel.user.rank_points.substring(3, 4));
				decade.setText(userInfoModel.user.rank_points.substring(4, 5));
				units.setText(userInfoModel.user.rank_points.substring(5, 6));
				rank_points_thousand.setText(userInfoModel.user.rank_points.substring(2, 3));
				rank_points_ten_hundreds.setText(userInfoModel.user.rank_points.substring(1, 2));
				rank_points_hundred_thousand.setText(userInfoModel.user.rank_points.substring(0, 1));
			}
		} else if (url.endsWith(ProtocolConst.ARTICLE)) {
			list = articleModel.articleArray;
			adapter.gedata(articleModel.articleArray);
			listView.setAdapter(adapter);
			adapter.notifyDataSetChanged();
		}

	}

}
