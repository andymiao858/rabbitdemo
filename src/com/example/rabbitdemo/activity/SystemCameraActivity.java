package com.example.rabbitdemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.rabbitdemo.R;

public class SystemCameraActivity extends Activity {
	
	private final static String TAG = "SystemCameraActivity";
	
	private final static int VIDEO_CODE = 1;

	private final static int IMAGE_CODE = 2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.system_camera_layout);
		View btn1 = findViewById(R.id.capture_video);
		View btn2 = findViewById(R.id.capture_image);
		btn1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setAction("android.media.action.VIDEO_CAPTURE");
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivityForResult(intent, VIDEO_CODE);
				
			}
		});
		
		btn2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setAction("android.media.action.IMAGE_CAPTURE");
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivityForResult(intent, IMAGE_CODE);
				
			}
		});
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d(TAG, String.valueOf(requestCode));
		switch(requestCode){
		case VIDEO_CODE:
			Log.d(TAG, "video result return.");
			
			break;
			
		case IMAGE_CODE:
			Log.d(TAG, "image result return.");
			break;
		}
	}
	
}
