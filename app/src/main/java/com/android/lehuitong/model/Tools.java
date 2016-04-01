package com.android.lehuitong.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.os.Environment;

public class Tools {
	private static FileInputStream inputStream;
	public static void writeToSdcard(Context context, String path, String fileName,
			byte[] fileData) {
		FileOutputStream fileOutputStream = null;
		try {

			String state = Environment.getExternalStorageState();
			if (Environment.DIRECTORY_PICTURES.equals(state)) {
				
				File filePath = new File(path);
				if (!filePath.exists()) {
					filePath.mkdirs();
				}
				File file = new File(path, fileName);
				fileOutputStream = new FileOutputStream(file);
				fileOutputStream.write(fileData);
				fileOutputStream.flush();
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			try {
				if (fileOutputStream != null) {
					fileOutputStream.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}
	public static void readFromScard(Context context,String path,String fileName){
		try {
			inputStream=new FileInputStream(path);
			byte[] b=new byte[1024*1024];
			int i=0;
			if((i=b.length)!=-1){
				inputStream.read(b);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
		try {
			inputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	}
}
