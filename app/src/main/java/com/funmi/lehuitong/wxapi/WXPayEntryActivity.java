package com.funmi.lehuitong.wxapi;
//import com.android.lehuitong.LehuitongApp;
import com.android.lehuitong.activity.LehuitongMainActivity;
import com.android.lehuitong.model.PayModel;
import com.android.lehuitong.pay.wechat.Constants;
import com.funmi.lehuitong.R;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

	private IWXAPI api;

	private PayModel model;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.pay_result);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
		api.handleIntent(getIntent(), this);
//		 LehuitongApp.getInstance().addActivity(this);
		
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
		
	}
	@Override
	public void onResp(BaseResp resp) {
		Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);

		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(R.string.app_tip);
			builder.setMessage(getString(R.string.pay_result_callback_msg,
					resp.errStr + ";code=" + String.valueOf(resp.errCode)));
			//builder.show();
			
			if (resp.errCode == 0) {
				Toast.makeText(this, "支付成功", Toast.LENGTH_SHORT).show();
				SharedPreferences  shared = getSharedPreferences("number", MODE_PRIVATE);
				String number=shared.getString("number", "");
				model=new PayModel(this);
				model.paydone(number);
				Intent intent=new Intent(this, LehuitongMainActivity.class);
				startActivity(intent);
//				LehuitongApp.getInstance().exit();
				finish();
				//支付成功页面
			} else {
				Toast.makeText(this, "您取消了支付", Toast.LENGTH_SHORT).show();
				finish();
			}
		}
	}
}