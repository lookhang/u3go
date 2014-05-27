package com.u3go.demo;

import java.util.ArrayList;
import java.util.HashMap;

import com.baidu.navisdk.BaiduNaviManager;
import com.baidu.navisdk.BNaviEngineManager.NaviEngineInitListener;
import com.baidu.navisdk.BaiduNaviManager.OnStartNavigationListener;
import com.baidu.navisdk.comapi.routeplan.RoutePlanParams.NE_RoutePlan_Mode;
import com.u3go.demo.navi.BNavigatorActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class IndexActivity extends Activity {
	private final String TAG="IndexActivity";
	private int[] pics = { R.drawable.shopping, R.drawable.navi,
			R.drawable.food, R.drawable.flower, R.drawable.ticket,
			R.drawable.house, R.drawable.parttimejob, R.drawable.search };
	private String[] texts = {"购物","导航", "订餐", "订花", "订票", "租房", "兼职", "搜索"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//初始化导航引擎  
        BaiduNaviManager.getInstance().  
            initEngine(this, getSdcardDir(), mNaviEngineInitListener, "ymmGEyTnjTwvGlia9r0C2WE8",null);

		setContentView(R.layout.index);
		GridView gridview = (GridView) findViewById(R.id.gridview);

		ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();

		for (int i = 0; i < 8; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("ItemImage", pics[i]);
			map.put("ItemText", texts[i]);
			lstImageItem.add(map);
		}

		SimpleAdapter saImageItems = new SimpleAdapter(this, lstImageItem,
				R.layout.pic_text, new String[] { "ItemImage", "ItemText" },
				new int[] { R.id.ItemImage, R.id.ItemText });
		gridview.setAdapter(saImageItems);
		gridview.setOnItemClickListener(new ItemClickListener());
	}

	class ItemClickListener implements OnItemClickListener {
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {

			@SuppressWarnings("unchecked")
			HashMap<String, Object> item = (HashMap<String, Object>) arg0
					.getItemAtPosition(arg2);
			Integer rid = (Integer) item.get("ItemImage");
			Log.i(TAG, rid.intValue()+","+R.drawable.navi);
			switch (rid.intValue()) {
			case R.drawable.navi:
				Log.i(TAG, "进入导航");
				BaiduNaviManager.getInstance().launchNavigator(
						IndexActivity.this, 30.526178, 114.374127, "珞瑜路卓刀泉",
						30.51742, 114.420359, "华中科技大学",
						NE_RoutePlan_Mode.ROUTE_PLAN_MOD_MIN_TIME, // 算路方式
						false, // 真实导航
						BaiduNaviManager.STRATEGY_FORCE_ONLINE_PRIORITY, // 在离线策略
						new OnStartNavigationListener() { // 跳转监听

							@Override
							public void onJumpToNavigator(Bundle configParams) {
								Intent intent = new Intent(IndexActivity.this,
										BNavigatorActivity.class);
								intent.putExtras(configParams);
								startActivity(intent);
							}

							@Override
							public void onJumpToDownloader() {
							}
						});
						break;
			case R.drawable.shopping:
				startActivity(new Intent(IndexActivity.this,
						SecondActivity.class));
				break;
			default:startActivity(new Intent(IndexActivity.this,
					AboutActivity.class));
				break;
			}
			// 显示所选Item的ItemText
			
		}
	}
	
	
	//navi
	private boolean mIsEngineInitSuccess = false;
	private NaviEngineInitListener mNaviEngineInitListener = new NaviEngineInitListener() {
		public void engineInitSuccess() {
			// 导航初始化是异步的，需要一小段时间，以这个标志来识别引擎是否初始化成功，为true时候才能发起导航
			mIsEngineInitSuccess = true;
		}

		public void engineInitStart() {
		}

		public void engineInitFail() {
		}
	};

	private String getSdcardDir() {//获取SD卡的路径
		if (Environment.getExternalStorageState().equalsIgnoreCase(
				Environment.MEDIA_MOUNTED)) {
			return Environment.getExternalStorageDirectory().toString();
		}
		return null;
	}
}
