package com.skootch.mobapps.utilities.mylibraryapp;

import android.content.Intent;
import android.content.ContentValues;

public class AccountRecord {
	
	public final static String TABLE_NAME="accounts";
	public final static String NAME = "name";
	public final static String BORROWER_NUMBER="borrower_number";
	public final static String BORROWER_PIN="borrower_pin";
	
	private String name;
	private String borrower_number;
	private String borrower_pin;
	
	AccountRecord(Intent intent) {
		this.name=intent.getStringExtra("name");
		this.borrower_number=intent.getStringExtra("borrower_number");
		this.borrower_pin=intent.getStringExtra("borrower_pin");
	}
	
	AccountRecord(String name, String number, String pin) {
		this.name=name;
		this.borrower_number=number;
		this.borrower_pin=pin;
	}
	
	public ContentValues getContentValues() {
		ContentValues values=new ContentValues();
		values.put(AccountRecord.NAME, this.name);
		values.put(AccountRecord.BORROWER_NUMBER, this.borrower_number);
		values.put(AccountRecord.BORROWER_PIN, this.borrower_pin);
		return values;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getBorrowerNumber() {
		return this.borrower_number;
	}
	
	public String getBorrowerPin() {
		return this.borrower_pin;
	}
}