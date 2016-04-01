package com.android.lehuitong.activity;

import org.json.JSONException;
import org.json.JSONObject;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.android.lehuitong.model.ArticleModel;
import com.android.lehuitong.protocol.ProtocolConst;
import com.external.androidquery.callback.AjaxStatus;
import com.funmi.lehuitong.R;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class FeedBackActivity extends BaseActivity implements OnClickListener,BusinessResponse{
	private ImageView title_back;
	private TextView title_text;
	private EditText feedback_recommendation;
	private Button feedback_button;
	private ArticleModel articleModel;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback);
		init();
	}

	 private void init(){
		 title_back=(ImageView) findViewById(R.id.title_back);
		 title_back.setOnClickListener(this);
		 title_text=(TextView) findViewById(R.id.title_text);
		 title_text.setText("意见反馈");
		 feedback_recommendation=(EditText) findViewById(R.id.feedback_recommendation);
		 feedback_button=(Button) findViewById(R.id.feedback_button);
		 feedback_button.setOnClickListener(this);
		 articleModel=new ArticleModel(this	);
		 articleModel.addResponseListener(this);
		 }
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_back:
			finish();
			break;
		case R.id.feedback_button:
			String suggestion=feedback_recommendation.getText().toString();
			articleModel.feedback(suggestion);
			break;
		default:
			break;
		}
		
	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {

		if (url.endsWith(ProtocolConst.ADD_SUGGESTION)) {
			Toast.makeText(this, "感谢您对乐汇通的反馈和建议", Toast.LENGTH_SHORT).show();
		}
	}

}
