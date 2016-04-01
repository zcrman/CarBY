package com.android.lehuitong.activity;

import java.text.DecimalFormat;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.SearchManager.OnCancelListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.android.lehuitong.adapter.BuyOrderDetailAdpter;
import com.android.lehuitong.model.OrderModel;
import com.android.lehuitong.protocol.GOODORDER;
import com.android.lehuitong.protocol.ProtocolConst;
import com.android.lehuitong.view.AddressDialog;
import com.external.androidquery.callback.AjaxStatus;
import com.external.maxwin.view.XListView;
import com.funmi.lehuitong.R;

/** 购买订单详情页 */
public class BuyOrderDetailActivity extends BaseActivity implements
		BusinessResponse, OnClickListener {
	private TextView title_text;
	private ImageView title_back;
	private View foot;
	private View head;
	private AddressDialog dialog;
	private TextView order_nuber;
	private TextView order_price;

	private TextView order_detail_state;
	private TextView order_detail_cancel;
	private TextView order_detail_pay;
	private TextView buy_detail_agin;
	private TextView order_detail_delete;

	private XListView order_list;
	private TextView recive_name;
	private TextView recive_address;
	private TextView Receive_way;
	private TextView detail_ship_price;
	private TextView detail_good_price;
	private TextView detail_bonus_price;
	private TextView recive_phone;
	private OrderModel model;
	private BuyOrderDetailAdpter adpter;
	private String order_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_buyorder_detail);
		init();
		OnClickListener();
	}

	private void init() {
		title_back = (ImageView) findViewById(R.id.title_back);
		title_text = (TextView) findViewById(R.id.title_text);
		title_text = (TextView) findViewById(R.id.title_text);
		title_text.setText("订单详情");
		order_list = (XListView) findViewById(R.id.order_detail_list);
		foot = LayoutInflater.from(this).inflate(
				R.layout.footer_buyorder_detail, null);
		head = LayoutInflater.from(this).inflate(
				R.layout.item_buy_order_parent_header, null);
		order_nuber = (TextView) head.findViewById(R.id.order_nuber);
		order_price = (TextView) head.findViewById(R.id.order_price);
		recive_name = (TextView) foot.findViewById(R.id.recive_name);
		recive_address = (TextView) foot.findViewById(R.id.recive_address);
		Receive_way = (TextView) foot.findViewById(R.id.Receive_way);
		recive_phone = (TextView) foot.findViewById(R.id.recive_phone);
		detail_ship_price = (TextView) foot
				.findViewById(R.id.detail_ship_price);
		detail_good_price = (TextView) foot
				.findViewById(R.id.detail_good_price);
		detail_bonus_price = (TextView) foot
				.findViewById(R.id.detail_bonus_price);
		order_detail_state = (TextView)findViewById(R.id.order_detail_state);
		order_detail_cancel = (TextView) 
				findViewById(R.id.order_detail_cancel);
		order_detail_pay = (TextView) findViewById(R.id.order_detail_pay);
		buy_detail_agin = (TextView) findViewById(R.id.buy_detail_agin);
		order_detail_delete = (TextView)findViewById(R.id.order_detail_delete);
		model = new OrderModel(this);
		model.addResponseListener(this);
		adpter = new BuyOrderDetailAdpter(this);
		order_list.setPullLoadEnable(false);
		order_list.setPullRefreshEnable(false);
		order_list.addFooterView(foot, null, false);
		order_list.addHeaderView(head, null, false);
		order_id = getIntent().getStringExtra("order_id");
		model.seeDetails(order_id);

	}

	private void OnClickListener() {
		title_back.setOnClickListener(this);
		order_detail_delete.setOnClickListener(this);
		order_detail_cancel.setOnClickListener(this);
		order_detail_pay.setOnClickListener(this);
		buy_detail_agin.setOnClickListener(this);
		order_list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(BuyOrderDetailActivity.this,
						MyPurchaseDetailActivity.class);
				intent.putExtra("id", Integer.parseInt(model.detail.goods_list
						.get(arg2 - 2).goods_id));
				startActivity(intent);
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_back:
			finish();
			break;
		case R.id.order_detail_delete:
			dialog = new AddressDialog(this) {
				@Override
				public void setCancel() {

					super.setCancel();
					dialog.mDialog.dismiss();
				}

				@Override
				public void setCommit() {

					super.setCommit();
					model.orderDelect(model.detail.order_id);
					dialog.mDialog.dismiss();
				}
			};
			dialog.settitle("你确定要删除该订单吗？");
			dialog.mDialog.show();
			break;
		case R.id.order_detail_cancel:
			dialog = new AddressDialog(this) {
				@Override
				public void setCancel() {

					super.setCancel();
					dialog.mDialog.dismiss();
				}

				@Override
				public void setCommit() {
					super.setCommit();
					model.orderCancle(model.detail.order_id);
					dialog.mDialog.dismiss();
				}
			};
			dialog.settitle("你确定要取消该订单吗？");
			dialog.mDialog.show();
			break;
		case R.id.order_detail_pay:
			Intent intent = new Intent(this, TallyOrderActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("order_id",order_id);
			bundle.putInt("status", 1);
			intent.putExtras(bundle);
			startActivity(intent);
			break;
		default:
			break;
		}

	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		if (url.endsWith(ProtocolConst.ORDER_DATAIL)) {
			adpter.bindData(model.detail.goods_list);
			adpter.getPrice(model.detail.goods_amount_fee);
			order_list.setAdapter(adpter);

			recive_name.setText(model.detail.address.consignee);
			recive_address.setText(model.detail.address.country_name
					+ model.detail.address.province_name
					+ model.detail.address.city_name
					+ model.detail.address.district_name
					+ model.detail.address.address);
			Receive_way.setText(model.detail.shipping.shipping_name);
			detail_ship_price.setText(model.detail.shipping.shipping_fee);
			detail_good_price.setText(model.detail.formated_total_fee);
//			 detail_bonus_price.setText(model.detail.formated_bonus + "元");
			recive_phone.setText(model.detail.address.mobile);
			order_price.setText(model.detail.formated_total_fee);
			order_nuber.setText(model.detail.order_sn);
			double price=0;
			for(int i = 0; i <model.detail.goods_list.size(); i ++){
				Log.i("model", ""+model.detail.goods_list.size());
				
				price=TallyOrderActivity.add(price, Double.valueOf(model.detail.goods_list.get(i).formated_shop_price));
				Log.i("price", ""+price);
			}
			DecimalFormat format = new DecimalFormat("#####0.00");
			if(TallyOrderActivity.add(price,Double.parseDouble(model.detail.shipping.shipping_fee))<Double.parseDouble(model.detail.formated_bonus)){
				detail_bonus_price.setText(format.format(price)+"元");
			}else{
			detail_bonus_price.setText(model.detail.formated_bonus);
			}
			switch (Integer.parseInt(model.detail.order_status)) {
			case 1:
				order_detail_state.setText("未付款");
				order_detail_delete.setVisibility(View.GONE);
				order_detail_cancel.setVisibility(View.VISIBLE);
				order_detail_pay.setVisibility(View.VISIBLE);
				buy_detail_agin.setVisibility(View.GONE);
				break;
			case 2:
				order_detail_state.setText("已付款");
				order_detail_delete.setVisibility(View.GONE);
				order_detail_cancel.setVisibility(View.GONE);
				order_detail_pay.setVisibility(View.GONE);
				buy_detail_agin.setVisibility(View.GONE);
				break;
			case 3:
				order_detail_state.setText("已发货");
				order_detail_delete.setVisibility(View.GONE);
				order_detail_cancel.setVisibility(View.GONE);
				order_detail_pay.setVisibility(View.GONE);
				buy_detail_agin.setVisibility(View.GONE);
				break;
			case 4:
				order_detail_state.setText("交易完成");
				order_detail_delete.setVisibility(View.VISIBLE);
				order_detail_cancel.setVisibility(View.GONE);
				order_detail_pay.setVisibility(View.GONE);
				buy_detail_agin.setVisibility(View.GONE);
				break;
			case 5:
				order_detail_state.setText("预定已确定");
				order_detail_delete.setVisibility(View.GONE);
				order_detail_cancel.setVisibility(View.GONE);
				order_detail_pay.setVisibility(View.GONE);
				buy_detail_agin.setVisibility(View.GONE);
				break;
			case 7:
				order_detail_state.setText("交易取消");
				order_detail_delete.setVisibility(View.VISIBLE);
				order_detail_cancel.setVisibility(View.GONE);
				order_detail_pay.setVisibility(View.GONE);
				buy_detail_agin.setVisibility(View.GONE);
				break;
			case 8:
				order_detail_state.setText("预定待确认");
				order_detail_delete.setVisibility(View.GONE);
				order_detail_cancel.setVisibility(View.GONE);
				order_detail_pay.setVisibility(View.GONE);
				buy_detail_agin.setVisibility(View.GONE);
				break;
			case 9:
				order_detail_state.setText("部分完成");
				order_detail_delete.setVisibility(View.GONE);
				order_detail_cancel.setVisibility(View.GONE);
				order_detail_pay.setVisibility(View.GONE);
				buy_detail_agin.setVisibility(View.GONE);
				break;
			default:
				order_detail_state.setText("");
				break;
			}
		} else if (url.endsWith(ProtocolConst.ORDER_CANCEL)) {
			Toast.makeText(this, "取消成功", Toast.LENGTH_SHORT).show();
			model.seeDetails(order_id);
		} else if (url.endsWith(ProtocolConst.ORDER_DELETE)) {
			Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
			finish();
		}

	}
}