package com.android.lehuitong.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.Attributes.Name;

import com.android.lehuitong.component.AddressManagerHolder;
import com.android.lehuitong.model.AddressModel;
import com.android.lehuitong.protocol.ADDRESS;
import com.android.lehuitong.view.AddressDialog;
import com.funmi.lehuitong.R;

import android.R.integer;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressWarnings("unused")
public class AddressManagerAdapter extends BaseAdapter {
	private Context context;
	private List<ADDRESS> addressList;
	private AddressModel addressModel;

	public AddressManagerAdapter(Context context, AddressModel addressModel) {
		this.context = context;
		this.addressModel = addressModel;
	}

	public void bindData(List<ADDRESS> addressList) {
		this.addressList = addressList;
	}

	@Override
	public int getCount() {

		return addressList.size();
	}

	@Override
	public Object getItem(int position) {

		return position;
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		int nunber = position;

		final AddressManagerHolder holder;
		final ADDRESS address = addressList.get(position);
		if (convertView == null) {
			holder = new AddressManagerHolder();
			final LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_address_manager, null);
			holder.address_select = (ImageView) convertView
					.findViewById(R.id.address_select);
			holder.myself_name = (TextView) convertView
					.findViewById(R.id.myself_name);
			holder.my_phone = (TextView) convertView
					.findViewById(R.id.my_phone);
			holder.my_address = (TextView) convertView
					.findViewById(R.id.my_address);
			holder.address_delete_button = (Button) convertView
					.findViewById(R.id.address_delete_button);
			convertView.setTag(holder);
		} else {
			holder = (AddressManagerHolder) convertView.getTag();
		}
		holder.address_select.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				addressModel.addressDefault(address.id + "");

			}
		});
		/** 点击删除是地址 */
		holder.address_delete_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				AddressDialog builder = new AddressDialog(context) {

					@Override
					public void setCommit() {
						super.setCommit();
						addressModel.addressDelete(address.id + "");
						mDialog.dismiss();

					}

					@Override
					public void setCancel() {
						super.setCancel();
						mDialog.dismiss();

					}

				};
				builder.mDialog.show();
			}

		});

		// 加载数据

		String consignee = address.consignee;
		String addressStr;
		if (address.province_name == null) {
			addressStr = "";
		} else {
			addressStr = address.province_name + address.city_name
					+ address.district_name + address.address;

		}
		String phone = address.mobile;
		int defaultAddress = address.default_address;
		holder.SetMyCouponsInfo(consignee, phone, addressStr, defaultAddress);

		return convertView;
	}

}
