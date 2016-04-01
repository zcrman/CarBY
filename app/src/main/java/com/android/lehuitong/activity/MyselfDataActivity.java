package com.android.lehuitong.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.service.textservice.SpellCheckerService.Session;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.android.lehuitong.model.ImageLoaderUtils;
import com.android.lehuitong.model.UserInfoModel;
import com.android.lehuitong.protocol.ProtocolConst;
import com.android.lehuitong.protocol.SESSION;
import com.android.lehuitong.view.ChangeUserNameDialog;
import com.android.lehuitong.view.CircularImage;
import com.android.lehuitong.view.TimePickerDialog;
import com.android.lehuitong.view.UserImageDialog;
import com.android.lehuitong.view.TimePickerDialog.PickerDialogButtonListener;
import com.android.lehuitong.view.UserSexChooseDialog;
import com.external.androidquery.callback.AjaxStatus;
import com.funmi.lehuitong.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * 个人资料页面
 * 
 * @author shenlw
 * 
 */
public class MyselfDataActivity extends BaseActivity implements OnClickListener, BusinessResponse {
	private Bitmap bitmap;
	private CircularImage mFace;
	private File tempFile;
	private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
	private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
	private static final int PHOTO_REQUEST_CUT = 3;// 结果
	/* 头像名称 */
	private static final String PHOTO_FILE_NAME = "temp_photo.jpg";

	private ImageView title_back;
	private TextView title_text;

	private RelativeLayout imageLayout;
	private RelativeLayout nameLayout;
	private RelativeLayout sexLayout;
	private RelativeLayout birthLayout;

	private TextView userName;
	private TextView userSex;
	private TextView userBirth;
	private TextView savebtn;

	private ChangeUserNameDialog changeUserNameDialog;
	private TimePickerDialog timePickerDialog;
	private UserSexChooseDialog userSexChooseDialog;
	private UserImageDialog userImageDialog;

	private UserInfoModel userInfoModel;

	private String imgUrl;

	private String sexId = "1";
	
