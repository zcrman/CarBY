package com.android.lehuitong.model;

import java.io.ByteArrayOutputStream;

import com.funmi.lehuitong.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;

public class ImageLoaderUtils {
	private static ImageLoaderConfiguration configuration;
	public static String IMAGE_PATH=Environment.getExternalStorageState()+"/lehuitong/image";
	private static ImageLoaderConfiguration getCofig(Context context){
		if (configuration==null)
		{
			configuration=new ImageLoaderConfiguration.Builder(context).discCacheSize(1024*1024*50).discCacheFileCount(100).build();
		}
		return configuration;
	}
	public static void displaySdcardImageage(final Context context,
			final String filePathAndName,
			final ImageView imageView)
	{
		ImageLoader imageLoader=ImageLoader.getInstance();
		imageLoader.init(getCofig(context));
		String uri="file://"+filePathAndName;
		imageLoader.displayImage(uri, imageView);
	}

	public static void displayNetworkImage(final Context context,final String url,final String path,final ImageView imageView)
	{
		DisplayImageOptions options=  new DisplayImageOptions.Builder().showImageForEmptyUri(R.drawable.default_image)
				.showImageOnFail(R.drawable.default_image).resetViewBeforeLoading(false).delayBeforeLoading(300)
				.cacheOnDisc(true).cacheInMemory(true).bitmapConfig(Bitmap.Config.ARGB_8888).build();
		ImageLoader imageLoader=ImageLoader.getInstance();
		imageLoader.init(getCofig(context));
		imageLoader.displayImage(url, imageView,options,new ImageLoadingListener() {
			
			@Override
			public void onLoadingStarted(String imageUri, View view) {
				
			}
			
			@Override
			public void onLoadingFailed(String imageUri, View view,
					FailReason failReason) {
				
			}
			
			@Override
			public void onLoadingComplete
			(String imageUri, 
					View view, 
					Bitmap loadedImage) {
				try {
					ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
					loadedImage.compress(CompressFormat.PNG, 100, byteArrayOutputStream);
					byte[] data=byteArrayOutputStream.toByteArray();
					int index=imageUri.lastIndexOf("/")+1;
					String fileName=imageUri.substring(index);
					
					Tools.writeToSdcard(context, IMAGE_PATH, fileName, data);
				} catch (Exception e) {
e.printStackTrace();
				}
				
			}
			
			@Override
			public void onLoadingCancelled(String imageUri, View view) {
				
			}
		});
	}
}
