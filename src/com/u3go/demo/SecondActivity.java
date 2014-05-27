package com.u3go.demo;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class SecondActivity extends Activity {
	WebView webview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.getWindow().requestFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.webview);
		webview = (WebView) findViewById(R.id.webview);
		webview.getSettings().setJavaScriptEnabled(false);
		webview.loadUrl("http://whu.u3go.com/mobile/user.php?act=user_center");
		webview.setWebViewClient(new HelloWebViewClient());
		webview.setWebChromeClient(new WebChromeClient()   
	    {            
	        public void onProgressChanged(WebView view, int progress)     
	        {
	        	SecondActivity.this.setTitle("Loading...");         
	        	SecondActivity.this.setProgress(progress * 100);       
	            if(progress == 100)              
	            	SecondActivity.this.setTitle(R.string.app_name);         
	            }        
	        }  
	    );
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.i("test", keyCode + "-------");
		if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
			webview.goBack();
			Log.i("test", keyCode + "+++++++++++++++++");
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private class HelloWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			Log.i("test", url + "-=-=-=-=-=-=-=-=-=");
			view.loadUrl(url);
			return true;
		}
	}
}
