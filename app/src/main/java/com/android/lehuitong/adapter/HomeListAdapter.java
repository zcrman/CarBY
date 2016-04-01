package com.android.lehuitong.adapter;

import java.util.List;

import com.android.lehuitong.component.HomePromoteGoodsHolder;
import com.android.lehuitong.model.HomeModel;
import com.android.lehuitong.protocol.GOODS;
import com.android.lehuitong.protocol.PHOTO;
import com.android.lehuitong.protocol.SHOP;
import com.android.lehuitong.protocol.SIMPLEGOODS;
import com.funmi.lehuitong.R;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class HomeListAdapter extends BaseAdapter {
	private List<SIMPLEGOODS> list;
	private List<GOODS> goods;
	private Context context;
	private SHOP shop;
	private View view;
	private SIMPLEGOODS simplegoods;
	private GOODS good;
	private String name;
	private PHOTO img;
	private String brief;
	private String goods_id;
	private int type;
	/**
	 * 
	 * @param context
	 * @param list
	 * @param goods
	 * @param homeModel
	 * @param type 判断进入页面的类型1，优惠卷详情，2，商品详情，3，
	 */
	public HomeListAdapter(Context context, List<SIMPLEGOODS> list,
			List<GOODS> goods, SHOP shop,int type) {
		this.list = list;
		this.context = context;
		this.goods = goods;
		this.shop = shop;
		this.type=type;
	}

	@Override
	public int getCount() {
		if (list == null) {
			return goods.size();
		} else if (goods == null) {
			return list.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		if (list == null) {
			return goods.get(position);
		} else if (goods == null) {
			return list.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@SuppressWarnings("static-access")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		HomePromoteGoodsHolder homePromoteGoodsHolder = null;

		if (convertView == null) {
			view = convertView.inflate(context,
					R.layout.item_home_promote_goods, null);
			homePromoteGoodsHolder = new HomePromoteGoodsHolder(context,type);

			homePromoteGoodsHolder.initView(view);
			view.setTag(homePromoteGoodsHolder);
		} else {
			homePromoteGoodsHolder = (HomePromoteGoodsHolder) view.getTag();
		}
		if (list != null) {
			simplegoods = list.get(position);
			name = simplegoods.name;
			brief = simplegoods.brief;
			img = simplegoods.img;
			goods_id = simplegoods.id + "";
			homePromoteGoodsHolder.setInfo(name, brief, img, goods_id,
					simplegoods.shop_price,simplegoods.promote_valume,type);
		} else if (goods != null && goods.size() > 0) {
			good = goods.get(position);
			name = good.goods_name;
			brief = good.goods_brief;
			goods_id = good.goods_id + "";
			homePromoteGoodsHolder.setInfo(name, brief, good.goods_thumb,
					goods_id, good.shop_price,good.sales_volume,type,shop.shop_name,good.canBuy,good.promote_start_date,good.promote_end_date,shop,good.goods_id);
		}
		return view;
	}
}
