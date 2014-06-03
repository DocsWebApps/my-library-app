package com.skootch.mobapps.utilities.mylibraryapp;

import android.os.Bundle;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;

public class LibraryWebViewActivity extends Activity {
	
	private WebView mWebView;
	private final String mLibraryUrl="https://capitadiscovery.co.uk/leicestershire/login?message=borrowerservices_notloggedin&referer=https%3A%2F%2Fcapitadiscovery.co.uk%2Fleicestershire%2Faccount";
	private Intent callingIntent;
	private String name;
	private String username;
	private String password;
	private ProgressDialog mProgressDialog;
	
    public void showDialog(String message) {
        mProgressDialog=ProgressDialog.show(this,"Logging In:", message+"'s Account");
    }
    
    public ValueCallback<String> dismissDialog() {
        if (mProgressDialog != null)
            mProgressDialog.dismiss();
		return null;
    }
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_library_web);
		callingIntent=getIntent();
		name=callingIntent.getStringExtra("Name");
		username=callingIntent.getStringExtra("Username");
		password=callingIntent.getStringExtra("Password");
		mWebView = (WebView) findViewById(R.id.librarywebview);
		showDialog(name);
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.getSettings().setBuiltInZoomControls(true);
		mWebView.loadUrl(mLibraryUrl);
		mWebView.setWebViewClient(new WebViewClient() {
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				view.evaluateJavascript("javascript:document.getElementById('borrowerBarcodeTextBox').value = '"+username+"';" +
						"document.getElementById('pinTextBox').value='"+password+"';" +
						"document.forms[1].submit();", dismissDialog());
			}
		});
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		mWebView.evaluateJavascript("javascript:document.forms[0].submit();", null);
		finish();
	}
}