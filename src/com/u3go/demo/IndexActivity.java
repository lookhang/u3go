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
	private String[] texts = {"����","����", "����", "����", "��Ʊ", "�ⷿ", "��ְ", "����"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//��ʼ����������  
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
				Log.i(TAG, "���뵼��");
				BaiduNaviManager.getInstance().launchNavigator(
						IndexActivity.this, 30.526178, 114.374127, "���·׿��Ȫ",
						30.51742, 114.420359, "���пƼ���ѧ",
						NE_RoutePlan_Mode.ROUTE_PLAN_MOD_MIN_TIME, // ��·��ʽ
						false, // ��ʵ����
						BaiduNaviManager.STRATEGY_FORCE_ONLINE_PRIORITY, // �����߲���
						new OnStartNavigationListener() { // ��ת����

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
			// ��ʾ��ѡItem��ItemText
			
		}
	}
	
	
	//navi
	private boolean mIsEngineInitSuccess = false;
	private NaviEngineInitListener mNaviEngineInitListener = new NaviEngineInitListener() {
		public void engineInitSuccess() {
			// ������ʼ�����첽�ģ���ҪһС��ʱ�䣬�������־��ʶ�������Ƿ��ʼ���ɹ���Ϊtrueʱ����ܷ��𵼺�
			mIsEngineInitSuccess = true;
		}

		public void engineInitStart() {
		}

		public void engineInitFail() {
		}
	};

	private String getSdcardDir() {//��ȡSD����·��
		if (Environment.getExternalStorageState().equalsIgnoreCase(
				Environment.MEDIA_MOUNTED)) {
			return Environment.getExternalStorageDirectory().toString();
		}
		return null;
	}
}
