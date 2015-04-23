package com.example.rabbitdemo.activity;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Region.Op;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import com.example.rabbitdemo.R;

public class FlipBookActivity extends Activity {

	private final static String TAG = "FlipBookActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.flip_book_layout);
		ViewGroup parent = (ViewGroup) findViewById(R.id.book_container);
		
		BookView book1 = new BookView(this);
		book1.setBackgroundColor(Color.WHITE);

		InputStream is = null;
		byte[] buffer = new byte[1024];
		int count = 0;
		StringBuffer sb = new StringBuffer();
		try {
			is = getAssets().open("book1.txt");
			count = is.read(buffer, 0, 1024);
			while(count > 0){
				 sb.append(new String(buffer));
				 count = is.read(buffer, 0, 1024);
			}
			book1.setText(sb.toString());
		} catch (IOException e) {
			try{
				is.close();
			}catch(Exception ex){
				
			}
		}
		
		
//		try {
//			is = getAssets().open("book2.txt");
//			sb = new StringBuffer();
//			count = is.read(buffer, 0, 1024);
//			while(count > 0){
//				 sb.append(sb.append(new String(buffer)));
//				 count = is.read(buffer, 0, 1024);
//			}
//			book2.setText(sb.toString());
//		} catch (IOException e) {
//			try{
//				is.close();
//			}catch(Exception ex){
//				
//			}
//		}
	
		
//		parent.addView(book2, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		parent.addView(book1, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		Log.d(TAG, "FlipBook onCreate.");
	}

	private static class BookView extends TextView{

		private float x;

		private float y;

		private Path path;

		private Paint paint;
		
		public BookView(Context context, AttributeSet attrs, int defStyleAttr) {

			super(context, attrs, defStyleAttr);

		}

		public BookView(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		public BookView(Context context) {
			super(context);
			paint = new Paint();
			paint.setColor(Color.BLACK);
			paint.setStyle(Style.STROKE);
			paint.setStrokeWidth(10);
			paint.setAntiAlias(true);
			path = new Path();
		}
	

		@Override
		protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
			x = getWidth();
			y = getHeight();
		}

		@Override
		protected void onDraw(Canvas canvas) {
			
			float midX = (x + getWidth()) / 2;
			float midY = (y + getHeight()) / 2;
			float mmidX = (midX + x) / 2;
			float mmidY = (midY + y) / 2;
			float k = -(x - getWidth()) / (y - getHeight());
			float startHx = mmidX + (getHeight() - mmidY) / k;
			float endVy = k * (getWidth() - mmidX) + mmidY;
			float controlHx = midX + (getHeight() - midY) / k;
			float controlVy = k * (getWidth() - midX) + midY;
			float startX1 = (x + controlHx) / 2;
			float startY1 = (y + getHeight()) / 2;
			float startX2 = (x + getWidth()) / 2;
			float startY2 = (y + controlVy) / 2;
			path.reset();
			path.moveTo(startHx, getHeight());
			path.quadTo(controlHx, getHeight(), startX1, startY1);
			path.lineTo(x, y);
			path.lineTo(startX2, startY2);
			path.quadTo(getWidth(), controlVy, getWidth(), endVy);
			canvas.drawPath(path, paint);
			
			
			float borderX1 = ((startX1 + startHx) / 2 + controlHx) / 2;
			float borderY1 = ((startY1 + getHeight()) / 2 + getHeight()) / 2;
			float borderX2 = ((startX2 +  getWidth()) / 2 + controlVy) / 2;
			float borderY2 = ((startY2 + endVy) / 2 + controlVy) / 2;
			path.reset();
			path.moveTo(0, 0);
			path.lineTo(0, getHeight());
			path.lineTo(startHx, getHeight());
			path.quadTo(controlHx, getHeight(), startX1, startY1);
			path.lineTo(x, y);
			path.lineTo(startX2, startY2);
			path.quadTo(getWidth(), controlVy, getWidth(), endVy);
			path.lineTo(getWidth(), getHeight());
			path.lineTo(getWidth(), 0);
			path.close();
			canvas.clipRect(canvas.getClipBounds());
			canvas.clipPath(path, Op.INTERSECT);
			super.onDraw(canvas);
		}

		@Override
		public boolean onTouchEvent(MotionEvent event) {
			x = event.getX();
			y = event.getY();
			switch (event.getAction()) {
			case MotionEvent.ACTION_MOVE:
				invalidate();
				break;
			case MotionEvent.ACTION_DOWN:
				invalidate();
				break;

			}
			return true;
		}

	}


	
	
}
