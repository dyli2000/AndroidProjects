package com.example.testsqlite01;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.widget.TextView;

public class MainActivity extends Activity 
{
	CMySQLiteHelper myHelper;
	TextView tv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tv = (TextView)findViewById(R.id.tv);
		tv.clearComposingText();
		
		/* 创建CMySQLiteHelper辅助类对象 */
		myHelper = new CMySQLiteHelper(this,"star.db",null,1);
		
		/* 向数据库中插入和更新数据 */
		InsertAndUpdateData(myHelper);
		
		String result = QueryData(myHelper);
		tv.setTextColor(Color.RED);
		tv.setTextSize(20.0f);
		tv.setText("name\tballAge\n"+result);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
    /** *************************************************************************************************
    *       DESC :     向数据库中插入和更新数据。
    *       ARGC :
    *                      @myHelper:   CMySQLiteHelper 对象。
    *       RET  :       void.
    *---------------------------------------------------------------------------------------------------
    ****************************************************************************************************/
	public void InsertAndUpdateData(CMySQLiteHelper myHelper)
	{
		/* 获取数据库对象  */
		SQLiteDatabase db = myHelper.getWritableDatabase();
		
		/* 使用execSQL方法向表中插入 */
		db.execSQL("insert into StarInfo(name,ballAge) values('Kobe',15)");

		ContentValues values = new ContentValues();
		
		/*  使用insert方法插入数据  */
		values.put("name", "James");
		values.put("ballAge",10);
		db.insert("StarInfo","id",values);
		
		/* 清空ContentValues对象  */	
		values.clear();	
		
		/*  使用UPDATE方法更新表中的数据  */
		values.put("name", "Durant");
		values.put("ballAge", 6);
		db.update("StarInfo", values, "ballAge=10", null);  	// 将James记录改为的Durant ballAge改为6
		
		/* 关闭SQLiteDatabase对象  */
		db.close();		
	}
	
    /** *************************************************************************************************
    *       DESC :     数据库数据查询
    *       ARGC :
    *                      @myHelper:   CMySQLiteHelper 对象。
    *       RET  :       void.
    *---------------------------------------------------------------------------------------------------
    ****************************************************************************************************/
	public String QueryData(CMySQLiteHelper myHelper)
	{
		String result = "";
		SQLiteDatabase db  = myHelper.getReadableDatabase();	// 获取数据库对象

		/* 查询表的数据  */
		Cursor cursor = db.query("StarInfo", null, null, null, null, null, "id asc");  
		/*  获取索引 */
		int nameIndex = cursor.getColumnIndex("name");
		int ballAgeIndex = cursor.getColumnIndex("ballAge");
		
		for(cursor.moveToFirst(); !(cursor.isAfterLast());cursor.moveToNext())
		{
			result = result + cursor.getString(nameIndex) + "\t";
			result = result + cursor.getInt(ballAgeIndex) + "\n";
 		}
		
		/*  资源释放  */
		cursor.close();	// 关闭结果集
		db.close();			// 关闭数据库对象
		return result;
	}
	
    /** *************************************************************************************************
    *       DESC :     程序退出（只按返回键时被执行，按虚拟器上的“主页”键是无效的）
    *       ARGC :
    *       RET  :       void.
    *---------------------------------------------------------------------------------------------------
    ****************************************************************************************************/
	protected void onDestroy()
	{
		SQLiteDatabase db = myHelper.getWritableDatabase();  // 获取数据对象
		/* 删除StarInfo中的所有数据，1 表示删除所有行 */
		db.delete("StarInfo","1",null);
		super.onDestroy();
	}

}
