package com.eoeAndroid.SQLite;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.*;

public class ActivityMain extends Activity
{
	OnClickListener listener1 = null;
	OnClickListener listener2 = null;
	OnClickListener listener3 = null;
	OnClickListener listener4 = null;
	OnClickListener listener5 = null;

	Button button1;
	Button button2;
	Button button3;
	Button button4;
	Button button5;
	TextView mShowDataTView;

	DatabaseHelper mOpenHelper;

	private static final String DATABASE_NAME = "dbForTest.db";
	private static final int DATABASE_VERSION = 1;
	private static final String TABLE_NAME = "Subjects";
	private static final String SID = "Sid";
	private static final String BODY = "Body";
	private static final String ANSWER  = "Answer";
	private static final String IS_ANSWER_CORRECTLY = "IsAnswerCorrectly";   
	
	private static class DatabaseHelper extends SQLiteOpenHelper 
	{
		DatabaseHelper(Context context) 
		{
			/* 通过DatabaseHelper 帮助创建database  */
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}


	    /** *************************************************************************************************
	     *       DESC :    在SQLiteOpenHelper创建database的时候被自动调用
	     *       ARGC :
	     *                     @ db:        SQLiteOpenHelper 肯定维护一个数据库的对象
	     *---------------------------------------------------------------------------------------------------
	     ****************************************************************************************************/    	
		@Override
		public void onCreate(SQLiteDatabase db) 
		{
			String sql = "CREATE TABLE " + TABLE_NAME + " (" 
							  + SID + " int not null, " 
							  + BODY + " text not null, " 
							  +  ANSWER +" text no null," 
							  +  IS_ANSWER_CORRECTLY + " int no null"
							  + ");";
			
			Log.i("giggle:createDB=", sql);
			db.execSQL(sql);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		}
	}

    /** *************************************************************************************************
     *       DESC :    Activity对象创建时被调用
     *       ARGC :
     *                     @ savedInstanceState:    A mapping from String values to various parcelable types.     
     *---------------------------------------------------------------------------------------------------
     ****************************************************************************************************/    	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		prepareListener();
		initLayout();
		mOpenHelper = new DatabaseHelper(this);
	}

	private void initLayout() 
	{
		button1 = (Button) findViewById(R.id.button1);
		button1.setOnClickListener(listener1);

		button2 = (Button) findViewById(R.id.button2);
		button2.setOnClickListener(listener2);

		button3 = (Button) findViewById(R.id.button3);
		button3.setOnClickListener(listener3);
		button4 = (Button) findViewById(R.id.button4);
		button4.setOnClickListener(listener4);

		button5 = (Button) findViewById(R.id.button5);
		button5.setOnClickListener(listener5);
		
		mShowDataTView = (TextView)findViewById(R.id.textView1);
	}

	//按钮响应函数
	private void prepareListener() 
	{
		listener1 = new OnClickListener() 
		{
			public void onClick(View v) 
			{
				createTable();
			}
		};
		
		listener2 = new OnClickListener() 
		{
			public void onClick(View v) 
			{
				dropTable();
			}
		};
		
		listener3 = new OnClickListener() 
		{
			public void onClick(View v) 
			{
				insertItem();
			}
		};
		
		listener4 = new OnClickListener() 
		{
			public void onClick(View v) 
			{
				deleteItem();
			}
		};
		
		listener5 = new OnClickListener() 
		{
			public void onClick(View v) 
			{
				showItems();
			}
		};
	}

	/*
	 * 重新建立数据表
	 */
	private void createTable() 
	{
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		String sql = "CREATE TABLE " + TABLE_NAME + " (" 
						  + SID + " int not null, " 
						  + BODY + " text not null, " 
						  +  ANSWER +" text no null," 
						  +  IS_ANSWER_CORRECTLY + " int no null"
						  + ");";
		
		Log.i("giggle:createDB=", sql);
		try 
		{
			db.execSQL("DROP TABLE IF EXISTS Subjects");
			db.execSQL(sql);
			setTitle("数据表成功重建");
		} 
		catch (SQLException e) 
		{
			setTitle("数据表重建错误");
		}
	}

	/*
	 * 删除数据表
	 */
	private void dropTable() 
	{
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		String sql = "drop table " + TABLE_NAME;
		try 
		{
			db.execSQL(sql);
			setTitle("数据表成功删除：" + sql);
		} 
		catch (SQLException e) 
		{
			setTitle("数据表删除错误");
		}
	}

	/*
	 * 插入两条数据
	 */
	private void insertItem() 
	{
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		String sql1 = "insert into " + TABLE_NAME + " (" + SID + ", " + BODY+ "," + ANSWER + "," + IS_ANSWER_CORRECTLY +")" +
							" values(1,'常见的设计模式有多少种?', 23, 0);";
		String sql2 = "insert into " + TABLE_NAME + " (" + SID + ", " + BODY+ "," + ANSWER + "," + IS_ANSWER_CORRECTLY +")" +
							" values(2,'NBA常规赛有多少场?', 81, 0);";		
		try 
		{
			Log.i("giggle:sql1=", sql1);
			Log.i("dungeon:sql2=", sql2);
			db.execSQL(sql1);
			db.execSQL(sql2);
			setTitle("插入两条数据成功");
		} catch (SQLException e) 
		{
			setTitle("插入两条数据失败");
		}
	}

	/*
	 * 删除其中的一条数据
	 */
	private void deleteItem() 
	{
		try 
		{
			SQLiteDatabase db = mOpenHelper.getWritableDatabase();
			db.delete(TABLE_NAME, " title = 'giggle'", null);
			setTitle("删除title为giggle的记录");
		} 
		catch (SQLException e) {}
	}

	/*
	 * 在屏幕的title区域显示当前数据表当中的数据的条数。
	 */
	private void showItems() 
	{
//		/* 查询表的数据  */
//		Cursor cursor = db.query("StarInfo", null, null, null, null, null, "id asc");  
//		/*  获取索引 */
//		int nameIndex = cursor.getColumnIndex("name");
//		int ballAgeIndex = cursor.getColumnIndex("ballAge");
//		
//		for(cursor.moveToFirst(); !(cursor.isAfterLast());cursor.moveToNext())
//		{
//			result = result + cursor.getString(nameIndex) + "\t";
//			result = result + cursor.getInt(ballAgeIndex) + "\n";
// 		}
		// SID + ", " + BODY+ "," + ANSWER + "," + IS_ANSWER_CORRECTLY
		
		SQLiteDatabase db = mOpenHelper.getReadableDatabase();
		//String col[] = { SID, BODY };
		String result = "";
		Cursor cur = db.query(TABLE_NAME, null, null, null, null, null, null);
		int  sidIndex = cur.getColumnIndex("Sid");
		int bodyIndex = cur.getColumnIndex("Body");
		int answerIndex = cur.getColumnIndex("Answer");
		int isOkIndex = cur.getColumnIndex("IsAnswerCorrectly");
		
		for(cur.moveToFirst(); !(cur.isAfterLast());cur.moveToNext())
		{
			result = result + cur.getInt(sidIndex) + "\t";
			result = result + cur.getString(bodyIndex) + "\t";
			result = result + cur.getString(answerIndex) + "\t";
			result = result + cur.getInt(isOkIndex) + "\n";
		}
		
		Integer num = cur.getCount();
	
		setTitle(Integer.toString(num) + " 条记录");			
	}	
}