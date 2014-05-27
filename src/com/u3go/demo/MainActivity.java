package com.u3go.demo;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.RadioGroup;

/**
 * TabHost界面
 * 
 * @author Administrator
 * 
 */
public class MainActivity extends Activity {

	@Override
	protected void onStart() {
		super.onStart();
	}

	private RadioGroup radioGroup;

	// 页卡内容
	private ViewPager mPager;
	// Tab页面列表
	private List<View> listViews;
	// 当前页卡编号
	private LocalActivityManager manager = null;

	private MyPagerAdapter mpAdapter = null;
	private int index;

	// 更新intent传过来的值
	@Override
	protected void onNewIntent(Intent intent) {
		setIntent(intent);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {

	}

	@Override
	public void onBackPressed() {
		Log.i("", "onBackPressed()");
		super.onBackPressed();
	}

	@Override
	protected void onPause() {
		Log.i("", "onPause()");
		super.onPause();
	}

	@Override
	protected void onStop() {
		Log.i("", "onStop()");
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		Log.i("", "onDestroy()");
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (getIntent() != null) {
			index = getIntent().getIntExtra("index", 0);
			mPager.setCurrentItem(index);
			setIntent(null);
		} else {
			if (index < 4) {
				index = index + 1;
				mPager.setCurrentItem(index);
				index = index - 1;
				mPager.setCurrentItem(index);

			} else if (index == 4) {
				index = index - 1;
				mPager.setCurrentItem(index);
				index = index + 1;
				mPager.setCurrentItem(index);
			}
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().requestFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.maintabs);
		mPager = (ViewPager) findViewById(R.id.vPager);
		manager = new LocalActivityManager(this, true);
		manager.dispatchCreate(savedInstanceState);
		InitViewPager();
		radioGroup = (RadioGroup) this.findViewById(R.id.main_radio);
		radioGroup
				.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						switch (checkedId) {

						case R.id.radio_button0:
							index = 0;
							listViews.set(
									0,
									getView("A", new Intent(MainActivity.this,
											IndexActivity.class)));
							mpAdapter.notifyDataSetChanged();
							mPager.setCurrentItem(0);
							break;

						case R.id.radio_button1:
							index = 1;
							listViews.set(
									1,
									getView("B", new Intent(MainActivity.this,
											SecondActivity.class)));
							mpAdapter.notifyDataSetChanged();
							mPager.setCurrentItem(1);
							break;

						case R.id.radio_button2:
							index = 2;
							listViews.set(
									2,
									getView("C", new Intent(MainActivity.this,
											ThirdActivity.class)));
							mpAdapter.notifyDataSetChanged();
							mPager.setCurrentItem(2);
							break;

						case R.id.radio_button3:
							index = 3;
							listViews.set(
									3,
									getView("D", new Intent(MainActivity.this,
											BankcardActivity.class)));
							mpAdapter.notifyDataSetChanged();
							mPager.setCurrentItem(3);
							break;

						default:
							break;
						}
					}
				});
	}

	/**
	 * 初始化ViewPager
	 */
	private void InitViewPager() {
		Intent intent = null;
		listViews = new ArrayList<View>();
		mpAdapter = new MyPagerAdapter(listViews);
		intent = new Intent(MainActivity.this, IndexActivity.class);
		listViews.add(getView("A", intent));
		intent = new Intent(MainActivity.this, SecondActivity.class);
		listViews.add(getView("B", intent));
		intent = new Intent(MainActivity.this, ThirdActivity.class);
		listViews.add(getView("C", intent));
		intent = new Intent(MainActivity.this, BankcardActivity.class);
		listViews.add(getView("D", intent));
		mPager.setOffscreenPageLimit(0);
		mPager.setAdapter(mpAdapter);
		mPager.setCurrentItem(0);
		mPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}

	/**
	 * ViewPager适配器
	 */
	public class MyPagerAdapter extends PagerAdapter {
		public List<View> mListViews;

		public MyPagerAdapter(List<View> mListViews) {
			this.mListViews = mListViews;
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView(mListViews.get(arg1));
		}

		@Override
		public void finishUpdate(View arg0) {
		}

		@Override
		public int getCount() {
			return mListViews.size();
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(mListViews.get(arg1), 0);
			return mListViews.get(arg1);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == (arg1);
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
		}
	}

	/**
	 * 页卡切换监听，ViewPager改变同样改变TabHost内容
	 */
	public class MyOnPageChangeListener implements OnPageChangeListener {

		public void onPageSelected(int arg0) {
			manager.dispatchResume();
			switch (arg0) {
			case 0:
				index = 0;
				radioGroup.check(R.id.radio_button0);
				listViews.set(
						0,
						getView("A", new Intent(MainActivity.this,
								IndexActivity.class)));
				mpAdapter.notifyDataSetChanged();
				break;
			case 1:
				index = 1;
				radioGroup.check(R.id.radio_button1);
				listViews.set(
						1,
						getView("B", new Intent(MainActivity.this,
								SecondActivity.class)));
				mpAdapter.notifyDataSetChanged();
				break;
			case 2:
				index = 2;
				radioGroup.check(R.id.radio_button2);
				listViews.set(
						2,
						getView("C", new Intent(MainActivity.this,
								ThirdActivity.class)));
				mpAdapter.notifyDataSetChanged();
				break;
			case 3:
				index = 3;
				radioGroup.check(R.id.radio_button3);
				listViews.set(
						3,
						getView("D", new Intent(MainActivity.this,
								BankcardActivity.class)));
				mpAdapter.notifyDataSetChanged();
				break;
			}
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		public void onPageScrollStateChanged(int arg0) {
		}
	}

	private View getView(String id, Intent intent) {
		return manager.startActivity(id, intent).getDecorView();
	}

}