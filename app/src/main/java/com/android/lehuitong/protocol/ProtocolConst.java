package com.android.lehuitong.protocol;

import android.os.Environment;

/**
 * 接口地址
 * 
 * @author shenlw
 * 
 */
public class ProtocolConst {

	public static String FILEPATH = Environment.getExternalStorageDirectory()
			+ "/Lehuitong/cache";

	/** 服务器地址 */
	public static String WEB_URL = "http://api.tibetlehuy.com/";

	/** 登陆接口 */
	public static String LOGIN = "user/signin";

	/** 注册接口 */
	public static String REGISTER = "user/signup";
	/**获取验证码接口*/
	public static String GETCODE = "user/getValidateCode";
	/** 获取个人信息接口 */
	public static String GET_USERINFO = "user/info";

	/** 主页数据加载接口 */
	public static String HOME_PAGE_LOAD_DATA = "home/data";

	/** 分类获取商品列表接口 */
	public static String GET_PRODUCT_LIST = "search";

	/** 忘记密码接口 */
	public static String FORGOTTEN_PWD = "user/forgetpswd";

	/** 修改密码接口 */
	public static String CHANGE_PWD = "user/changepswd";

	/** 添加收货地址接口 */
	public static String ADDRESS_ADD = "address/add";

	/** 删除收货地址接口 */
	public static String ADDRESS_DELETE = "address/delete";

	/** 修改收货地址接口 */
	public static String ADDRESS_UPDATE = "address/update";

	/** 设置默认收货地址接口 */
	public static String ADDRESS_SETDEFAULT = "address/setDefault";

	/** 获得收货地址列表 接口 */
	public static String ADDRESS_LIST = "address/list";

	/** 获取详细信息接口 */
	public static String ADDRESS_INFO = "address/info";

	/** 获取商家列表接口 */
	public static String SELLER_LIST = "seller/list";
	
	/** 获取商家详情接口 */
	public static String  SELLER_INFO= "seller/info";

	/** 查看购物车中的商品接口 */
	public static String CART_LIST = "cart/list";

	/** 添加商品到购物车接口 */
	public static String CART_CREATE = "cart/create";

	/**删除购物车中的商品接口 */
	public static String CART_DELETE = "cart/delete";

	/** 修改购物车中的商品数量接口 */
	public static String CART_UPDATE = "cart/update";

	/** 获取订单列表 */
	public static String ORDER_LIST = "order/list";

	/** 生成订单（购物车） */
	public static String FLOW_CHECKORDER = "flow/checkOrder";

	/** 确认下单 */
	public static String FLOW_DONE = "flow/done";

	/** 取消订单 */
	public static String ORDER_CANCEL = "order/cancel";
	
	/**删除订单**/
	public static String ORDER_DELETE="order/delete";

	/** 查看详情 */
	public static String ORDER_DATAIL = "order/detail";

	/** 查看快递记录 */
	public static String ORDER_EXPRESS = "order/express";

	/** 确认收货 */
	public static String ORDER_AFFIRMRECEIVED = "order/affirmReceived";

	/** 支付订单 */
	public static String ORDER_PAY = "order/pay";
	
	/**获取城市列表*/
	public static String REGION = "region";	 
	
	/**通过分类获取商品列表*/
	public static String SEARCH="search";
	
	/**优惠券、ktv生成订单**/
	public static String FLOW_TICKET_CHECKORDER="flow/ticket_checkOrder";
	
	/**优惠券、ktv确认生成订单**/
	public static String FLOW_TICKET_DONE="flow/ticket_done";
	
	/**优惠券、ktv获取订单列表**/
	public static String ORDER_TICKET_LIST="order/ticket_list";
	
	/**优惠券、ktv取消订单**/
	public static String ORDER_TICKET_CANCEL="order/ticket_cancel";
	
	/**优惠券、ktv查看详情**/
	public static String ORDER_TICKET_DETAIL="order/ticket_detail";
	
	/**优惠券、ktv删除订单**/
	public static String ORDER_TICKET_DELETE="order/ticket_delete";
	
	/**我的积分问答列表*/
	public static String ARTICLE="article";
	
	/**商品详情**/
	public static String GOODS="goods";
	
	/**个人资料修改接口**/
	public static String UODATEUSERINFO="user/updateUserInfo";

	/**获取商品分类列表**/
	public static String CATEGORY_LIST = "category/list";
	
	/**添加建议接口*/
	
	public static String ADD_SUGGESTION ="add_suggestion";
	/**推送接口*/
	public static String USER_BLINDPUSHER="user/bindPusher";
	
	/**支付宝成功后调用接口*/
	public static String FLOW_PAYDONE="flow/pay_done";
	
	/**我的红包接口*/
	public static String BONUS="bonus";
	
	/**快递选择接口*/
	public static String FLOW_SELECT_SHIPPING ="flow/select_shipping";
	
	/**选择红包接口*/
	public static String FLOW_SELECT_BONUS ="flow/select_bonus";
}
