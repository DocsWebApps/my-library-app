package com.skootch.mobapps.utilities.mylibraryapp;

import java.util.ArrayList;
import java.util.List;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.content.ContentValues;

public class AccountDisplayAdapter extends BaseAdapter{

	private final Context mContext;
	private SQLiteDatabase database;
	final private static String NAME = "library_db";
	final private static Integer VERSION = 1;
	List<AccountRecord> recordList=new ArrayList<AccountRecord>();

	final static String _ID = "_id";
	final private String[] mColumns={AccountRecord.NAME,AccountRecord.BORROWER_NUMBER,AccountRecord.BORROWER_PIN};
	final private static String CREATE_TABLE = "CREATE TABLE " + AccountRecord.TABLE_NAME +  " (" 
					+ _ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ AccountRecord.NAME + " TEXT NOT NULL,"
					+ AccountRecord.BORROWER_NUMBER + " TEXT NOT NULL,"
					+ AccountRecord.BORROWER_PIN + " TEXT NOT NULL);";

	
	public AccountDisplayAdapter(Context context) {
		mContext = context;
		DatabaseHelper dbHelper=new DatabaseHelper();
		database = dbHelper.getWritableDatabase();
		getRecords();
	}
	
	private void getRecords() {
		recordList.clear();
		String name, number,pin;
		SQLiteQueryBuilder qb=new SQLiteQueryBuilder();
		qb.setTables(AccountRecord.TABLE_NAME);
		Cursor mCursor=qb.query(database, mColumns, null, null, null, null, AccountRecord.NAME);
		if (mCursor.moveToFirst()) {
			do {
				name=mCursor.getString(mCursor.getColumnIndex(AccountRecord.NAME));
				number=mCursor.getString(mCursor.getColumnIndex(AccountRecord.BORROWER_NUMBER));
				pin=mCursor.getString(mCursor.getColumnIndex(AccountRecord.BORROWER_PIN));
				recordList.add(new AccountRecord(name,number,pin));	
			} while (mCursor.moveToNext());
		}
	}
	
	public void addRecord(ContentValues values) {
		long ROWID=database.insert(AccountRecord.TABLE_NAME,null,values);
		getRecords();
		notifyDataSetChanged();
		if (!(ROWID>0)) {
			throw new SQLException("Failed to add record into " + AccountRecord.NAME);
		}
	}

	@Override
	public int getCount() {
		if (recordList.isEmpty()) {
			return 0;
		} else { 
			return recordList.size();
		}
	}

	@Override
	public Object getItem(int position) {
		return recordList.get(position);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final AccountRecord record=recordList.get(position);
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		RelativeLayout listView=(RelativeLayout) inflater.inflate(R.layout.account_view, null);
		TextView accountText=(TextView) listView.findViewById(R.id.account_text);
		accountText.setText(record.getName());
		
		listView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(mContext, LibraryWebViewActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.putExtra("Name", record.getName());
				intent.putExtra("Username", record.getBorrowerNumber());
				intent.putExtra("Password", record.getBorrowerPin());
				mContext.startActivity(intent);
			}
		});
		
		return listView;
	}
	
	public class DatabaseHelper extends SQLiteOpenHelper {
		public DatabaseHelper() {
			super(mContext, NAME, null, VERSION);
		}
		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL(CREATE_TABLE);
		}
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS " + AccountRecord.TABLE_NAME);
		}	
	}
}
