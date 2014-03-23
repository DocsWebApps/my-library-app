package com.skootch.mobapps.utilities.mylibraryapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	
	private static final String myPin="3391";
	private static String enteredPin="";
	final Context loginView=this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		final EditText pin1=(EditText) findViewById(R.id.pin1);
		final EditText pin2=(EditText) findViewById(R.id.pin2);
		final EditText pin3=(EditText) findViewById(R.id.pin3);
		final EditText pin4=(EditText) findViewById(R.id.pin4);
		
		setOnFocusChangeListeners(pin1);
		setOnFocusChangeListeners(pin2);
		setOnFocusChangeListeners(pin3);
		setOnFocusChangeListeners(pin4);
		
		respondToTextChanges(pin1,pin2);
		respondToTextChanges(pin2,pin3);
		respondToTextChanges(pin3,pin4);
				
		pin4.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable arg0) {
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				if (pin4.getText().toString().length()<1) return;
				enteredPin=enteredPin+pin4.getText().toString();
				if (myPin.equals(enteredPin)) {
					Intent accountIntent=new Intent(loginView, AccountDisplay.class);
					startActivity(accountIntent);
				} else {
					Toast.makeText(loginView,"Incorrect pin, please re-enter", Toast.LENGTH_SHORT).show();
					enteredPin="";
					pin2.requestFocus();
					pin3.requestFocus();
					pin4.requestFocus();
					pin1.requestFocus();
				}
			}});
	}
		
	private void respondToTextChanges(final EditText et1, final EditText et2) {
		et1.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				if (et1.getText().toString().length()<1) return;
				enteredPin=enteredPin+et1.getText().toString();
				et2.requestFocus();	
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
			}});
	}
	
	private void setOnFocusChangeListeners(EditText et) {
		et.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					((EditText) v).setText("");
				}
		}});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
}