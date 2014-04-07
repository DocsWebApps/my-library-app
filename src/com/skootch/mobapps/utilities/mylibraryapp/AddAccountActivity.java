package com.skootch.mobapps.utilities.mylibraryapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import android.view.View;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.content.Intent;

public class AddAccountActivity extends Activity {
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_account_view);
		
		Button saveButton=(Button) findViewById(R.id.butSave);
		final TextView acc_name=(TextView) findViewById(R.id.name);
		final TextView borrower_num=(TextView) findViewById(R.id.borrower_number);
		final TextView borrower_pin=(TextView) findViewById(R.id.borrower_pin);
		
		saveButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String name=acc_name.getText().toString();
				String number=borrower_num.getText().toString();
				String pin=borrower_pin.getText().toString();
				
				if (name.length()==0||number.length()==0||pin.length()==0) {
					Toast.makeText(getApplicationContext(), "One or more fields are empty", Toast.LENGTH_SHORT).show();
				} else {
					Intent returnIntent=new Intent();
					returnIntent.putExtra(AccountRecord.NAME, name);
					returnIntent.putExtra(AccountRecord.BORROWER_NUMBER, number);
					returnIntent.putExtra(AccountRecord.BORROWER_PIN, pin);
					setResult(RESULT_OK, returnIntent);
					finish();
				}
			}
		});
	}
}
