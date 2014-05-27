package com.skootch.mobapps.utilities.mylibraryapp;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;

public class LibraryWebViewActivity extends Activity {
	
	private WebView mWebView;
	private final String mLibraryUrl="https://capitadiscovery.co.uk/leicestershire/login?message=borrowerservices_notloggedin&referer=https%3A%2F%2Fcapitadiscovery.co.uk%2Fleicestershire%2Faccount";
	private Intent callingIntent;
	private String mUsername;
	private String mPassword;
	private Handler mHandle=new Handler();
	
	@Override
	protected void onPause() {
		super.onPause();
		mWebView.loadUrl("javascript:document.forms[0].submit();");
		finish();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_library_web);
		callingIntent=getIntent();
		mUsername=callingIntent.getStringExtra("Username");
		mPassword=callingIntent.getStringExtra("Password");
		mWebView = (WebView) findViewById(R.id.librarywebview);
		
		new Thread(new Runnable() {
			public void run() {
				mHandle.post(new Runnable() {
					public void run() {
						mWebView.loadUrl(mLibraryUrl);
						mWebView.getSettings().setJavaScriptEnabled(true);
						mWebView.setWebViewClient(new WebViewClient() {
							public void onPageFinished(WebView view, String url) {
								view.loadUrl("javascript:document.getElementById('borrowerBarcodeTextBox').value = '"+mUsername+"';" +
										"document.getElementById('pinTextBox').value='"+mPassword+"';" +
										"document.forms[1].submit();");
							}
						});
					}
				});
			}
		}).start();
	}
}