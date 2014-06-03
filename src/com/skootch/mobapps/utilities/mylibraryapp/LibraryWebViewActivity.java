package com.skootch.mobapps.utilities.mylibraryapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;

public class LibraryWebViewActivity extends Activity {
	
	private WebView mWebView;
	private final String mLibraryUrl="https://capitadiscovery.co.uk/leicestershire/login?message=borrowerservices_notloggedin&referer=https%3A%2F%2Fcapitadiscovery.co.uk%2Fleicestershire%2Faccount";
	private Intent callingIntent;
	private String name;
	private String username;
	private String password;
	private Handler mHandle=new Handler();
	private ProgressDialog mProgressDialog;
	
    public void showDialog(String message) {
        mProgressDialog=ProgressDialog.show(this,"Logging In:", message+"'s Account");
    }
    
    public void dismissDialog() {
        if (mProgressDialog != null)
            mProgressDialog.dismiss();
    }
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_library_web);
		callingIntent=getIntent();
		name=callingIntent.getStringExtra("Name");
		username=callingIntent.getStringExtra("Username");
		password=callingIntent.getStringExtra("Password");
		mWebView = new WebView(LibraryWebViewActivity.this);
		mWebView.getSettings().setJavaScriptEnabled(true);
		new LoadWebView().execute(mWebView);
	}
	
	class LoadWebView extends AsyncTask<WebView, Integer, WebView> {
		WebView webView1 = new WebView(LibraryWebViewActivity.this);
		
		protected void onPreExecute() {
			showDialog(name);
		}
		
		protected WebView doInBackground(WebView... webView) {
			webView1.setWebViewClient(new WebViewClient() {
				public void onPageFinished(WebView view, String url) {
					super.onPageFinished(view, url);
					view.loadUrl("javascript:document.getElementById('borrowerBarcodeTextBox').value = '"+username+"';" +
							"document.getElementById('pinTextBox').value='"+password+"';" +
							"document.forms[1].submit();");
					
				}
			});
			webView1.loadUrl(mLibraryUrl);
			
			return webView1;
		}
		
		protected void onPostExecute(WebView webView) {
			dismissDialog();
			setContentView(webView1);
		}
	}
	
	
	@Override
	protected void onPause() {
		super.onPause();
		mWebView.loadUrl("javascript:document.forms[0].submit();");
		finish();
	}
}