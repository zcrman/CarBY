package com.android.lehuitong.activity;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.activity.StartActivity;
import com.funmi.lehuitong.R;

public class GalleryImageActivity extends BaseActivity implements OnGestureListener, OnTouchListener {
	private ImageView iv1;
	private ImageView iv2;
	private ImageView iv3;
	
	private ViewPager imagePager;
	private PagerAdapter galleryImageAdapter;
    
    private SharedPreferences shared;
	private SharedPreferences.Editor editor;
	
	private View galleryImg1;
	private View galleryImg2;
	private View galleryImg3;
	
	private View enter;
	
	@SuppressWarnings("unused")
	private int pager_num;
	
	private List<View> list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gallery);
		
		shared = getSharedPreferences("userInfo", 0); 
		editor = shared.edit();
		galleryImg1 = LayoutInflater.from(this).inflate(R.layout.item_gallery_img1, null);
		galleryImg2 = LayoutInflater.from(this).inflate(R.layout.item_gallery_img2, null);
		galleryImg3 = LayoutInflater.from(this).inflate(R.layout.item_gallery_img3, null);
		
		iv1 = (ImageView) galleryImg1.findViewById(R.id.gallery1);
		iv2 = (ImageView) galleryImg2.findViewById(R.id.gallery2);
		iv3 = (ImageView) galleryImg3.findViewById(R.id.gallery3);
//		enter=galleryImg3.findViewById(R.id.log_button);
		galleryImg3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(GalleryImageActivity.this,StartActivity.class);
				startActivity(intent);
				finish();
				
				editor.putBoolean("isFirstRun", false);
	            editor.commit();
				
			}
		});
		
		list = new ArrayList<View>();
    	Bitmap itemimage1=alterimage(R.drawable.bootpage1);
    	iv1.setImageBitmap(itemimage1);
    	list.add(galleryImg1);   
    	
    	Bitmap itemimage2=alterimage(R.drawable.bootpage2);
    	iv2.setImageBitmap(itemimage2);
    	list.add(galleryImg2);  
    	Bitmap itemimage3=alterimage(R.drawable.bootpage3);
    	iv3.setImageBitmap(itemimage3);
    	list.add(galleryImg3);  
		
    	imagePager = (ViewPager) findViewById(R.id.image_pager);
    	galleryImageAdapter = new PagerAdapter() {
			
			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0==arg1;
			}
			
			@Override
			public int getCount() {
				return list.size();
			}
				  
	        @Override  
		    public void destroyItem(ViewGroup container, int position, Object object)   {     
				   container.removeView(list.get(position));//删除页卡   
		    }  
	        public View instantiateItem(ViewGroup container, int position) {  //这个方法用来实例化页卡          
	        	   container.addView(list.get(position), 0);//添加页卡   
	        	   return list.get(position);  
	        }				
		};
		
		imagePager.setAdapter(galleryImageAdapter);
		imagePager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				pager_num = position + 1;
			}
			
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels)
            {
            }
			
			@Override
			public void onPageScrollStateChanged(int state) {
			}
		});
		
		imagePager.setOnTouchListener(this);
	}
	
	@SuppressWarnings("deprecation")
	GestureDetector mygesture = new GestureDetector(this);  
    public boolean onTouch(View v, MotionEvent event) {  
        return mygesture.onTouchEvent(event);  
    }

    @Override
	public boolean onDown(MotionEvent e) {
		return false;
	}
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
//		if (e1.getX() - e2.getX() > 120) {      
//			if(pager_num == 2) {
//				Intent intent = new Intent(GalleryImageActivity.this,LoginActivity.class);
//				startActivity(intent);
//				finish();
//				editor.putBoolean("isFirstRun", false);
//	            editor.commit();
//			}
//        }   
		return false;
	}
	@Override
	public void onLongPress(MotionEvent e) {
	}
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		return false;
	}
	@Override
	public void onShowPress(MotionEvent e) {
	}
	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}

	public Bitmap alterimage(int imageid) {
		InputStream is = this.getResources().openRawResource(imageid);
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = false;
		options.inSampleSize = 2;
		Bitmap btp = BitmapFactory.decodeStream(is, null, options);
		return btp;
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}
}