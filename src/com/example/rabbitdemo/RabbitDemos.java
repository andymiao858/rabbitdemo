package com.example.rabbitdemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class RabbitDemos extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setListAdapter(new SimpleAdapter(this, getData(), android.R.layout.simple_list_item_1, new String[]{"title"}, new int[]{android.R.id.text1}));
		getListView().setTextFilterEnabled(true);
	}
	
	private List<Map<String, Object>> getData(){
		List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_TEST);
		PackageManager pm = getPackageManager();
		List<ResolveInfo> infos = pm.queryIntentActivities(intent, 0);
		for(ResolveInfo info : infos){
			Intent startIntent = new Intent();
			try {
				startIntent.setClass(this, Class.forName(info.activityInfo.name));
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			addItem(datas, info.loadLabel(pm).toString(), startIntent );
		}
		return datas;
		
	}

	
	private void addItem(List<Map<String, Object>> data, String title, Intent intent){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", title);
		map.put("intent", intent);
		data.add(map);
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Map<String, Object> item = (Map<String, Object>)l.getItemAtPosition(position);
		Intent intent = (Intent)item.get("intent");
		startActivity(intent);
	}
	
}