	private DisplayImageOptions option;
	private ImageLoader loader;
	private Date chose;
	private Date current;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myself_data);
		init();
	}

	public void init() {

		loader = ImageLoader.getInstance();
		option = new DisplayImageOptions.Builder().showImageForEmptyUri(R.drawable.touxiang)
				.showImageOnFail(R.drawable.touxiang).resetViewBeforeLoading(false) 
				.delayBeforeLoading(300).cacheOnDisc(true).cacheInMemory(true).bitmapConfig(Bitmap.Config.RGB_565).build();

		title_back = (ImageView) findViewById(R.id.title_back);
		title_text = (TextView) findViewById(R.id.title_text);
		title_text.setText("个人资料");

		savebtn = (TextView) findViewById(R.id.finish_button);
		savebtn.setVisibility(View.VISIBLE);
		imageLayout = (RelativeLayout) findViewById(R.id.user_image_layout);
		nameLayout = (RelativeLayout) findViewById(R.id.user_name_layout);
		sexLayout = (RelativeLayout) findViewById(R.id.user_sex_layout);
		birthLayout = (RelativeLayout) findViewById(R.id.user_birth_date_layout);

		userName = (TextView) findViewById(R.id.user_name);
		mFace = (CircularImage) findViewById(R.id.user_image);
		this.mFace.setImageBitmap(bitmap);
		userSex = (TextView) findViewById(R.id.user_sex);
		userBirth = (TextView) findViewById(R.id.user_borth);
		userInfoModel = new UserInfoModel(this);
		userInfoModel.addResponseListener(this);
		userInfoModel.mylehui();
		userSexChooseDialog = new UserSexChooseDialog(MyselfDataActivity.this, R.style.customer_dialog) {
			@Override
			public void chooseSex(String sexName) {
				super.chooseSex(sexName);
				userSex.setText(sexName);
				userSexChooseDialog.dismiss();
			}
		};

		setOnClickListener();

	}

	private void setOnClickListener() {
		savebtn.setOnClickListener(this);
		title_back.setOnClickListener(this);
		imageLayout.setOnClickListener(this);
		nameLayout.setOnClickListener(this);
		sexLayout.setOnClickListener(this);
		birthLayout.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.title_back:
			finish();
			break;
		case R.id.finish_button:
			String path = imgUrl;
			String name = userName.getText().toString();
			String sex = userSex.getText().toString();
			int i = 1;
			if (sex.equals("男")) {
				i = 1;
			} else if (sex.equals("女")) {
				i = 0;
			}
			if(getCalaner()>0){
				Toast.makeText(getApplication(), "生日日期不能超前", 1).show();
			}else{
			String birth = userBirth.getText().toString();
			userInfoModel.changeuserinfo(name, i+"", birth, path);
			}
			break;
		case R.id.user_image_layout:

			userImageDialog = new UserImageDialog(this, R.style.customer_dialog) {
				@Override
				public void takePhoto() {
					super.takePhoto();

					Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
					// 判断存储卡是否可以用，可用进行存储
					if (hasSdcard()) {
						intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), PHOTO_FILE_NAME)));
					}
					startActivityForResult(intent, PHOTO_REQUEST_CAMERA);

					userImageDialog.dismiss();
				}

				@Override
				public void uploadPhoto() {
					super.uploadPhoto();

					// 激活系统图库，选择一张图片
					Intent intent = new Intent(Intent.ACTION_PICK);
					intent.setType("image/*");
					startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
					userImageDialog.dismiss();
				}
			};
			userImageDialog.show();
			break;
		case R.id.user_name_layout:
			changeUserNameDialog = new ChangeUserNameDialog(MyselfDataActivity.this, R.style.customer_dialog) {
				@Override
				public void commitChange(String phoneStr) {
					super.commitChange(phoneStr);
					userName.setText(phoneStr);
					changeUserNameDialog.dismiss();
				}
			};
			changeUserNameDialog.show();
			break;
		case R.id.user_sex_layout:

			userSexChooseDialog.show();
			break;
		case R.id.user_birth_date_layout:
			final Calendar calendar = Calendar.getInstance();
			timePickerDialog = new TimePickerDialog(MyselfDataActivity.this, R.style.SampleTheme_Light, new PickerDialogButtonListener() {
				
				@Override
				public void onClick(View v) {
					if (timePickerDialog.getYear() > calendar.get(Calendar.YEAR)) {
						Toast.makeText(MyselfDataActivity.this, "不能选择当前以后的日期~", Toast.LENGTH_SHORT).show();
					} else if(timePickerDialog.getYear() == calendar.get(Calendar.YEAR)) {
						if (timePickerDialog.getMonth() > calendar.get(Calendar.MONTH) + 1) {
							Toast.makeText(MyselfDataActivity.this, "不能选择当前以后的日期~", Toast.LENGTH_SHORT).show();
						} else {
							if (timePickerDialog.getDay() > calendar.get(Calendar.DAY_OF_MONTH)) {
								Toast.makeText(MyselfDataActivity.this, "不能选择当前以后的日期~", Toast.LENGTH_SHORT).show();
							} else {
								userBirth.setText(timePickerDialog.getTime());
								timePickerDialog.dismiss();
							}
						}
					} else {
						userBirth.setText(timePickerDialog.getTime());
						timePickerDialog.dismiss();
					}

				}
			}, 100);
			timePickerDialog.show();
			break;

		}

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == PHOTO_REQUEST_GALLERY) {
			if (data != null) {
				// 得到图片的全路径
				Uri uri = data.getData();
				crop(uri);
			}

		} else if (requestCode == PHOTO_REQUEST_CAMERA) {
			if (hasSdcard()) {
				tempFile = new File(Environment.getExternalStorageDirectory(), PHOTO_FILE_NAME);
				crop(Uri.fromFile(tempFile));
			} else {
				Toast.makeText(this, "未找到存储卡，无法存储照片！", 0).show();
			}

		} else if (requestCode == PHOTO_REQUEST_CUT) {
			try {
				bitmap = data.getParcelableExtra("data");
				this.mFace.setImageBitmap(bitmap);
				saveImg(bitmap);
				boolean delete = tempFile.delete();
				System.out.println("delete = " + delete);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 剪切图片
	 * 
	 * @function:
	 * @author:Jerry
	 * @date:2013-12-30
	 * @param uri
	 */
	private void crop(Uri uri) {
		// 裁剪图片意图
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		// 裁剪框的比例，1：1
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// 裁剪后输出图片的尺寸大小
		intent.putExtra("outputX", 250);
		intent.putExtra("outputY", 250);
		// 图片格式
		intent.putExtra("outputFormat", "JPEG");
		intent.putExtra("noFaceDetection", true);// 取消人脸识别
		intent.putExtra("return-data", true);// true:不返回uri，false：返回uri
		startActivityForResult(intent, PHOTO_REQUEST_CUT);
	}

	private boolean hasSdcard() {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	public void saveImg(Bitmap bitmap) {
		if (hasSdcard()) {
			imgUrl = Environment.getExternalStorageDirectory() + "/" + SESSION.getInstance().uid + ".png";
		}
		File f = new File(imgUrl);
		try {
			f.createNewFile();
		} catch (IOException e) {
		}
		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
		try {
			fOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {
		if (url.endsWith(ProtocolConst.GET_USERINFO)) {
			String name = userInfoModel.user.nickname;
			userBirth.setText(userInfoModel.user.birthday);
			if (userInfoModel.user.nickname.length()==4) {
				 name=userInfoModel.user.nickname.substring(0, 4);
			}
			else {
				userName.setText(userInfoModel.user.nickname);
			}
			if (name==null) {
				SharedPreferences shared = getSharedPreferences("user", MODE_PRIVATE);
				userName.setText(shared.getString("name", ""));
			}
			else if (name.equals("null")) {
				SharedPreferences shared = getSharedPreferences("user", MODE_PRIVATE);
				userName.setText(shared.getString("name", ""));
			}else {
				userName.setText(userInfoModel.user.nickname);
			}
			sexId = userInfoModel.user.sex;
			if (sexId.equals("1")) {
				userSex.setText("男");
			} else {
				userSex.setText("女");
			}
			loader.displayImage(userInfoModel.user.head_img, mFace, option, new SimpleImageLoadingListener() {
				@Override
				public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
					((CircularImage) view).setImageBitmap(loadedImage);
					saveImg(loadedImage);

				}
				
			});
		}else if (url.endsWith(ProtocolConst.UODATEUSERINFO)) {
			Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
//			Intent intent=new Intent(this,MyLeHuiActivity.class);
//			startActivity(intent);
			userInfoModel.mylehui();
			finish();
			
		}
		
	}
	private int getCalaner() {
		Calendar calendar=Calendar.getInstance();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		try {
		if(timePickerDialog.getTime()!=null){
				chose=format.parse(timePickerDialog.getTime());
		}
		current=format.parse(calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DAY_OF_MONTH));
			} catch (Exception e) {
				e.printStackTrace();
				try {
					current=format.parse(calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DAY_OF_MONTH));
					chose=format.parse(format.format(new Date()));
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
			}
		long choiceTime = chose.getTime();
		long currentTime=current.getTime();

		return (int) ((new Long(choiceTime).longValue()-new Long(currentTime).longValue())/(1000L*60*60*24));

	}
	
}
