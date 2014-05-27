package com.u3go.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Window;
import android.view.WindowManager;

/**
 * 欢迎界面
 * 
 * @author Administrator
 * 
 */
public class WelcomeActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);

		new CountDownTimer(3000, 1000) {

			@Override
			public void onTick(long millisUntilFinished) {
			}

			@Override
			public void onFinish() {
				Intent intent = new Intent();
				intent.setClass(WelcomeActivity.this, MainActivity.class);
				startActivity(intent);

				int VERSION = android.os.Build.VERSION.SDK_INT;
				if (VERSION >= 5) {
					WelcomeActivity.this.overridePendingTransition(
							R.anim.alpha_in, R.anim.alpha_out);
				}
				finish();
			}
		}.start();

	}

}
